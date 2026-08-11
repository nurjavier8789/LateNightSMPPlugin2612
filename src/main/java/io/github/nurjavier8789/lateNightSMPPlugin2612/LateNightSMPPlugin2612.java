package io.github.nurjavier8789.lateNightSMPPlugin2612;

import io.github.nurjavier8789.lateNightSMPPlugin2612.listener.*;
import io.github.nurjavier8789.lateNightSMPPlugin2612.model.chatModels;
import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList.ChatCommand;
import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList._MainCommand;

import java.net.URI;

import org.bukkit.Bukkit;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;

public final class LateNightSMPPlugin2612 extends JavaPlugin implements Listener {
    public static Economy econ = null;
    chatModels ChatModels = new chatModels();
    // private CustomConfig linksConfig;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        // linksConfig = new CustomConfig(this, "announcement.yml");

        if (!setupEconomy() ) {
            getLogger().severe("Vault tidak ditemukan! Pastikan Vault terinstall untuk mencegah plugin rusak!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new lnsmppExpansionPAPI(this).register();
            getLogger().info("Connected to PlaceholderAPI!");
        } else {
            getLogger().warning("PlaceholderAPI tidak ditemukan!");
        }
        
        getLogger().info("Registering command...");
        getCommand("latenightsmpplugin").setExecutor(new _MainCommand(this));
        
        ChatCommand chatCommand = new ChatCommand(ChatModels);
        getCommand("chat").setExecutor(chatCommand);
        getLogger().info("Command registered!");
        
        getLogger().info("Registering listener...");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new CommandTabCompletion(), this);
        getServer().getPluginManager().registerEvents(new ElytraListener(this), this);
        // getServer().getPluginManager().registerEvents(new FirstJoinListener(), this);
        getServer().getPluginManager().registerEvents(new LNSMPPEventListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(ChatModels), this);
        getLogger().info("Listener registered!");
        
        getLogger().info("Registering server links...");
        ServerLinks serverLinks = Bukkit.getServer().getServerLinks();
        for (ServerLinks.ServerLink linkLama : serverLinks.getLinks()) {
            serverLinks.removeLink(linkLama);
        }

        serverLinks.addLink(Component.text("Discord Server"), URI.create("https://discord.gg/muwrAQfe8r"));
        serverLinks.addLink(ServerLinks.Type.STATUS, URI.create("https://nurjavier8789.my.id/LNPPublicSheet.html"));
        getLogger().info("Server links registered!");

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
