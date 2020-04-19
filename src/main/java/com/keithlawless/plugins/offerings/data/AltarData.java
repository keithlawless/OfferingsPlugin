package com.keithlawless.plugins.offerings.data;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.util.Arrays;

public class AltarData {

    private static AltarData singleton = null;

    private Material[] altarMaterials = new Material[]{Material.CHEST};

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

    public boolean materialCanBeAnAltar(Material material) {
        return Arrays.asList(altarMaterials).contains(material);
    }

    // Ideally, this method should only be called by your unit tests.
    public void destroy() {
        if(singleton != null) {
            singleton = null;
        }
    }

}
