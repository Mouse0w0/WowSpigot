package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.network.NetworkManager;
import com.github.mouse0w0.wowspigot.network.SpigotNetworkManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WowSpigot extends JavaPlugin {

    private static WowSpigot instance;
    private static SpigotNetworkManager network;

    public WowSpigot() {
        instance = this;
    }

    @Override
    public void onEnable() {
        network = new SpigotNetworkManager();
        network.init();
        getServer().getPluginManager().registerEvents(new WowSpigotListener(), this);
    }

    public static WowSpigot getInstance() {
        return instance;
    }

    public static NetworkManager getNetwork() {
        return network;
    }
}
