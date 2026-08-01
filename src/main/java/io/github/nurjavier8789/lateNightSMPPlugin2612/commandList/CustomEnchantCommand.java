package io.github.nurjavier8789.lateNightSMPPlugin2612.commandList;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class CustomEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§7[§bLate Night SMP Plugin§7] §fCommand ini hanya untuk pemain!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("latenightsmpplugin.admin.customenchant")) {
            player.sendMessage("§7[§bLate Night SMP Plugin§7] §cNeed custom enchant? Go get yourself!!");
            return true;
        }

        if (args.length > 0 && args[1].equalsIgnoreCase("smelting_touch")) {
            ItemStack onHandItem = player.getInventory().getItemInMainHand();

            if (onHandItem.getType().isAir()) {
                player.sendMessage("§7[§bLate Night SMP Plugin§7] §cMohon pegang pickaxe atau book di tangan anda!");
                return true;
            }

            boolean isBook = false;
            if (onHandItem.getType() == org.bukkit.Material.BOOK) {
                ItemStack enchantBook = new ItemStack(org.bukkit.Material.ENCHANTED_BOOK, onHandItem.getAmount());
                
                onHandItem = enchantBook; 
                isBook = true;
            }

            ItemMeta meta = onHandItem.getItemMeta();
            List<Component> lore = meta.hasLore() ? meta.lore() : new ArrayList<>();

            boolean hasIt = false;
            if (meta.hasLore()) {
                for (Component lines : meta.lore()) {
                    String plainText = PlainTextComponentSerializer.plainText().serialize(lines);
                    if (plainText.contains("Smelting Touch I")) {
                        hasIt = true;
                        break;
                    }
                }
            }

            if (hasIt) {
                player.sendMessage("§7[§bLate Night SMP Plugin§7] §cSudah ada mas mbak!");
                return true;
            }

            Component enchantText = Component.text("Smelting Touch I").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false);

            lore.add(enchantText);
            meta.lore(lore);
            meta.setEnchantmentGlintOverride(true);
            onHandItem.setItemMeta(meta);
            if (isBook) {
                player.getInventory().setItemInMainHand(onHandItem);
            }

            player.sendMessage("§7[§bLate Night SMP Plugin§7] §aDone!");
            return true;
        }

        player.sendMessage("§7[§bLate Night SMP Plugin§7] §f Use: §b/lnsmpp customenchant <nama_enchant>");
        return true;
    }
}
