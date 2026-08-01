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
    private final ReloadPlugin reloadPlugin;
    private final LeaderboardCommand leaderboardCommand;
    private final ToggleSBCommand toggleSBCommand;
    private final HelpListCommand helpListCommand;
    private final ServerRestartCommand serverRestartCommand;
    private final EventCommand eventCommand; 
    private final CustomEnchantCommand customEnchantCommand; 

    public _MainCommand(JavaPlugin plugin) {
        this.reloadPlugin = new ReloadPlugin(plugin);
        this.leaderboardCommand = new LeaderboardCommand(plugin);
        this.toggleSBCommand = new ToggleSBCommand();
        this.helpListCommand = new HelpListCommand();
        this.serverRestartCommand = new ServerRestartCommand(plugin);
        this.eventCommand = new EventCommand(plugin);
        this.customEnchantCommand = new CustomEnchantCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eHello and welcome to §bLate Night SMP Indonesia Season 3§e!");
            sender.sendMessage("§eHope you guys enjoy!");
            sender.sendMessage("\n§eNeed anything? just ask player in here or on discord!");
            sender.sendMessage("§eWe have another command too! Check §b/lnsmpp help §efor more info!\n");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            return reloadPlugin.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("leaderboard")) {
            return leaderboardCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("sbtoggle")) {
            return toggleSBCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("help")) {
            return helpListCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("restartserver")) {
            return serverRestartCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("event")) {
            return eventCommand.onCommand(sender, cmd, label, args);
        } else if (args[0].equalsIgnoreCase("customenchant")) {
            return customEnchantCommand.onCommand(sender, cmd, label, args);
        }

        sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cUh-oh. I can't find the command that you sent! (。_。)");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> saran = new ArrayList<>();
        List<String> hasilAkhir = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("latenightsmpplugin.admin")) {
                saran.add("reload");
                saran.add("restartserver");
                saran.add("customenchant");
            }
            saran.add("help");
            saran.add("leaderboard");
            saran.add("sbtoggle");
            saran.add("event");

            StringUtil.copyPartialMatches(args[0], saran, hasilAkhir);
            Collections.sort(hasilAkhir);

            return hasilAkhir;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("restartserver") && sender.hasPermission("latenightsmpplugin.admin.restartserver")) {
                saran.add("cancel");
                saran.add("30");
                saran.add("60");
                saran.add("120");
            }

            if (args[0].equalsIgnoreCase("event") && sender.hasPermission("latenightsmpplugin.admin.event")) {
                saran.add("start");
                saran.add("stop");
            }

            if (args[0].equalsIgnoreCase("customenchant") && sender.hasPermission("latenightsmpplugin.admin.customenchant")) {
                saran.add("smelting_touch");
            }

            StringUtil.copyPartialMatches(args[1], saran, hasilAkhir);
            Collections.sort(hasilAkhir);
            
            return hasilAkhir;
        }

        return new ArrayList<>();
    }
}
