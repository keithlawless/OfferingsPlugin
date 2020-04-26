package com.keithlawless.plugins.offerings.data;

import org.bukkit.Material;

import java.time.LocalDateTime;

public class OfferingThreshold {
    // Offering Threshold should be immutable, so only provide getters to class attributes.
    private Material material;
    private Integer quantity;

    // Force player to use one of the supported constructors.
    protected OfferingThreshold() {}

    public OfferingThreshold(Material material, Integer quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
