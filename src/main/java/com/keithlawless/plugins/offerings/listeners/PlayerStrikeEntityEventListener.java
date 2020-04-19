package com.keithlawless.plugins.offerings.listeners;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.commands.AltarSetCallback;
import com.keithlawless.plugins.offerings.data.AltarData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerStrikeEntityEventListener implements Listener {
    private OfferingsPlugin offeringsPlugin;
    private boolean isActive = false;
    private AltarSetCallback altarSetCallback;

    public PlayerStrikeEntityEventListener(OfferingsPlugin offeringsPlugin) {
        this.offeringsPlugin = offeringsPlugin;
        this.isActive = false;
        this.altarSetCallback = null;
        offeringsPlugin.getServer().getPluginManager().registerEvents(this,offeringsPlugin);
    }

    @EventHandler
    public void playerStrikeEntity(PlayerInteractEvent playerInteractEvent) {

        // If no one is holding the "magic wand", just exit.
        if(!isActive) {
            return;
        }

        // We only care about "strikes", check the Action
        if(playerInteractEvent.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Player player = playerInteractEvent.getPlayer();
        AltarData altarData = AltarData.getInstance();

        /*
        Make sure the player is the Active Altar Setter, and is holding the "magic wand",
        a.k.a. a Wooden Sword.
         */
        if(altarData.existsActiveAltarSetter() && altarData.activeAltarSetter == player) {
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if(heldItem.getType() == Material.WOODEN_SWORD) {
                // If the struck object can be an altar, it becomes the new altar.
                //TODO: Don't allow if altar is not empty of blocks (since we will clear all blocks "offered").
                if(playerInteractEvent.hasBlock() && altarData.materialCanBeAnAltar(playerInteractEvent.getClickedBlock().getType())) {
                    altarData.altarBlock = playerInteractEvent.getClickedBlock();
                    player.sendMessage("You have set the new altar.");
                    this.altarSetCallback.altarSet();
                    //TODO: Take the "magic wand" away after a successful altar set action.
                }
            }
        }
    }

    public void activate(AltarSetCallback altarSetCallback) {
        this.isActive = true;
        this.altarSetCallback = altarSetCallback;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
