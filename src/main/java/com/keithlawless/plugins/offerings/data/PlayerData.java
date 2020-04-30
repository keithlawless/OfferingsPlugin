package com.keithlawless.plugins.offerings.data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
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
        offerings.add(offering);
    }

    public void addOffering(Material material, Integer quantity) {
        Offering offering = new Offering(this.level, material, quantity, LocalDateTime.now());
        offerings.add(offering);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Integer getLevel() {
        return level;
    }

    public HashMap<Material,Integer> getOfferingsForCurrentLevel() {
        Integer existingQuantity = null;
        HashMap<Material,Integer> map = new HashMap<>();
        ListIterator<Offering> listIterator = offerings.listIterator();
        while(listIterator.hasNext()) {
            Offering offering = listIterator.next();
            if(offering.getLevel() == this.level) {
                existingQuantity = map.get(offering.getMaterial());
                if((existingQuantity == null) || (existingQuantity == 0))  {
                    map.put(offering.getMaterial(), offering.getQuantity());
                }
                else {
                    Integer newQuantity = existingQuantity + offering.getQuantity();
                    map.put(offering.getMaterial(), newQuantity);
                }
            }
        }
        return map;
    }

    public void levelUp() {
        level++;
    }

}
