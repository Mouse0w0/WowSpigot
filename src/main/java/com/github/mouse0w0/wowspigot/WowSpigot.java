package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.network.NetworkManager;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wowspigot.network.SpigotNetworkManager;
import com.github.mouse0w0.wowspigot.network.handler.ClientVerificationPacketHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WowSpigot extends JavaPlugin {

    private static WowSpigot instance;
    private static SpigotNetworkManager network;

    public WowSpigot() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        WowConfig.reload();

        network = new SpigotNetworkManager();
        network.init();

        getCommand("wow").setExecutor(new WowCommand());
    }

    public static WowSpigot getInstance() {
        return instance;
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static User getProfile(Player player) {
        return ClientVerificationPacketHandler.getProfile(player);
    }

    public static boolean isInitialized(Player player) {
        return ClientVerificationPacketHandler.isInitialized(player);
    }
}
