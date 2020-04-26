package com.keithlawless.plugins.offerings.data;

import org.bukkit.Material;

import java.time.LocalDateTime;

public class Offering {

    // Offering should be immutable, so only provide getters to class attributes.
    private Integer level;
    private Material material;
    private Integer quantity;
    private LocalDateTime timestamp;

    // Force player to use one of the supported constructors.
    protected Offering() {}

    public Offering(Integer level, Material material, Integer quantity, LocalDateTime timestamp) {
        this.level = level;
        this.material = material;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public Integer getLevel() {
        return level;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
