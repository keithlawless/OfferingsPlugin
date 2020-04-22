package com.keithlawless.plugins.offerings.data;

import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;

public class AltarData {

    private static AltarData singleton = null;

    private AltarData() {
        activeAltarSetter = null;
        altarBlock = null;
    }

    public static AltarData getInstance() {
        if(singleton == null) {
            singleton = new AltarData();
        }
        return singleton;
    }

    public Player activeAltarSetter;
    public Block altarBlock;

    public boolean existsActiveAltarSetter() {
        return (!(activeAltarSetter == null));
    }

    public boolean materialCanBeAnAltar(Block block) {
        return (block.getState() instanceof Container);
    }

    // Ideally, this method should only be called by your unit tests.
    public void destroy() {
        if(singleton != null) {
            singleton = null;
        }
    }

}
