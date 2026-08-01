package io.github.nurjavier8789.lateNightSMPPlugin2612.listener;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class EnchantListener implements Listener {
    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack pickaxe = player.getInventory().getItemInMainHand();

        if (!pickaxe.hasItemMeta() || !pickaxe.getItemMeta().hasLore()) {
            return;
        }

        boolean hasIt = false;
        for (Component line : pickaxe.getItemMeta().lore()) {
            String plainText = PlainTextComponentSerializer.plainText().serialize(line);

            if (plainText.contains("Smelting Touch I")) {
                hasIt = true;
                break;
            }
        }

        if (!hasIt) return;

        Block block = event.getBlock();
        Material ore = block.getType();
        Material hasilBakar = null;
        int exp = 0;

        if (ore == Material.IRON_ORE || ore == Material.DEEPSLATE_IRON_ORE) {
            hasilBakar = Material.IRON_INGOT;
            exp = 2;
        } else if (ore == Material.GOLD_ORE || ore == Material.DEEPSLATE_GOLD_ORE) {
            hasilBakar = Material.GOLD_INGOT;
            exp = 3;
        } else if (ore == Material.COPPER_ORE || ore == Material.DEEPSLATE_COPPER_ORE) {
            hasilBakar = Material.COPPER_INGOT;
            exp = 1;
        }

        if (hasilBakar != null) {
            event.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(hasilBakar));

            final int lastEXP = exp;
            
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class, orb -> orb.setExperience(lastEXP));
        }
    }

    @EventHandler
    public void whileEnchanting(EnchantItemEvent event) {
        ItemStack item = event.getItem();

        if (item.getType().toString().endsWith("PICKAXE")) {
            if (ThreadLocalRandom.current().nextInt(100) < 15) {
                ItemMeta meta = item.getItemMeta();
                List<Component> lore = meta.hasLore() ? meta.lore() : new ArrayList<>();
                Component enchantText = Component.text("Smelting Touch I").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false);

                lore.add(enchantText);
                meta.lore(lore);
                meta.setEnchantmentGlintOverride(true);
                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void padaSaatPakaiAnvil(PrepareAnvilEvent event) {
        ItemStack slotKiri = event.getInventory().getItem(0);
        ItemStack slotKanan = event.getInventory().getItem(1);

        if (slotKiri == null || slotKanan == null) return;

        if (slotKanan.hasItemMeta() && slotKanan.getItemMeta().hasLore()) {
            boolean tumbalPunyaSmelt = false;

            for (Component line : slotKanan.getItemMeta().lore()) {
                String plainText = PlainTextComponentSerializer.plainText().serialize(line);

                if (plainText.contains("Smelting Touch I")) {
                    tumbalPunyaSmelt = true;
                    break;
                }
            }

            if (tumbalPunyaSmelt) {
                ItemStack hasil = slotKiri.clone();
                ItemMeta meta = hasil.getItemMeta();

                List<Component> lore = meta.hasLore() ? meta.lore() : new ArrayList<>();

                boolean hasIt = false;
                for (Component line : lore) {
                    String plainText = PlainTextComponentSerializer.plainText().serialize(line);

                    if (plainText.contains("Smelting Touch I")) {
                        hasIt = true;
                        break;
                    }
                }

                if (!hasIt) {
                    Component enchantText = Component.text("Smelting Touch I").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false);

                    lore.add(enchantText);
                    meta.lore(lore);
                    meta.setEnchantmentGlintOverride(true);
                    hasil.setItemMeta(meta);

                    // Bukkit.getScheduler().runTask(plugin, () -> {});
                    event.setResult(hasil);
                    if (event.getView() instanceof AnvilView) {
                        AnvilView anvilView = (AnvilView) event.getView();

                        anvilView.setRepairCost(10);
                    }
                }
            }
        }
    }
}
