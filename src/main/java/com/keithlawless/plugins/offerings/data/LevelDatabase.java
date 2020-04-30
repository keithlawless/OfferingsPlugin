package com.keithlawless.plugins.offerings.data;

import com.keithlawless.plugins.offerings.seed.LevelDataSeed;

import java.util.HashMap;

public class LevelDatabase {
    private static LevelDatabase singleton = null;
    private HashMap<Integer,LevelData> levelDatabase;

    private LevelDatabase() {
        levelDatabase = new HashMap<Integer, LevelData>();
    }

    public static LevelDatabase getInstance() {
        if(singleton == null) {
            singleton = new LevelDatabase();
        }
        return singleton;
    }

    public LevelData getLevelData(Integer level) {
        return levelDatabase.get(level);
    }

    public Integer getLevelCount() {
        return levelDatabase.size() - 1;
    }

    public void seed(LevelDataSeed levelDataSeed) {
        levelDatabase = new HashMap<>();
        for(LevelData levelData: levelDataSeed.getLevelSeeds()) {
            levelDatabase.put(levelData.getLevel(), levelData);
        }
    }
}
