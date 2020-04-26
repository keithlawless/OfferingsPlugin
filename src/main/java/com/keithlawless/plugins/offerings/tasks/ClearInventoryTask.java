package com.keithlawless.plugins.offerings.tasks;

import org.bukkit.inventory.Inventory;

public class ClearInventoryTask implements Runnable {

    private Inventory inventory;

    public ClearInventoryTask(Inventory inventory) {
        this.inventory = inventory;

    }
    public void run() {
        inventory.clear();
    }
}
