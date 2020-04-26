package com.keithlawless.plugins.offerings.data;

import com.keithlawless.plugins.offerings.seed.LevelDataSeed;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class LevelDatabaseTest {

    private Integer levelCount = 0;

    @Before
    public void setUp() {
        LevelDatabase levelDatabase = LevelDatabase.getInstance();
        LevelDataSeed levelDataSeed = new LevelDataSeed();
        levelDatabase.seed(levelDataSeed);
        levelCount = levelDataSeed.getLevelCount();
    }

    @Test
    public void testGetInstance() {
        LevelDatabase levelDatabase = LevelDatabase.getInstance();
        assert(levelDatabase != null);
        assert(levelDatabase instanceof LevelDatabase);
    }

    @Test
    public void testGetLevelData() {
        LevelDatabase levelDatabase = LevelDatabase.getInstance();
        LevelData levelData = levelDatabase.getLevelData(1);
        assertThat(levelData.getLevel(), equalTo(1));
        assertThat(levelData.getThresholdCount(), equalTo(1));

        OfferingThreshold offeringThreshold = levelData.getThreshold(0);
        assertThat(offeringThreshold.getMaterial(), equalTo(Material.OAK_PLANKS));
        assertThat(offeringThreshold.getQuantity(), equalTo(5));
    }

    @Test
    public void testSeed() {
        LevelDatabase levelDatabase = LevelDatabase.getInstance();
        assertThat(levelDatabase.getLevelCount(), equalTo(this.levelCount));
    }
}