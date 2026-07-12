package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ToggleSBCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§b[Late Night SMP] §fCommand ini hanya untuk pemain!");
            return true;
        }

        Player player = (Player) sender;
        player.performCommand("simplescore toggle");

        return true;
    }
}
