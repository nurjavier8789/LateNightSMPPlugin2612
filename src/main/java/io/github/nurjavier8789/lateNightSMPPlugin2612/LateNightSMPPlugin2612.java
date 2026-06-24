package io.github.nurjavier8789.lateNightSMPPlugin2612;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.kyori.adventure.text.Component;

public final class LateNightSMPPlugin2612 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        startUIUpdater();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new helpTabCompletion(), this);
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
                player.sendMessage("§eSelamat bermain!");
                player.sendMessage("§b=======================================");
                player.sendMessage("");
            }
        }, 20L);
    }

    private void startUIUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateTabList(player);
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    private void updateTabList(Player player) {
        int ping = player.getPing();
        String pingColor = "§a";
        if (ping > 150 && ping <= 300) pingColor = "§e";
        else if (ping > 300) pingColor = "§c";

        Component header = Component.text("\n§b §lLate Night SMP§b\n    §7Selamat datang, §e" + player.getName() + "    \n");
        Component footer = Component.text("\n§7Ping: " + pingColor + ping + "ms\n" + "§bHave fun playing!\n");

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
