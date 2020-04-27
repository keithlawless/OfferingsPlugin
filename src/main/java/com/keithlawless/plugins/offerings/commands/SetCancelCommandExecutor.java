package com.keithlawless.plugins.offerings.commands;

import com.keithlawless.plugins.offerings.OfferingsPlugin;
import com.keithlawless.plugins.offerings.data.AltarData;
import com.keithlawless.plugins.offerings.listeners.PlayerStrikeEntityEventListener;
import com.keithlawless.plugins.offerings.util.PlayerMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCancelCommandExecutor implements CommandExecutor {

    private final OfferingsPlugin offeringsPlugin;
    private final PlayerStrikeEntityEventListener playerStrikeEntityEventListener;

    public SetCancelCommandExecutor(OfferingsPlugin offeringsPlugin, PlayerStrikeEntityEventListener playerStrikeEntityEventListener) {
        this.offeringsPlugin = offeringsPlugin;
        this.playerStrikeEntityEventListener = playerStrikeEntityEventListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(PlayerMessage.format("The offerings-set-cancel command can only be executed by a player."));
            return false;
        }

        Player player = (Player)sender;

        // Get the AltarData singleton.
        AltarData altarData = AltarData.getInstance();

        Player currentPlayer = null;
        if(altarData.existsActiveAltarSetter()) {
            currentPlayer = altarData.getActiveAltarSetter();
        }

        altarData.clearActiveAltarSetter();

        if(!(currentPlayer == null)) {
            if(currentPlayer.equals(player)) {
                player.sendMessage(PlayerMessage.format("You have cancelled the Set Altar command for yourself."));
            }
            else {
                player.sendMessage(PlayerMessage.format(String.format("You have cancelled the Set Altar command for %s.", currentPlayer.getDisplayName())));
                //TODO: Check if currentPlayer is still online before sending a message
                currentPlayer.sendMessage(PlayerMessage.format(String.format("Set Altar command was cancelled by %s.", player.getDisplayName())));
            }

            // Deactivate the event listener that listens for player strikes.
            this.playerStrikeEntityEventListener.deactivate();
        }
        else {
            player.sendMessage(PlayerMessage.format("No one was using the Set Altar command. No action taken."));
        }

        return false;
    }
}
