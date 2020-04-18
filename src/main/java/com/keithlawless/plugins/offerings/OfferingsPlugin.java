package com.keithlawless.plugins.offerings;

import com.keithlawless.plugins.offerings.commands.SetAltarCommandExecutor;
import com.keithlawless.plugins.offerings.commands.SetCancelCommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class OfferingsPlugin extends JavaPlugin {

    // Required for unit testing using MockBukkit
    public OfferingsPlugin() {
        super();
    }

    // Required for unit testing using MockBukkit
    protected OfferingsPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        this.getCommand("offerings-set-altar").setExecutor(new SetAltarCommandExecutor(this));
        this.getCommand("offerings-set-cancel").setExecutor(new SetCancelCommandExecutor(this));
    }

}
