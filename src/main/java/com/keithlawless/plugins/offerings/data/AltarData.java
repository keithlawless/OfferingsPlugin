package com.keithlawless.plugins.offerings.data;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;

public class AltarData {

    private static AltarData singleton = null;

    private AltarData() {
        activeAltarSetter = null;
        altarBlock = null;
        altarLocation = null;
    }

    public static AltarData getInstance() {
        if(singleton == null) {
            singleton = new AltarData();
        }
        return singleton;
    }

    private Player activeAltarSetter;
    private Block altarBlock;
    private Location altarLocation;

    public boolean existsActiveAltarSetter() {
        return (!(activeAltarSetter == null));
    }

    public boolean materialCanBeAnAltar(Block block) {
        return (block.getState() instanceof Container);
    }

    public void setAltar(Block block, Location location) {
        this.altarBlock = block;
        this.altarLocation = location;
    }

    public Player getActiveAltarSetter() {
        return activeAltarSetter;
    }

    public void setActiveAltarSetter(Player activeAltarSetter) {
        this.activeAltarSetter = activeAltarSetter;
    }

    public void clearActiveAltarSetter() {
        this.activeAltarSetter = null;
    }

    public Block getAltarBlock() {
        return altarBlock;
    }

    public Location getAltarLocation() {
        return altarLocation;
    }

    // Ideally, this method should only be called by your unit tests.
    public void destroy() {
        if(singleton != null) {
            singleton = null;
        }
    }
}
