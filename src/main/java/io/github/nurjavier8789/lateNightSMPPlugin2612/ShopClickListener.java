package io.github.nurjavier8789.lateNightSMPPlugin2612;

import io.github.nurjavier8789.lateNightSMPPlugin2612.model.shopModels;
import io.github.nurjavier8789.lateNightSMPPlugin2612.dataSaver.shopDatabase;
import io.github.nurjavier8789.lateNightSMPPlugin2612.customGUI.CustomGUIShop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.EconomyResponse;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.text.DecimalFormat;

public class ShopClickListener implements Listener {
    private final DecimalFormat formatter = new DecimalFormat("#,###");
    CustomGUIShop customGUIShop = new CustomGUIShop();
    shopDatabase shopdatabase = new shopDatabase();

    public boolean isInvalidClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getClickedInventory() == null) {
            return true;
        }

        if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
            return true;
        }

        return event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || event.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE;
    }

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        String title = LegacyComponentSerializer.legacySection().serialize(event.getView().title());

        if (event.getView().title().equals(Component.text("§8Toko Madura Admin 24 Jam"))) {
            if (isInvalidClick(event)) return;

            Material clickedType = event.getCurrentItem().getType();

            if (clickedType == Material.BARRIER) {
                player.closeInventory();
            }

            if (shopdatabase.alreadyExist(clickedType)) {
                shopModels item = shopdatabase.getItemForShop(clickedType);
                String finalItemName = item.convertItemName();

                double hargaBeli = item.getHargaBeli();
                double hargaJual = item.getHargaJual();

                String textHargaBeli = formatter.format(item.getHargaBeli());
                String textHargaJual = formatter.format(item.getHargaJual());

                ClickType jenisKlik = event.getClick();

                if (jenisKlik == ClickType.LEFT) {
                    if (LateNightSMPPlugin2612.econ.getBalance(player) >= hargaBeli) {
                        EconomyResponse transaksi = LateNightSMPPlugin2612.econ.withdrawPlayer(player, hargaBeli);

                        if (transaksi.transactionSuccess()) {
                            player.getInventory().addItem(new ItemStack(item.getMaterial(), 1));

                            player.sendMessage("§c[- Rp" + textHargaBeli + "] §fBerhasil membeli 1 " + finalItemName + "!");
                        } else {
                            player.sendMessage("§cTransaksi gagal: " + transaksi.errorMessage);
                        }

                    } else {
                        double kurang = hargaBeli - LateNightSMPPlugin2612.econ.getBalance(player);
                        player.sendMessage("§cUangmu tidak cukup! Kamu butuh §eRp" + formatter.format(kurang) + " §clagi.");
                    }
                } else if (jenisKlik == ClickType.RIGHT) {
                    ItemStack barangJual = new ItemStack(item.getMaterial(), 1);

                    if (player.getInventory().containsAtLeast(barangJual, 1)) {
                        player.getInventory().removeItem(barangJual);

                        EconomyResponse transaksi = LateNightSMPPlugin2612.econ.depositPlayer(player, hargaJual);

                        if (transaksi.transactionSuccess()) {
                            player.sendMessage("§a[+ Rp" + textHargaJual + "] §fBerhasil menjual 1 "+ finalItemName +"!");
                        }
                    } else {
                        player.sendMessage("§cKamu tidak memiliki " + finalItemName + " di inventory-mu untuk dijual!");
                    }
                } else if (jenisKlik == ClickType.SHIFT_LEFT) {
                    customGUIShop.bulkBuyShopGUI(player, item);
                } else if (jenisKlik == ClickType.SHIFT_RIGHT) {
                    customGUIShop.proccessSellAllSpecificItem(player, clickedType, hargaJual, item);
                }
            }
        } else if (title.contains("§8Beli Banyak:")) {
            if (isInvalidClick(event)) return;

            Material clickedType = event.getCurrentItem().getType();

            if (clickedType == Material.BARRIER) {
                player.closeInventory();
            }

            if (clickedType == Material.ARROW) {
                customGUIShop.shopInit(player);
            }

            if (shopdatabase.alreadyExist(clickedType)) {
                shopModels item = shopdatabase.getItemForShop(clickedType);
                String finalItemName = item.convertItemName();

                double hargaBeli = item.getHargaBeli();

                ClickType jenisKlik = event.getClick();

                if (jenisKlik == ClickType.LEFT) {
                    int jumlahBeli = event.getCurrentItem().getAmount();
                    double hargaTotal = hargaBeli * jumlahBeli;

                    if (LateNightSMPPlugin2612.econ.getBalance(player) >= hargaTotal) {
                        EconomyResponse transaksi = LateNightSMPPlugin2612.econ.withdrawPlayer(player, hargaTotal);

                        if (transaksi.transactionSuccess()) {
                            player.getInventory().addItem(new ItemStack(item.getMaterial(), jumlahBeli));

                            player.sendMessage("§c[- Rp" + formatter.format(hargaTotal) + "] §fBerhasil membeli " + jumlahBeli + " " + finalItemName + "!");
                        } else {
                            player.sendMessage("§cTransaksi gagal: " + transaksi.errorMessage);
                        }

                    } else {
                        double kurang = hargaTotal - LateNightSMPPlugin2612.econ.getBalance(player);
                        player.sendMessage("§cUangmu tidak cukup! Kamu butuh §eRp" + formatter.format(kurang) + " §clagi.");
                    }
                }
            }
        } else if (title.contains("§8Konfirmasi Jual:")) {
            if (isInvalidClick(event)) return;

            Material clickedType = event.getCurrentItem().getType();

            if (clickedType == Material.BARRIER) {
                player.closeInventory();
            }

            if (clickedType == Material.ARROW) {
                customGUIShop.shopInit(player);
            }

            if (shopdatabase.alreadyExist(clickedType)) {
                shopModels item = shopdatabase.getItemForShop(clickedType);
                String finalItemName = item.convertItemName();

                double hargajual = item.getHargaJual();

                ClickType jenisKlik = event.getClick();

                if (jenisKlik == ClickType.LEFT) {
                    ItemStack barangJual = new ItemStack(item.getMaterial());
                    if (clickedType == Material.EMERALD_BLOCK) {
                        int itemInInventory = 0;

                        for (ItemStack itemstack : player.getInventory().getContents()) {
                            if (itemstack != null && itemstack.getType() == clickedType) {
                                itemInInventory += itemstack.getAmount();
                            }
                        }

                        int jumlahJual = itemInInventory;
                        double hargaTotal = hargajual * jumlahJual;

                        player.getInventory().removeItem(barangJual);
                        EconomyResponse transaksi = LateNightSMPPlugin2612.econ.depositPlayer(player, hargaTotal);

                        if (transaksi.transactionSuccess()) {
                            player.sendMessage("§a[+ Rp" + formatter.format(hargaTotal) + "] §fBerhasil menjual " + jumlahJual + " "+ finalItemName +"!");
                        }
                    } else if (clickedType == item.getMaterial()) {
                        int jumlahJual = event.getCurrentItem().getAmount();
                        double hargaTotal = hargajual * jumlahJual;

                        if (player.getInventory().containsAtLeast(barangJual, jumlahJual)) {
                            player.getInventory().removeItem(new ItemStack(item.getMaterial(), jumlahJual));
                            EconomyResponse transaksi = LateNightSMPPlugin2612.econ.depositPlayer(player, hargaTotal);

                            if (transaksi.transactionSuccess()) {
                                player.sendMessage("§a[+ Rp" + formatter.format(hargaTotal) + "] §fBerhasil menjual " + jumlahJual + " "+ finalItemName +"!");
                            }
                        } else {
                            player.sendMessage("§cKamu tidak memiliki cukup " + finalItemName + " di inventory-mu untuk dijual!");
                        }
                    }
                }
            }
        }
    }
}
