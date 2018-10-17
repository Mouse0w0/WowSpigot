package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.PlatformProvider;
import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.keybinding.Key;
import com.github.mouse0w0.wow.keybinding.ServerKeyBinding;
import com.github.mouse0w0.wow.network.NetworkManager;
import com.github.mouse0w0.wow.profile.Server;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wow.registry.Registry;
import com.github.mouse0w0.wow.registry.RegistryManager;
import com.github.mouse0w0.wow.registry.SimpleRegistry;
import com.github.mouse0w0.wow.registry.SimpleRegistryManager;
import com.github.mouse0w0.wowspigot.network.SpigotNetworkManager;
import com.github.mouse0w0.wowspigot.network.handler.ClientVerificationPacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class WowSpigot extends JavaPlugin {

    private static WowSpigot instance;
    private static SpigotNetworkManager network;
    private static RegistryManager registryManager;
    private static Registry<ServerKeyBinding> keyBindingRegistry;
    private static Server server;

    public WowSpigot() {
        instance = this;
    }

    @Override
    public void onLoad() {
        registryManager = new SimpleRegistryManager();
        keyBindingRegistry = new SimpleRegistry<>();
        registryManager.addRegistry(ServerKeyBinding.class, keyBindingRegistry);
        ServerKeyBinding keyBinding = ServerKeyBinding.builder()
                .defaultKey(Key.KEY_H)
                .displayName("测试")
                .onPress(user -> ((Player) user.getSource()).sendMessage("Hello Wow!"))
                .build()
                .setRegistryName("wow:test");
        System.out.println(keyBindingRegistry.getRegistryEntryType().getName());
        registryManager.register(keyBinding);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        WowConfig.reload();

        server = new Server(true, WowPlatform.getInternalVersion(), WowConfig.getServerUUID(), null);

        network = new SpigotNetworkManager();
        network.init();

        getServer().getPluginManager().registerEvents(new WowListener(), this);

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

    public static User getUser(Player player) {
        return ClientVerificationPacketHandler.getUser(player);
    }

    public static boolean isInitialized(Player player) {
        return ClientVerificationPacketHandler.isInitialized(player);
    }

    public static Registry<ServerKeyBinding> getKeyBindingRegistry() {
        return keyBindingRegistry;
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

        @Override
        public Server getServer() {
            return WowSpigot.server;
        }

        @Override
        public User getUser(UUID uuid) throws UnsupportedOperationException {
            return WowSpigot.getUser(Bukkit.getPlayer(uuid));
        }

        @Override
        public User getUser(String name) throws UnsupportedOperationException {
            return WowSpigot.getUser(Bukkit.getPlayerExact(name));
        }

        @Override
        public User getUser() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("This is server platform.");
        }
    }
}
