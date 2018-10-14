package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.PlatformProvider;
import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.keybinding.KeyBinding;
import com.github.mouse0w0.wow.keybinding.ServerKeyBinding;
import com.github.mouse0w0.wow.network.NetworkManager;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wow.registry.Registry;
import com.github.mouse0w0.wow.registry.RegistryManager;
import com.github.mouse0w0.wow.registry.SimpleRegistryManager;
import com.github.mouse0w0.wowspigot.keybinding.ServerKeyBindingManager;
import com.github.mouse0w0.wowspigot.network.SpigotNetworkManager;
import com.github.mouse0w0.wowspigot.network.handler.ClientVerificationPacketHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WowSpigot extends JavaPlugin {

    private static WowSpigot instance;
    private static SpigotNetworkManager network;
    private static RegistryManager registryManager;

    public WowSpigot() {
        instance = this;
    }

    @Override
    public void onLoad() {
        registryManager = new SimpleRegistryManager();
        registryManager.addRegistry(ServerKeyBinding.class, new ServerKeyBindingManager());
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        WowConfig.reload();

        network = new SpigotNetworkManager();
        network.init();

        getCommand("wow").setExecutor(new WowCommand());

        WowPlatform.setPlatformProvider(new SpigotPlatformProvider());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static WowSpigot getInstance() {
        return instance;
    }

    public static NetworkManager getNetwork() {
        return network;
    }

    public static RegistryManager getRegistryManager() {
        return registryManager;
    }

    public static User getProfile(Player player) {
        return ClientVerificationPacketHandler.getProfile(player);
    }

    public static boolean isInitialized(Player player) {
        return ClientVerificationPacketHandler.isInitialized(player);
    }

    public static class SpigotPlatformProvider implements PlatformProvider {

        @Override
        public NetworkManager getNetwork() {
            return WowSpigot.getNetwork();
        }

        @Override
        public RegistryManager getRegistryManager() {
            return WowSpigot.getRegistryManager();
        }

        @Override
        public boolean isServer() {
            return true;
        }
    }
}
