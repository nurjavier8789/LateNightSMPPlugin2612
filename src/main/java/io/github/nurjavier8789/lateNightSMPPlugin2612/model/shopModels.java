package io.github.nurjavier8789.lateNightSMPPlugin2612.model;

import org.bukkit.Material;

public class shopModels {
    private final Material material;
    private final double hargaBeli;
    private final double hargaJual;

    public shopModels(Material material, double hargaBeli, double hargaJual) {
        this.material = material;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public Material getMaterial() {
        return material;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public String convertItemName() {
        String realName = material.name();

        String[] words = realName.split("_");
        StringBuilder finalName = new StringBuilder();

        for (String word : words) {
            finalName.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return finalName.toString().trim();
    }
}
