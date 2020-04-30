package com.keithlawless.plugins.offerings.data;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDatabase {
    private static PlayerDatabase singleton = null;
    private HashMap<UUID,PlayerData> playerDatabase;

    private PlayerDatabase() {
        playerDatabase = new HashMap<>();
    }

    public static PlayerDatabase getInstance() {
        if(singleton == null) {
            singleton = new PlayerDatabase();
        }
        return singleton;
    }

    public PlayerData getPlayer(UUID uniqueID) {
        PlayerData playerData = playerDatabase.get(uniqueID);
        if(playerData == null) {
            playerData = new PlayerData(uniqueID);
        }

        return playerData;
    }

    public void updatePlayer(PlayerData playerData) {
        this.playerDatabase.put(playerData.getUniqueId(), playerData);
    }

}
