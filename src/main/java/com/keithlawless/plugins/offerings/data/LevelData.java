package com.keithlawless.plugins.offerings.data;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LevelData {
    private Integer level;
    private LinkedList<OfferingThreshold> thresholds;
    private String altarOpenMessage;

    private LevelData(Integer level) {
        this.level = level;
        thresholds = new LinkedList<>();
        altarOpenMessage = new String();
    }

    public LevelData(Integer level, Material material, Integer amount) {
        this(level);
        addThreshold(material, amount);
    }

    public LevelData(Integer level, Material material, Integer amount, String message) {
        this(level, material, amount);
        altarOpenMessage = new String(message);
    }

    public LevelData(Integer level, OfferingThreshold[] thresholdsAsArray) {
        this(level);
        List<OfferingThreshold> list = Arrays.asList(thresholdsAsArray);
        thresholds.addAll(list);
    }

    public LevelData(Integer level, OfferingThreshold[] thresholdsAsArray, String message) {
        this(level, thresholdsAsArray);
        altarOpenMessage = new String(message);
    }

    public void addThreshold(Material material, Integer amount) {
        if(thresholds != null) {
            OfferingThreshold offeringThreshold = new OfferingThreshold(material, amount);
            thresholds.push(offeringThreshold);
        }
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getThresholdCount() {
        return thresholds.size();
    }

    public OfferingThreshold getThreshold(Integer index) {
        return thresholds.get(index);
    }

    public String getAltarOpenMessage() {
        return altarOpenMessage;
    }
}
