package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.util.StringUtil;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandExecutor;

public class _MainCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    private final ReloadPlugin reloadPlugin;
    private final LeaderboardCommand leaderboardCommand;
    private final ToggleSBCommand toggleSBCommand;

    public _MainCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.reloadPlugin = new ReloadPlugin(plugin);
        this.leaderboardCommand = new LeaderboardCommand(plugin);
        this.toggleSBCommand = new ToggleSBCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eHello and welcome to §bLate Night SMP Indonesia Season 3§e!");
            sender.sendMessage("§eHope you guys enjoy!");
            sender.sendMessage("\n§eNeed anything? just ask player in here or on discord!");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            return reloadPlugin.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("leaderboard")) {
            return leaderboardCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("sbtoggle")) {
            return toggleSBCommand.onCommand(sender, cmd, label, args);
        }

        sender.sendMessage("§cUh-oh. I can't find the command that you sent! (。_。)");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> saran = new ArrayList<>();
        List<String> hasilAkhir = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("latenightsmpplugin.admin")) {
                saran.add("reload");
            }
            saran.add("leaderboard");
            saran.add("sbtoggle");

            StringUtil.copyPartialMatches(args[0], saran, hasilAkhir);
            
            Collections.sort(hasilAkhir);
            return hasilAkhir;
        }

        return new ArrayList<>();
    }
}
