package com.keithlawless.plugins.offerings.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.AltarData;
import com.keithlawless.plugins.offerings.listeners.PlayerStrikeEntityEventListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class SetCancelCommandExecutorTest {

    private ServerMock server;
    private OfferingsPlugin plugin;
    private SetCancelCommandExecutor setCancelCommandExecutor;
    private SetAltarCommandExecutor setAltarCommandExecutor;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (OfferingsPlugin) MockBukkit.load(OfferingsPlugin.class);
        PlayerStrikeEntityEventListener playerStrikeEntityEventListener = new PlayerStrikeEntityEventListener(plugin);
        setCancelCommandExecutor = new SetCancelCommandExecutor(plugin, playerStrikeEntityEventListener);
        setAltarCommandExecutor = new SetAltarCommandExecutor(plugin, playerStrikeEntityEventListener);
    }

    @After
    public void tearDown() {
        AltarData.getInstance().destroy();
        MockBukkit.unload();
    }

    @Test
    public void testCheckIfAPlayer() {
        ConsoleMock consoleMock = new ConsoleMock();
        setCancelCommandExecutor.onCommand( consoleMock, null, null, null );
        assertThat(consoleMock.receivedMessage, equalTo("The offerings-set-cancel command can only be executed by a player."));
    }

    @Test
    public void testCancelForYourself() {
        PrivatePlayerMock player = new PrivatePlayerMock(server, "DavidRose");
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 0));
        setAltarCommandExecutor.onCommand(player, null, null, null);

        setCancelCommandExecutor.onCommand(player, null, null, null);

        assertThat(player.receivedMessage, equalTo("You have cancelled the Set Altar command for yourself."));
    }

    @Test
    public void testCancelForAnotherPlayer() {
        PrivatePlayerMock activePlayer = new PrivatePlayerMock(server, "DavidRose");
        activePlayer.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 0));
        setAltarCommandExecutor.onCommand(activePlayer, null, null, null);

        PrivatePlayerMock cancelPlayer = new PrivatePlayerMock(server, "AlexisRose");
        setCancelCommandExecutor.onCommand(cancelPlayer, null, null, null);

        assertThat(cancelPlayer.receivedMessage, equalTo(String.format("You have cancelled the Set Altar command for %s.", activePlayer.getDisplayName())));
        assertThat(activePlayer.receivedMessage, equalTo(String.format("Set Altar command was cancelled by %s.", cancelPlayer.getDisplayName())));
    }

    @Test
    public void testCancelForNoOne() {
        PrivatePlayerMock player = new PrivatePlayerMock(server, "MoiraRose");
        setCancelCommandExecutor.onCommand(player, null, null, null);

        assertThat(player.receivedMessage, equalTo("No one was using the Set Altar command. No action taken."));

    }

}