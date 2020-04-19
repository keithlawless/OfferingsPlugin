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

public class SetAltarCommandExecutorTest {

    private ServerMock server;
    private OfferingsPlugin plugin;
    private SetAltarCommandExecutor setAltarCommandExecutor;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (OfferingsPlugin) MockBukkit.load(OfferingsPlugin.class);
        setAltarCommandExecutor = new SetAltarCommandExecutor(plugin, new PlayerStrikeEntityEventListener(plugin));
    }

    @After
    public void tearDown() {
        AltarData.getInstance().destroy();
        MockBukkit.unload();
    }

    @Test
    public void testCheckIfAPlayer() {
        ConsoleMock consoleMock = new ConsoleMock();
        setAltarCommandExecutor.onCommand( consoleMock, null, null, null );
        assertThat(consoleMock.receivedMessage, equalTo("The offerings-set-altar command can only be executed by a player."));
    }

    @Test
    public void testAlreadyAnActiveSetter() {
        // Make Roland the activeAltarSetter
        String activeName = "RolandSchitt";
        PrivatePlayerMock currentActivePlayer = new PrivatePlayerMock(server, activeName);

        AltarData altarData = AltarData.getInstance();
        altarData.activeAltarSetter = currentActivePlayer;

        // Try to make Johnny the activeAltarSetter
        PrivatePlayerMock testPlayer = new PrivatePlayerMock(server, "JohnnyRose");
        setAltarCommandExecutor.onCommand(testPlayer, null, null, null);

        assertThat(testPlayer.receivedMessage, equalTo(String.format("Player %s is already setting an altar.", currentActivePlayer.getDisplayName())));
    }

    @Test
    public void testMainHandIsNotEmpty() {

        PrivatePlayerMock player = new PrivatePlayerMock(server, "MoiraRose");


        //Give the player a wooden sword.
        ItemStack diamondShovel = new ItemStack(Material.DIAMOND_SHOVEL);
        player.getInventory().setItemInMainHand(diamondShovel);

        setAltarCommandExecutor.onCommand(player, null, null, null);

        assertThat(player.receivedMessage, equalTo("You must not be holding any items in your main hand when using this command."));
    }

    @Test
    public void testMainHandIsHoldingWoodenSword() {
        PrivatePlayerMock player = new PrivatePlayerMock(server, "JocelynSchitt");

        //Give the player a wooden sword.
        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
        player.getInventory().setItemInMainHand(woodenSword);

        setAltarCommandExecutor.onCommand(player, null, null, null);

        assertThat(player.getDisplayName(), equalTo(AltarData.getInstance().activeAltarSetter.getDisplayName()));

    }

    @Test
    public void testPlayerIsNowActiveAltarSetter() {
        PrivatePlayerMock player = new PrivatePlayerMock(server, "DavidRose");
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 0));

        setAltarCommandExecutor.onCommand(player, null, null, null);

        assertThat(player.getDisplayName(), equalTo(AltarData.getInstance().activeAltarSetter.getDisplayName()));
    }

}