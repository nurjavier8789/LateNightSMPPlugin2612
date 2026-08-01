package io.github.nurjavier8789.lateNightSMPPlugin2612;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.nurjavier8789.lateNightSMPPlugin2612.commandList.EventCommand;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class lnsmppExpansionPAPI extends PlaceholderExpansion {
    private final JavaPlugin plugin;

    public lnsmppExpansionPAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "lnsmpp";
    }

    @Override
    public String getAuthor() {
        return "nurjavier8789";
    }

    @Override
    public String getVersion() {
        return "2026.07.2-b";
    }

    @Override
    public boolean persist() {
        return true; 
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        // Events
        if (params.equalsIgnoreCase("event_status")) {
            return plugin.getConfig().getString("event.event-running");
        }

        if (params.equalsIgnoreCase("event_name")) {
            return plugin.getConfig().getString("event.event-name");
        }
        
        
        if (params.equalsIgnoreCase("event_duration")) {
            return EventCommand.countdownText;
        }

        // Tracker
        if (params.equalsIgnoreCase("monthly_tracker_name")) {
            return plugin.getConfig().getString("monthly-event.counter.leaderboard-display");
        }

        if (params.equalsIgnoreCase("monthly_tracker_track_counter")) {
            return plugin.getConfig().getString("monthly-event.counter.track-counter");
        }

        return null;
    }
}
