package com.keithlawless.plugins.offerings.listeners;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.AltarData;
import com.keithlawless.plugins.offerings.data.PlayerData;
import com.keithlawless.plugins.offerings.data.PlayerDatabase;
import com.keithlawless.plugins.offerings.tasks.ClearInventoryTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.List;
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
        int quantity = offeredItemStack.getAmount();
        Material material = offeredItemStack.getType();

        // If the Material is AIR, exit.
        if(material.isAir()) {
            return;
        }

        PlayerData playerData = PlayerDatabase.getInstance().getPlayer(inventoryClickEvent.getWhoClicked().getUniqueId());
        playerData.addOffering(material,quantity);

        // After the offering has been made, clear the inventory.
        ClearInventoryTask clearInventoryTask = new ClearInventoryTask(inventory);
        this.offeringsPlugin.getServer().getScheduler().runTask(offeringsPlugin, clearInventoryTask);

        String msg = " offered " + quantity + " of " + material.toString();
        String logMsg = "Player " + inventoryClickEvent.getWhoClicked().getName() + msg;
        String playerMsg = "You " + msg;

        logger.info(logMsg);

        inventoryClickEvent.getWhoClicked().sendMessage(playerMsg);

    }
}
