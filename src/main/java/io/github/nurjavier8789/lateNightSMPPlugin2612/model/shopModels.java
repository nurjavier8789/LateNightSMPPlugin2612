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
}
