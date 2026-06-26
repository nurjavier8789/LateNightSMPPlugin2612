package io.github.nurjavier8789.lateNightSMPPlugin2612.customGUI;

import io.github.nurjavier8789.lateNightSMPPlugin2612.dataSaver.shopDatabase;
import io.github.nurjavier8789.lateNightSMPPlugin2612.model.shopModels;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomGUIShop {
    public ItemStack borderShop() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.displayName(Component.empty());
            meta.setHideTooltip(true);

            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack closeButton() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        meta.itemName(Component.text("Tutup"));
        meta.setRarity(ItemRarity.UNCOMMON);

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack initItemShop(Material theItem, int hargaBeli, int hargaJual) {
        shopDatabase shopdatabase = new shopDatabase();

        ItemStack item = new ItemStack(theItem, 1);
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("§fHarga Beli: §cRp" + hargaBeli));
        lore.add(Component.text("§fHarga Jual: §aRp" + hargaJual));
        lore.add(Component.text(""));
        lore.add(Component.text("§e[Klik Kiri] §7untuk membeli!"));
        lore.add(Component.text("§e[Shift + Klik Kiri] §7untuk membeli banyak!"));
        lore.add(Component.text("§6[Klik Kanan] §7untuk menjual!"));
        lore.add(Component.text("§6[Shift + Klik Kanan] §7untuk menjual semua!"));
        meta.lore(lore);

        item.setItemMeta(meta);

        shopdatabase.putItemOnShop(theItem, hargaBeli, hargaJual);

        return item;
    }

    public void shopInit(Player player) {
        Inventory shopGui = Bukkit.createInventory(player, 54, Component.text("§8Toko Madura Admin 24 Jam"));

        // Border Shop
        shopGui.setItem(0, borderShop());
        shopGui.setItem(1, borderShop());
        shopGui.setItem(2, borderShop());
        shopGui.setItem(3, borderShop());
        shopGui.setItem(4, borderShop());
        shopGui.setItem(5, borderShop());
        shopGui.setItem(6, borderShop());
        shopGui.setItem(7, borderShop());
        shopGui.setItem(8, borderShop());
        shopGui.setItem(9, borderShop());
        shopGui.setItem(17, borderShop());
        shopGui.setItem(18, borderShop());
        shopGui.setItem(26, borderShop());
        shopGui.setItem(27, borderShop());
        shopGui.setItem(35, borderShop());
        shopGui.setItem(36, borderShop());
        shopGui.setItem(44, borderShop());
        shopGui.setItem(45, borderShop());
        shopGui.setItem(46, borderShop());
        shopGui.setItem(47, borderShop());
        shopGui.setItem(48, borderShop());
        shopGui.setItem(50, borderShop());
        shopGui.setItem(51, borderShop());
        shopGui.setItem(52, borderShop());
        shopGui.setItem(53, borderShop());

        // Control Page
        shopGui.setItem(49, closeButton());

        // Item List
        shopGui.setItem(10, initItemShop(Material.IRON_INGOT, 5000, 2500));
        shopGui.setItem(11, initItemShop(Material.COPPER_INGOT, 10000, 5000));
        shopGui.setItem(12, initItemShop(Material.GOLD_INGOT, 30000, 15000));
        shopGui.setItem(13, initItemShop(Material.DIAMOND, 50000, 25000));
        shopGui.setItem(14, initItemShop(Material.NETHERITE_INGOT, 1000000, 450000));

        player.openInventory(shopGui);
    }

    public void bulkBuyShopGUI(Player player, shopModels item) {
        Inventory newGUI = Bukkit.createInventory(player, 54, Component.text("§8Beli Banyak: " + item.getMaterial().name()));

        newGUI.setItem(10, listBulkItem(item.getMaterial(), 8, 5000 * 8));
        newGUI.setItem(12, listBulkItem(item.getMaterial(), 16, 5000 * 16));
        newGUI.setItem(14, listBulkItem(item.getMaterial(), 32, 5000 * 32));
        newGUI.setItem(16, listBulkItem(item.getMaterial(), 64, 5000 * 64));

        player.openInventory(newGUI);
    }

    private ItemStack listBulkItem(Material mat, int jumlah, double totalHarga) {
        ItemStack item = new ItemStack(mat, jumlah);
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Beli " + jumlah + "x"));
        lore.add(Component.text(""));
        lore.add(Component.text("§fTotal Harga: §cRp " + totalHarga));
        lore.add(Component.text("§eKlik untuk membeli!"));
        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void proccessSellAllSpecificItem(Player player, Material material, double hargaJualSatuan) {
        int itemInInventory = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                itemInInventory += item.getAmount();
            }
        }

        if (itemInInventory == 0) {
            player.sendMessage("§cKamu tidak memiliki barang ini di inventory-mu untuk dijual!");
            return;
        }

        double totalSellPrice = itemInInventory * hargaJualSatuan;

        Inventory guiJual = Bukkit.createInventory(player, 54, Component.text("§8Konfirmasi Jual: " + material.name()));

        ItemStack jualSemua = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta metaSemua = jualSemua.getItemMeta();
        metaSemua.displayName(Component.text("§a§lJUAL SEMUA " + itemInInventory + " Item"));

        List<Component> loreSemua = new ArrayList<>();
        loreSemua.add(Component.text("§fTotal Didapat: §a+Rp " + totalSellPrice));
        loreSemua.add(Component.text("§7Ini akan mengosongkan"));
        loreSemua.add(Component.text("§7semua item tersebut dari tasmu."));
        metaSemua.lore(loreSemua);
        jualSemua.setItemMeta(metaSemua);

        ItemStack jualCustom = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta metaCustom = jualCustom.getItemMeta();
        metaCustom.displayName(Component.text("§e§lATUR JUMLAH JUAL"));

        List<Component> loreCustom = new ArrayList<>();
        loreCustom.add(Component.text("§7Klik untuk menjual"));
        loreCustom.add(Component.text("§7jumlah barang secara spesifik."));
        metaCustom.lore(loreCustom);
        jualCustom.setItemMeta(metaCustom);

        guiJual.setItem(11, jualSemua);
        guiJual.setItem(15, jualCustom);

        player.openInventory(guiJual);
    }
}
