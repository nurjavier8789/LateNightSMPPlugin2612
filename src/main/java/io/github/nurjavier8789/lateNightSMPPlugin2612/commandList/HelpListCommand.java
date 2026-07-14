package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("\n§bOfficial Late Night SMP Command List");
        sender.sendMessage("§eGunakan /lnsmpp <command> untuk menggunakan command yang tersedia!");
        sender.sendMessage("§eIni adalah list command yang tersedia!");
        sender.sendMessage("§n§lCommand List:");
        sender.sendMessage("§6leaderboard§f: Lihat monthly leaderboard");
        sender.sendMessage("§6sbtoggle§f: Sembunyikan/menampilkan scoreboard");
        sender.sendMessage("§6help§f: TOLONGG!!! (ini cuma menampilkan list ini)");

        return true;
    }
}
