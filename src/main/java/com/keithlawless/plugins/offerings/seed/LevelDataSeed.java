package com.keithlawless.plugins.offerings.seed;

import com.keithlawless.plugins.offerings.data.LevelData;
import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.logging.Level;

public class LevelDataSeed {
    private LinkedList<LevelData> levelSeeds;

    public LevelDataSeed() {
        levelSeeds = new LinkedList<>();

        LevelData levelData = new LevelData(1, Material.OAK_PLANKS, 5);
        levelSeeds.push(levelData);

        levelData = new LevelData(2, Material.COAL, 5);
        levelSeeds.push(levelData);

        levelData = new LevelData(3, Material.IRON_BARS, 1);
        levelSeeds.push(levelData);
    }

    public LinkedList<LevelData> getLevelSeeds() {
        return levelSeeds;
    }
}
