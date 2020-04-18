package com.keithlawless.plugins.offerings.commands;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.AltarData;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetAltarCommandExecutor implements CommandExecutor {

    private final OfferingsPlugin offeringsPlugin;

    public SetAltarCommandExecutor(OfferingsPlugin offeringsPlugin) {
        this.offeringsPlugin = offeringsPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("The offerings-set-altar command can only be executed by a player.");
            return false;
        }

        Player player = (Player)sender;

        // Get the AltarData singleton.
        AltarData altarData = AltarData.getInstance();

        // If there is already an activeAltarSetter, display a message and exit.
        if(altarData.existsActiveAltarSetter()) {
            Player activeAltarSetter = altarData.activeAltarSetter;
            player.sendMessage(String.format("Player %s is already setting an altar.", activeAltarSetter.getDisplayName()));
            return false;
        }

        /*
        If the player's main hand is not empty, return a message and bail out. Since we are going to
        give the player a wooden sword, we don't want to risk them losing a held item.

        UNLESS, the player is already holding a wooden sword.
         */
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if(heldItem.getType() != Material.WOODEN_SWORD) {
            if (heldItem.getAmount() > 0) {
                player.sendMessage("You must not be holding any items in your main hand when using this command.");
                return false;
            }

            //Give the player a wooden sword.
            ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
            player.getInventory().setItemInMainHand(woodenSword);
        }

        // Make this player the current "activeAltarSetter"
        altarData.activeAltarSetter = player;
        player.sendMessage("Strike the chest you want to use as the Offerings Altar.");

        return false;
    }
}
