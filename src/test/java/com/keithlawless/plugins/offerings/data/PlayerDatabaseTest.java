package com.keithlawless.plugins.offerings.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.keithlawless.plugins.offerings.OfferingsPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerDatabaseTest {

    private ServerMock server;
    private OfferingsPlugin plugin;

    @Before
    public void setUp() throws Exception {
        server = MockBukkit.mock();
        plugin = (OfferingsPlugin) MockBukkit.load(OfferingsPlugin.class);
    }

    @After
    public void tearDown() throws Exception {
        AltarData.getInstance().destroy();
        MockBukkit.unload();
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getPlayer() {
    }
}