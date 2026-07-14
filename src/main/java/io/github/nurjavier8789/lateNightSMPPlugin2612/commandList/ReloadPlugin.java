package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadPlugin implements CommandExecutor {
    private final JavaPlugin plugin;

    public ReloadPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("latenightsmpplugin.admin.reload")) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §cYou can't access this command! It's a no no!");
            return true;
        }

        plugin.reloadConfig();
        
        sender.sendMessage("§7[§bLate Night SMP Plugin§7] §aPlugin reloaded!");
        
        return true;
    }
}
