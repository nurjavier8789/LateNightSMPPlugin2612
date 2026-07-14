package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinListener implements Listener {
    @EventHandler
    public void saatPemainMasuk(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (!player.hasPlayedBefore()) {
            World dunia = Bukkit.getWorld("lateNightSMPSeason3");
            
            if (dunia != null) {
                double x = 0.5;
                double y = 64.0;
                double z = 0.5;
                float yaw = 180f; 
                float pitch = 0f;

                Location spawnLobby = new Location(dunia, x, y, z, yaw, pitch);

                player.teleport(spawnLobby);
            }
        }
    }
}
