package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import io.github.nurjavier8789.lateNightSMPPlugin2612.customGUI.CustomGUIShop;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CustomGUIShop customGUIShop = new CustomGUIShop();

        if (sender instanceof Player player) {
            customGUIShop.shopInit(player);

            return true;
        } else {
            sender.sendMessage("Hanya pemain yang bisa membuka Toko!");
            return true;
        }
    }
}
