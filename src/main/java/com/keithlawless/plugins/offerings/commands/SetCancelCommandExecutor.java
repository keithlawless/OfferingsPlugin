package com.keithlawless.plugins.offerings.commands;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.AltarData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCancelCommandExecutor implements CommandExecutor {

    private final OfferingsPlugin offeringsPlugin;

    public SetCancelCommandExecutor(OfferingsPlugin offeringsPlugin) {
        this.offeringsPlugin = offeringsPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("The offerings-set-cancel command can only be executed by a player.");
            return false;
        }

        Player player = (Player)sender;

        // Get the AltarData singleton.
        AltarData altarData = AltarData.getInstance();

        Player currentPlayer = null;
        if(altarData.existsActiveAltarSetter()) {
            currentPlayer = altarData.activeAltarSetter;
        }

        altarData.activeAltarSetter = null;

        if(!(currentPlayer == null)) {
            if(currentPlayer.equals(player)) {
                player.sendMessage("You have cancelled the Set Altar command for yourself.");
            }
            else {
                player.sendMessage(String.format("You have cancelled the Set Altar command for %s.", currentPlayer.getDisplayName()));
                //TODO: Check if currentPlayer is still online before sending a message
                currentPlayer.sendMessage(String.format("Set Altar command was cancelled by %s.", player.getDisplayName()));
            }
        }
        else {
            player.sendMessage("No one was using the Set Altar command. No action taken.");
        }

        return false;
    }
}
