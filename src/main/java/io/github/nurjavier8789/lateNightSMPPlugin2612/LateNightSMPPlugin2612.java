package io.github.nurjavier8789.lateNightSMPPlugin2612;

import io.github.nurjavier8789.lateNightSMPPlugin2612.listener.*;
import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList._MainCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

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

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new lnsmppExpansionPAPI(this).register();
            getLogger().info("Placeholder %lnsmpp_...% berhasil didaftarkan!");
        } else {
            getLogger().warning("PlaceholderAPI tidak ditemukan!");
        }

        getCommand("latenightsmpplugin").setExecutor(new _MainCommand(this));
        
        getServer().getPluginManager().registerEvents(new ServerLinksListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new helpTabCompletion(), this);
        getServer().getPluginManager().registerEvents(new ElytraListener(this), this);
        getServer().getPluginManager().registerEvents(new FirstJoinListener(), this);
        getServer().getPluginManager().registerEvents(new LNSMPPEventListener(this), this);

        getLogger().info("Late Night SMP Plugin is active!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getServer().getScheduler().runTaskLater(this, () -> {
            if (player.isOnline()) {
                player.sendMessage("");
                player.sendMessage("§b=======================================");
                player.sendMessage("§eSelamat datang di Late Night SMP, §a" + player.getName() + "§e!");
                player.sendMessage("§eGunakan §b/lnsmpp §euntuk command resmi server!");
                player.sendMessage("§6Tracking saat ini: §f" + getConfig().getString("monthly-event.counter.leaderboard-display"));
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

    @Override
    public void onDisable() {
        getLogger().info("Late Night SMP Plugin is disabled!");
        getLogger().info("Have a nice day!");
    }
}
