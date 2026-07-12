package io.github.nurjavier8789.lateNightSMPPlugin2612;

import io.github.nurjavier8789.lateNightSMPPlugin2612.listener.*;
import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList._MainCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.kyori.adventure.text.Component;

public final class LateNightSMPPlugin2612 extends JavaPlugin implements Listener {
    public static Economy econ = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!setupEconomy() ) {
            getLogger().severe("Vault tidak ditemukan! Pastikan Vault terinstall untuk mencegah plugin rusak!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("latenightsmpplugin").setExecutor(new _MainCommand(this));
        
        startUIUpdater();
        
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new helpTabCompletion(), this);
        getServer().getPluginManager().registerEvents(new ElytraListener(this), this);
        getServer().getPluginManager().registerEvents(new ServerLinksListener(this), this);
        getServer().getPluginManager().registerEvents(new FirstJoinListener(this), this);

        getLogger().info("Late Night SMP Plugin is active!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.joinMessage(Component.text("§a[+] §e" + player.getName()));

        getServer().getScheduler().runTaskLater(this, () -> {
            if (player.isOnline()) {
                player.sendMessage("");
                player.sendMessage("§b=======================================");
                player.sendMessage("§eSelamat datang di Late Night SMP, §a" + player.getName() + "§e!");
                player.sendMessage("§eTracking saat ini: §f" + getConfig().getString("monthly-event.counter.leaderboard-display"));
                player.sendMessage("§eSelamat bermain!");
                player.sendMessage("§b=======================================");
                player.sendMessage("");
            }
        }, 20L);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    private void startUIUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateTabList(player);
                };
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    private void updateTabList(Player player) {
        String currentTrackingText = getConfig().getString("monthly-event.counter.leaderboard-display");
        String currentEvent = getConfig().getString("monthly-event.counter.leaderboard-display");

        int ping = player.getPing();
        String pingColor = "§a";
        if (ping > 150 && ping <= 300) pingColor = "§e";
        else if (ping > 300) pingColor = "§c";

        double rawTps = Bukkit.getTPS()[0];
        double tps = Math.min(20.0, rawTps);
        String tpsWarna = "§a";
        if (tps < 18.0) {
            tpsWarna = "§e";
        }
        if (tps < 15.0) {
            tpsWarna = "§c";
        }
        String tpsFormatted = String.format(java.util.Locale.US, "%.2f", tps);

        Component header = Component.text("\n§b §lLate Night SMP Indonesia\nSeason 3\n    §7Selamat datang, §e" + player.getName() + "§7!    \n§eSedang tracking: §f" + currentTrackingText + "\n");
        Component footer = Component.text("\n§7Ping: " + pingColor + ping + "ms §8| §7TPS: " + tpsWarna + tpsFormatted + "\n" + "§bHave fun playing!\n");

        player.sendPlayerListHeaderAndFooter(header, footer);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(Component.text("§c[-] §e" + player.getName()));
    }

    @Override
    public void onDisable() {
        getLogger().info("Late Night SMP Plugin is disabled!");
        getLogger().info("Have a nice day!");
    }
}
