package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import net.kyori.adventure.text.Component;

public class EventCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public EventCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static String countdownText = "Not Started";
    public static boolean isEventActive = false;
    public static BossBar eventBossBar;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean isRun = plugin.getConfig().getBoolean("event.event-running");
        String namaEvent = plugin.getConfig().getString("event.event-name");

        if (args.length == 1 && !isRun) {
            sender.sendMessage("§7[§eEvent§7] §fTidak ada event yang berjalan");
            return true;
        } else if (args.length == 1 && isRun) {
            sender.sendMessage("§7[§eEvent§7] §6Current Event: §f" + plugin.getConfig().getString("event.event-name"));
            sender.sendMessage("§7[§eEvent§7] §6Detail Event: §f" + plugin.getConfig().getString("event.event-details"));
            return true;
        }

        if (!sender.hasPermission("latenightsmpplugin.admin.event")) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cWhat are you doing? you don't even have access to config files...");
            return true;
        }

        if (args.length > 1  && args[1].equalsIgnoreCase("start") && sender.hasPermission("latenightsmpplugin.admin.event")) {
            if (isEventActive) {
                sender.sendMessage("§7[§eEvent§7] §cEvent sedang berjalan! Tunggu atau hentikan dulu.");
                return true;
            }

            sender.sendMessage("§7[§eEvent§7] Reading config file...");

            String eventType = plugin.getConfig().getString("event.event-type");
            String eventImpact = plugin.getConfig().getString("event.impact");

            if (!eventType.equalsIgnoreCase("EFFECT")) {
                sender.sendMessage("§7[§eEvent§7] §cPastikan \"event-type\" pada file config benar!");
                return true;
            } else if (!eventImpact.equalsIgnoreCase("moon-gravity") && !eventImpact.equalsIgnoreCase("fortune-drop")) {
                sender.sendMessage("§7[§eEvent§7] §cPastikan \"impact\" pada file config benar!");
                return true;
            }

            plugin.getConfig().set("event.event-running", true);
            isEventActive = true;
            final int duration = plugin.getConfig().getInt("event.duration");

            sender.sendMessage("§7[§eEvent§7] Starting...");

            Bukkit.broadcast(Component.text("§7[§eEvent§7] §a§lEVENT " + namaEvent + " DIMULAI!"));
            Bukkit.broadcast(Component.text("§7[§eEvent§7] §fCheck §b/lnsmpp event §ffor more info!"));

            eventBossBar = Bukkit.createBossBar(
                "§e§lEVENT: §r§f" + namaEvent,
                BarColor.GREEN,
                BarStyle.SOLID
            );

            for (Player player : Bukkit.getOnlinePlayers()) {
                eventBossBar.addPlayer(player);
                player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

                if (eventImpact.equalsIgnoreCase("moon-gravity")) {
                    aktifkanMoonGravity(player);
                }
            }

            new BukkitRunnable() {
                int durasi = duration;
                @Override
                public void run() {
                    if (!isEventActive) {
                        eventBossBar.removeAll();
                        this.cancel();
                        return;
                    }

                    durasi--;

                    int menit = durasi / 60;
                    int detik = durasi % 60;
                    countdownText = String.format("%02d:%02d", menit, detik);

                    eventBossBar.setTitle("§a§lEVENT: " + namaEvent + " §f| Sisa Waktu: §e" + countdownText);
                    eventBossBar.setProgress(1.0);

                    if (durasi <= 60) {
                        eventBossBar.setColor(BarColor.RED);
                    }

                    if (durasi <= 5 && durasi > 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
                        }
                    }

                    if (durasi <= 0) {
                        plugin.getConfig().set("event.event-running", false);
                        isEventActive = false;
                        countdownText = "Ended";

                        eventBossBar.removeAll();
                        
                        Bukkit.broadcast(Component.text("§7[§eEvent§7] §b§lEvent Selesai!"));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
                            hapusMoonGravity(player);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }

        if (args.length > 1  && args[1].equalsIgnoreCase("stop") && sender.hasPermission("latenightsmpplugin.admin.event")) {
            if (!isEventActive) {
                sender.sendMessage("§7[§eEvent§7] §cTidak ada event yang sedang berjalan!");
                return true;
            }

            isEventActive = false; 
            countdownText = "Not Started";

            plugin.getConfig().set("event.event-running", false);
            plugin.saveConfig();

            Bukkit.broadcast(Component.text("§7[§eEvent§7] §c§lEvent telah dihentikan paksa oleh Admin!"));
            
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_ANVIL_LAND, 1.0f, 0.5f);
                hapusMoonGravity(player);
            }
            return true;
        }
        return true;
    }

    public static final NamespacedKey GRAVITY_KEY = new NamespacedKey("lnsmpp", "moon_gravity");
    
    public static void aktifkanMoonGravity(Player player) {
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);
        if (gravity != null) {
            gravity.removeModifier(GRAVITY_KEY);
            
            AttributeModifier modifier = new AttributeModifier(
                GRAVITY_KEY, 
                -0.75, 
                AttributeModifier.Operation.ADD_SCALAR
            );
            
            gravity.addModifier(modifier);
        }
    }
    
    public static void hapusMoonGravity(Player player) {
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);
        if (gravity != null) {
            gravity.removeModifier(GRAVITY_KEY);
        }
    }
}
