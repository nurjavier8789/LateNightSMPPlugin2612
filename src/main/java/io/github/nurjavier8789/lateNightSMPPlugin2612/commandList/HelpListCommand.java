package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("\n§bOfficial Late Night SMP Command List");
        sender.sendMessage("§eGunakan /lnsmpp <command> untuk menggunakan command yang tersedia!");
        sender.sendMessage("\n§n§lCommand List:");
        sender.sendMessage("§6/lnsmpp event§f: Cek apakah ada event yang berjalan.");
        sender.sendMessage("§6/lnsmpp help§f: TOLONGG!!! (ini cuma menampilkan list ini).");
        sender.sendMessage("§6/lnsmpp leaderboard§f: Lihat monthly leaderboard.");
        sender.sendMessage("§6/lnsmpp sbtoggle§f: Sembunyikan/menampilkan scoreboard.");

        return true;
    }
}
