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
        String target = plugin.getConfig().getString("monthly-event.counter.track-counter");
        String displayText = plugin.getConfig().getString("monthly-event.counter.leaderboard-display");

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        sender.sendMessage("§e=== §lMonthly Tracking §e===");
        sender.sendMessage("§eCurrently tracking: §r" + displayText);
        
        for (int i = 1; i <= 8; i++) {
            String teksNama = "%ajlb_lb_" + target + "_" + i + "_monthly_name%";
            String teksNilai = "%ajlb_lb_" + target + "_" + i + "_monthly_value%";
            
            String namaAsli = PlaceholderAPI.setPlaceholders(player, teksNama);
            String nilaiAsli = PlaceholderAPI.setPlaceholders(player, teksNilai);

            if (i == 1 || i == 2 || i == 3) {
                sender.sendMessage("§b" + i + ". §6" + namaAsli + " §7- §a" + nilaiAsli);
            } else {
                sender.sendMessage("§b" + i + ". §f" + namaAsli + " §7- §a" + nilaiAsli);
            }

        }

        return true;
    }
}
