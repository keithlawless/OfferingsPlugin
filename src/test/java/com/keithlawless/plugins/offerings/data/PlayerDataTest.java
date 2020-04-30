package com.keithlawless.plugins.offerings.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.commands.PrivatePlayerMock;
import org.bukkit.Material;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PlayerDataTest {

    private ServerMock server;
    private OfferingsPlugin plugin;
    private PlayerData playerData;

    @Before
    public void setUp() throws Exception {
        server = MockBukkit.mock();
        plugin = (OfferingsPlugin) MockBukkit.load(OfferingsPlugin.class);
        PrivatePlayerMock privatePlayerMock = new PrivatePlayerMock(server, "Roland Schitt");
        this.playerData = new PlayerData(privatePlayerMock.getUniqueId());
    }

    @After
    public void tearDown() throws Exception {
        AltarData.getInstance().destroy();
        MockBukkit.unload();
    }

    @Test
    public void testAddOffering() {
    }

    @Test
    public void testAddOfferingMaterialQuantity() {
        playerData.addOffering(Material.OAK_DOOR, 4);
        playerData.addOffering(Material.ACACIA_FENCE, 10);
        playerData.addOffering(Material.IRON_BOOTS, 3);

        HashMap<Material,Integer> offerings = playerData.getOfferingsForCurrentLevel();

        assertThat(offerings.size(), equalTo(3));

    }

    @Test
    public void testGetLevel() {
    }

    @Test
    public void testGetOfferingsForCurrentLevel() {
    }

    @Test
    public void testLevelUp() {
    }

    @Test
    public void testDumpOfferings() {
    }
}