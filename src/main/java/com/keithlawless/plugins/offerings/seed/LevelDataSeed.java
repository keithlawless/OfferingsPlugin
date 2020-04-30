package com.keithlawless.plugins.offerings.seed;

import com.keithlawless.plugins.offerings.data.LevelData;
import org.bukkit.Material;

import java.util.LinkedList;

public class LevelDataSeed {
    private LinkedList<LevelData> levelSeeds;

    public LevelDataSeed() {
        levelSeeds = new LinkedList<>();

        LevelData levelData = new LevelData(0, Material.AIR, 0,
                "Offer 5 Oak Planks to advance to Level One.");
        levelSeeds.add(levelData);

        levelData = new LevelData(1, Material.OAK_PLANKS, 5,
                "Offer 5 Coal to advance to level Two.");
        levelSeeds.add(levelData);

        levelData = new LevelData(2, Material.COAL, 5,
                "Offer 1 Iron Ingot to advance to level Three.");
        levelSeeds.add(levelData);

        levelData = new LevelData(3, Material.IRON_INGOT, 1,
                "Offer 1 Bone to advance to level Four.");
        levelSeeds.add(levelData);

        levelData = new LevelData(4, Material.BONE, 1,
                "You are at the highest level.");
        levelSeeds.add(levelData);
    }

    public LinkedList<LevelData> getLevelSeeds() {
        return levelSeeds;
    }

    public int getLevelCount() {
        return levelSeeds.size() -1 ;
    }
}
