package com.keithlawless.plugins.offerings.listeners;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerStrikeEntityEventListener implements Listener {
    private OfferingsPlugin offeringsPlugin;
    private boolean isActive = false;

    public PlayerStrikeEntityEventListener(OfferingsPlugin offeringsPlugin) {
        this.offeringsPlugin = offeringsPlugin;
        this.isActive = false;
        offeringsPlugin.getServer().getPluginManager().registerEvents(this,offeringsPlugin);
    }

    @EventHandler
    public void playerStrikeEntity(PlayerInteractEvent playerInteractEvent) {

        // If an admin isn't holding the magic wand, just exit.
        if(!isActive) {
            return;
        }

        //TODO: Check if the player is the active "altar setter" from the  AltarData singleton.
        //TODO: Check if the player is holding the wooden sword.
        if(playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = playerInteractEvent.getPlayer();
            player.sendMessage("Ow, that hurts!");
        }
    }

    //TODO: Accept a callback function so we can notify the command when the player actually strikes a chest, and the command can be cleared.
    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
