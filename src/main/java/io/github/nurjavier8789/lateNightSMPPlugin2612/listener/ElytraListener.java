package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ElytraListener implements Listener {
    private final JavaPlugin plugin;

    public ElytraListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerTryToGlide(EntityToggleGlideEvent event) {
        boolean isBan = plugin.getConfig().getBoolean("features.elytra.is-ban");
        if (!isBan) return;

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.isGliding()) {
                if (player.hasPermission("latenightsmpplugin.admin")) return;

                event.setCancelled(true);
                
                String pesan = plugin.getConfig().getString("features.elytra.message");
                if (pesan != null) {
                    player.sendMessage(pesan.replace("&", "§"));
                }
            }
        }
    }
}
