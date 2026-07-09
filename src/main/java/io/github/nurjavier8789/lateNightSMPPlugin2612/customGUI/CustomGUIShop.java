package io.github.nurjavier8789.lateNightSMPPlugin2612.customGUI;

import io.github.nurjavier8789.lateNightSMPPlugin2612.model.shopModels;
import io.github.nurjavier8789.lateNightSMPPlugin2612.dataSaver.shopDatabase;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomGUIShop {
    private final DecimalFormat formatter = new DecimalFormat("#,###");

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

    public ItemStack arrowControl(String text) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.itemName(Component.text(text));
        meta.setRarity(ItemRarity.UNCOMMON);

        item.setItemMeta(meta);

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
        ItemStack item = new ItemStack(theItem, 1);
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("§fHarga Beli: §cRp" + formatter.format(hargaBeli)));
        lore.add(Component.text("§fHarga Jual: §aRp" + formatter.format(hargaJual)));
        lore.add(Component.text(""));
        lore.add(Component.text("§e[Klik Kiri] §7untuk membeli!"));
        lore.add(Component.text("§e[Shift + Klik Kiri] §7untuk membeli banyak!"));
        lore.add(Component.text("§6[Klik Kanan] §7untuk menjual!"));
        lore.add(Component.text("§6[Shift + Klik Kanan] §7untuk menjual semua!"));
        meta.lore(lore);

        item.setItemMeta(meta);
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
        shopGui.setItem(12, initItemShop(Material.GOLD_INGOT, 75000, 50000));
        shopGui.setItem(13, initItemShop(Material.DIAMOND, 50000, 25000));
        shopGui.setItem(14, initItemShop(Material.NETHERITE_SCRAP, 1000000, 450000));

        player.openInventory(shopGui);
    }

    public void bulkBuyShopGUI(Player player, shopModels item) {
        Inventory newGUI = Bukkit.createInventory(player, 54, Component.text("§8Beli Banyak: " + item.convertItemName()));

        // Items List
        newGUI.setItem(19, listBulkItem(item.getMaterial(), 8, item.getHargaBeli() * 8, "buy"));
        newGUI.setItem(21, listBulkItem(item.getMaterial(), 16, item.getHargaBeli() * 16, "buy"));
        newGUI.setItem(23, listBulkItem(item.getMaterial(), 32, item.getHargaBeli() * 32, "buy"));
        newGUI.setItem(25, listBulkItem(item.getMaterial(), 64, item.getHargaBeli() * 64, "buy"));

        // Control Page
        newGUI.setItem(53, closeButton());
        newGUI.setItem(45, arrowControl("Kembali"));

        // Filler
        ItemStack fillerSlot = borderShop();
        for (int i = 0; i < newGUI.getSize(); i++) {
            if (newGUI.getItem(i) == null) {
                newGUI.setItem(i, fillerSlot);
            }
        }

        player.openInventory(newGUI);
    }

    private ItemStack listBulkItem(Material mat, int jumlah, double totalHarga, String isSellOrBuy) {
        ItemStack item = new ItemStack(mat, jumlah);
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = new ArrayList<>();
        if (Objects.equals(isSellOrBuy, "buy")) {
            lore.add(Component.text(""));
            lore.add(Component.text("§fTotal Harga: §cRp " + formatter.format(totalHarga)));
            lore.add(Component.text("§eKlik untuk membeli!"));
        } else {
            lore.add(Component.text(""));
            lore.add(Component.text("§fTotal Harga: §aRp " + formatter.format(totalHarga)));
            lore.add(Component.text("§eKlik untuk menjual!"));
        }
        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void proccessSellAllSpecificItem(Player player, Material material, double hargaJualSatuan, shopModels items) {
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

        Inventory guiJual = Bukkit.createInventory(player, 54, Component.text("§8Konfirmasi Jual: " + items.convertItemName()));

        ItemStack jualSemua = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta metaSemua = jualSemua.getItemMeta();
        metaSemua.displayName(Component.text("§a§lJUAL SEMUA " + itemInInventory + " Item"));

        List<Component> loreSemua = new ArrayList<>();
        loreSemua.add(Component.text(""));
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

//        guiJual.setItem(30, jualSemua);
//        guiJual.setItem(32, jualCustom);

        // Items List
        guiJual.setItem(10, listBulkItem(items.getMaterial(), 8, items.getHargaJual() * 8, "sell"));
        guiJual.setItem(12, listBulkItem(items.getMaterial(), 16, items.getHargaJual() * 16, "sell"));
        guiJual.setItem(14, listBulkItem(items.getMaterial(), 32, items.getHargaJual() * 32, "sell"));
        guiJual.setItem(16, listBulkItem(items.getMaterial(), 64, items.getHargaJual() * 64, "sell"));

        // Control Page
        guiJual.setItem(53, closeButton());
        guiJual.setItem(45, arrowControl("Kembali"));

        // Filler
        ItemStack fillerSlot = borderShop();
        for (int i = 0; i < guiJual.getSize(); i++) {
            if (guiJual.getItem(i) == null) {
                guiJual.setItem(i, fillerSlot);
            }
        }

        player.openInventory(guiJual);
    }
}
