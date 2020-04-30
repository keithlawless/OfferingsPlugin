package com.keithlawless.plugins.offerings.tasks;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.LevelDatabase;
import com.keithlawless.plugins.offerings.data.OfferingThreshold;
import com.keithlawless.plugins.offerings.data.PlayerData;
import com.keithlawless.plugins.offerings.data.PlayerDatabase;
import com.keithlawless.plugins.offerings.util.PlayerMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

public class PlayerLevelUpTask implements Runnable {

    OfferingsPlugin offeringsPlugin;
    private Player player;
    private LevelDatabase levelDatabase;
    private PlayerDatabase playerDatabase;

    public PlayerLevelUpTask(OfferingsPlugin offeringsPlugin, Player player) {
        this.offeringsPlugin = offeringsPlugin;
        this.player = player;
        this.levelDatabase = LevelDatabase.getInstance();
        this.playerDatabase = PlayerDatabase.getInstance();
    }

    public void run() {
        PlayerData playerData = playerDatabase.getPlayer(player.getUniqueId());
        Integer currentLevel = playerData.getLevel();

        if(currentLevel < levelDatabase.getLevelCount()) {
            boolean readyToLevelUp = true;
            HashMap<Material,Integer> map = playerData.getOfferingsForCurrentLevel();

            // Get the offerings needed to attain the *next* level.
            LinkedList<OfferingThreshold> offeringThresholds = levelDatabase.getLevelData(currentLevel+1).getThresholds();
            ListIterator<OfferingThreshold> listIterator = offeringThresholds.listIterator();
            while(listIterator.hasNext()) {
                OfferingThreshold offeringThreshold = listIterator.next();

                // How much of each required Material has the player offered?
                Integer quantity = map.get(offeringThreshold.getMaterial());
                if ((quantity == null) || (quantity < offeringThreshold.getQuantity())) {
                    readyToLevelUp = false;
                }
            }

            if(readyToLevelUp) {
                playerData.levelUp();
                player.sendMessage(PlayerMessage.format("You have advanced to level " + playerData.getLevel()));
            }
        }
    }
}
