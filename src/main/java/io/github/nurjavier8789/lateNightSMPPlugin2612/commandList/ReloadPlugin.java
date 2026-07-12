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
        if (!sender.hasPermission("latenightsmpplugin.reload")) {
            sender.sendMessage("§cYou can't access this command! It's a no no!");
            return true;
        }

        plugin.reloadConfig();
        
        sender.sendMessage("§a[Late Night SMP Plugin] Plugin reloaded!");
        
        return true;
    }
}
