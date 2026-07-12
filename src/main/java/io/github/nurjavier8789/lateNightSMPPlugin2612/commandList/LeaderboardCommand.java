package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;

public class LeaderboardCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public LeaderboardCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        String target = plugin.getConfig().getString("monthly-event.counter.track-counter");
        String displayText = plugin.getConfig().getString("monthly-event.counter.leaderboard-display");

        player.sendMessage("");
        player.sendMessage("§e=== §lMonthly Tracking §e===");
        player.sendMessage("§eCurrently tracking: §r" + displayText);
        
        for (int i = 1; i <= 5; i++) {
            String teksNama = "%ajlb_lb_" + target + "_" + i + "_monthly_name%";
            String teksNilai = "%ajlb_lb_" + target + "_" + i + "_monthly_value%";
            
            String namaAsli = PlaceholderAPI.setPlaceholders(player, teksNama);
            String nilaiAsli = PlaceholderAPI.setPlaceholders(player, teksNilai);

            if (i == 1 || i == 2 || i == 3) {
                player.sendMessage("§b" + i + ". §6" + namaAsli + " §7- §a" + nilaiAsli);
            } else {
                player.sendMessage("§b" + i + ". §f" + namaAsli + " §7- §a" + nilaiAsli);
            }

        }
        
        player.sendMessage("§e========================");
        player.sendMessage("");

        return true;
    }
}
