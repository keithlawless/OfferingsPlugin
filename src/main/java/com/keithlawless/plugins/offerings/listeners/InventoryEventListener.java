package com.keithlawless.plugins.offerings.listeners;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.*;
import com.keithlawless.plugins.offerings.tasks.ClearInventoryTask;
import com.keithlawless.plugins.offerings.tasks.PlayerLevelUpTask;
import com.keithlawless.plugins.offerings.util.PlayerMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class InventoryEventListener implements Listener {
    private OfferingsPlugin offeringsPlugin;

    private InventoryAction[]  interestingActionsSource = new InventoryAction[]{
            InventoryAction.PLACE_ALL,
            InventoryAction.PLACE_SOME,
            InventoryAction.PLACE_ONE};

    private List<InventoryAction> interestingActions;

    public InventoryEventListener(OfferingsPlugin offeringsPlugin) {
        this.offeringsPlugin = offeringsPlugin;
        offeringsPlugin.getServer().getPluginManager().registerEvents(this,offeringsPlugin);
        this.interestingActions = Arrays.asList(interestingActionsSource);
    }

    @EventHandler
    public void inventoryOpened(InventoryOpenEvent inventoryOpenEvent) {
        // Is there an active Altar? If not, no need to continue.
        AltarData altarData = AltarData.getInstance();
        Location altarLocation = altarData.getAltarLocation();
        if(altarLocation == null) {
            return;
        }

        Inventory inventory = inventoryOpenEvent.getInventory();

        // Is the Inventory involved in this event the altar? If not, no need to continue.
        if(false == inventory.getLocation().equals(altarLocation)) {
            return;
        }

        PlayerData playerData = PlayerDatabase.getInstance().getPlayer(inventoryOpenEvent.getPlayer().getUniqueId());
        Integer level = playerData.getLevel();

        LevelData levelData = LevelDatabase.getInstance().getLevelData(level);

        if(levelData.getAltarOpenMessage() != null) {
            inventoryOpenEvent.getPlayer().sendMessage(PlayerMessage.format(levelData.getAltarOpenMessage()));
        }
    }


    @EventHandler
    public void inventoryClicked(InventoryClickEvent inventoryClickEvent) {
        Logger logger = this.offeringsPlugin.getLogger();

        // Is there an active Altar? If not, no need to continue.
        AltarData altarData = AltarData.getInstance();
        Location altarLocation = altarData.getAltarLocation();
        if(altarLocation == null) {
            return;
        }

        Inventory inventory = inventoryClickEvent.getView().getTopInventory();

        // Was the Top Inventory what was clicked? If not, no need to continue.
        if(inventory == null || inventoryClickEvent.getClickedInventory() == null) {
            return;
        }

        if(false == inventoryClickEvent.getClickedInventory().equals(inventory)) {
            return;
        }

        // Is the Inventory involved in this event the altar? If not, no need to continue.
        if(false == inventory.getLocation().equals(altarLocation)) {
            return;
        }

        // If we are not placing Material, no need to continue.
        if( false == interestingActions.contains(inventoryClickEvent.getAction())) {
            return;
        }

        ItemStack offeredItemStack = inventoryClickEvent.getCursor();

        int quantity = 0;
        if(inventoryClickEvent.isRightClick()) {
            quantity = 1;
        }
        else if(inventoryClickEvent.isLeftClick()) {
            quantity = offeredItemStack.getAmount();
        }

        Material material = offeredItemStack.getType();

        // If the Material is AIR, exit.
        if(material.isAir()) {
            return;
        }

        PlayerData playerData = PlayerDatabase.getInstance().getPlayer(inventoryClickEvent.getWhoClicked().getUniqueId());
        playerData.addOffering(material,quantity);
        PlayerDatabase.getInstance().updatePlayer(playerData);

        // After the offering has been made, clear the inventory.
        ClearInventoryTask clearInventoryTask = new ClearInventoryTask(inventory);
        this.offeringsPlugin.getServer().getScheduler().runTask(offeringsPlugin, clearInventoryTask);

        String msg = " offered " + quantity + " of " + material.toString();
        String logMsg = "Player " + inventoryClickEvent.getWhoClicked().getName() + msg;
        String playerMsg = "You " + msg;

        logger.info(logMsg);

        inventoryClickEvent.getWhoClicked().sendMessage(PlayerMessage.format(playerMsg));

        // Check if the Player is ready to level up. Don't block this thread while we're doing that.
        PlayerLevelUpTask playerLevelUpTask = new PlayerLevelUpTask(offeringsPlugin, getPlayerByUuid(inventoryClickEvent.getWhoClicked().getUniqueId()));
        this.offeringsPlugin.getServer().getScheduler().runTask(offeringsPlugin, playerLevelUpTask);
    }

    public Player getPlayerByUuid(UUID uuid) {
        for(Player p : this.offeringsPlugin.getServer().getOnlinePlayers())
            if(p.getUniqueId().equals(uuid))
                return p;

        throw new IllegalArgumentException();
    }
}
