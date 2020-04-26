package com.keithlawless.plugins.offerings.data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Material;

public class PlayerData {
    private UUID uniqueId;
    private Integer level;
    private LinkedList<Offering> offerings;

    private PlayerData() {
        this.level = 0;
        this.offerings = new LinkedList<>();
    }

    public PlayerData(UUID uniqueId) {
        this();
        this.uniqueId = uniqueId;
    }

    public void addOffering(Offering offering) {
        offerings.push(offering);
    }

    public void addOffering(Material material, Integer quantity) {
        Offering offering = new Offering(this.level, material, quantity, LocalDateTime.now());
        offerings.push(offering);
    }

    public Integer getLevel() {
        return level;
    }
}
