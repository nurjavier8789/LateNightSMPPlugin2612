package io.github.nurjavier8789.lateNightSMPPlugin2612.dataSaver;

import io.github.nurjavier8789.lateNightSMPPlugin2612.model.shopModels;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class shopDatabase {
    private final Map<Material, shopModels> daftarBarang = new HashMap<>();

    public shopDatabase() {
        daftarBarang.put(Material.IRON_INGOT, new shopModels(Material.IRON_INGOT, 5000, 2500));
        daftarBarang.put(Material.COPPER_INGOT, new shopModels(Material.COPPER_INGOT, 10000, 5000));
        daftarBarang.put(Material.GOLD_INGOT, new shopModels(Material.GOLD_INGOT, 75000, 50000));
        daftarBarang.put(Material.DIAMOND, new shopModels(Material.DIAMOND, 50000, 25000));
        daftarBarang.put(Material.NETHERITE_SCRAP, new shopModels(Material.NETHERITE_SCRAP, 1000000, 450000));
    }

    public boolean alreadyExist(Material material) {
        return daftarBarang.containsKey(material);
    }

    public shopModels getItemForShop(Material material) {
        return daftarBarang.get(material);
    }
}
