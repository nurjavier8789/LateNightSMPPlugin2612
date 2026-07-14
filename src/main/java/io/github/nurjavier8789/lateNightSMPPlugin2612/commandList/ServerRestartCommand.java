package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.title.Title;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.time.Duration;

public class ServerRestartCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private boolean sedangRestart = false;
    private BukkitTask tugasRestart;

    public ServerRestartCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("latenightsmpplugin.admin.restartserver")) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cHol up! What are you doing? Nu-uh...");
            return true;
        }

        if (args.length == 1) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §fGunakan §b/lnsmpp restartserver <durasi> §fatau §b/lnsmpp restartserver cancel");
            return true;
        }
        
        if (args.length > 0  && args[1].equalsIgnoreCase("cancel")) {
            if (!sedangRestart || tugasRestart == null) {
                sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cTidak ada jadwal restart yang sedang berjalan.");
                return true;
            }

            tugasRestart.cancel();
            
            sedangRestart = false;
            tugasRestart = null;

            Bukkit.broadcast(Component.text("§a§l[!] §fRestart server §aDIBATALKAN§f!\nMaap ges... >_<"));
            
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.clearTitle();
            }
            
            return true;
        }

        if (sedangRestart) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cServer sudah dalam proses restart! Ketik '/lnsmpp restartserver cancel' untuk membatalkan.");
            return true;
        }

        int waktu = 60;
        if (args.length > 0) {
            try {
                waktu = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cHarap masukkan angka yang valid atau ketik 'cancel'!");
                return true;
            }
        }

        sedangRestart = true;
        final int waktuAwal = waktu;

        tugasRestart = new BukkitRunnable() {
            int detik = waktuAwal;

            @Override
            public void run() {
                if (detik <= 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kick(Component.text("§c§lServer sedang direstart!\n\n§fHubungi owner server untuk cek status server."));
                    }
                    Bukkit.restart();
                    cancel();
                    return;
                }

                if (detik % 10 == 0 || detik <= 5) {
                    if (detik >= 5) {
                        Bukkit.broadcast(Component.text("§c§l[!] §fServer akan direstart dalam §e" + detik + " detik§f!\nPastikan selasaikan urusan kalian dan simpan progress kalian!"));
                    }
                    
                    Component title = Component.text("RESTART").color(NamedTextColor.RED).decorate(TextDecoration.BOLD);
                    Component subtitle = Component.text("Dalam " + detik + " detik").color(NamedTextColor.WHITE);

                    Title.Times durasi = Title.Times.times(Duration.ofMillis(250), Duration.ofSeconds(1), Duration.ofMillis(250));

                    Title titleRestart = Title.title(title, subtitle, durasi);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.showTitle(titleRestart);
                    }
                }

                detik--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
