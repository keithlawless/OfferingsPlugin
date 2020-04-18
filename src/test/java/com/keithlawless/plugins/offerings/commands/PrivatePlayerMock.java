package com.keithlawless.plugins.offerings.commands;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class PrivatePlayerMock extends PlayerMock {

    public String receivedMessage;

    public PrivatePlayerMock(ServerMock server, String playerName) {
        super(server, playerName);
    }

    @Override
    public void sendMessage(String message) {
        this.receivedMessage = message;
    }

    @Override
    public void sendMessage(String[] messages) {
        for( String s: messages ) {
            this.receivedMessage = s;
        }
    }

    @Override
    public void sendRawMessage(String message) {
        this.receivedMessage = message;
    }

}
