package com.keithlawless.plugins.offerings.data;

import org.bukkit.entity.Player;

public class AltarData {

    private static AltarData singleton = null;

    private AltarData() {
        activeAltarSetter = null;
    }

    public static AltarData getInstance() {
        if(singleton == null) {
            singleton = new AltarData();
        }
        return singleton;
    }

    public Player activeAltarSetter;

    public boolean existsActiveAltarSetter() {
        return (!(activeAltarSetter == null));
    }

    // Ideally, this method should only be called by your unit tests.
    public void destroy() {
        if(singleton != null) {
            singleton = null;
        }
    }
}
