package com.github.mouse0w0.wowspigot.network.handler;

import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.network.PacketHandler;
import com.github.mouse0w0.wow.network.packet.client.ClientVerificationPacket;
import com.github.mouse0w0.wow.network.packet.server.ServerVerificationPacket;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wowspigot.WowConfig;
import com.github.mouse0w0.wowspigot.WowSpigot;
import com.github.mouse0w0.wowspigot.event.WowVerificationEvent;
import com.github.mouse0w0.wowspigot.profile.SpigotUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.WeakHashMap;

public class ClientVerificationPacketHandler implements PacketHandler<ClientVerificationPacket>, Listener {

    private static final Map<Player, User> playerProfile = new WeakHashMap<>();

    public static User getProfile(Player player) {
        return playerProfile.get(player);
    }

    public static boolean isInitialized(Player player) {
        return playerProfile.containsKey(player);
    }

    public ClientVerificationPacketHandler() {
        Bukkit.getPluginManager().registerEvents(this, WowSpigot.getInstance());
    }

    private User createProfile(Player player, int version) {
        SpigotUser user = new SpigotUser(player, version);
        playerProfile.put(player, user);
        return user;
    }

    private User createUnsupportProfile(Player player) {
        return createProfile(player, 0);
    }

    @Override
    public void hander(Object sender, ClientVerificationPacket packet) {
        Player player = (Player) sender;
        User user = createProfile(player, packet.getInternalVersion());
        Bukkit.getPluginManager().callEvent(new WowVerificationEvent(player, user));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable(){
            private int count = 0;

            @Override
            public void run() {
                if(isInitialized(event.getPlayer())){
                    cancel();
                    return;
                }
                if (count >= WowConfig.getVerificationRetryCount()) {
                    Bukkit.getPluginManager().callEvent(new WowVerificationEvent(event.getPlayer(), createUnsupportProfile(event.getPlayer())));
                    cancel();
                    return;
                }
                WowSpigot.getNetwork().send(event.getPlayer(), new ServerVerificationPacket(WowPlatform.INTERNAL_VERSION));
                count++;
            }
        }.runTaskTimerAsynchronously(WowSpigot.getInstance(), 20L, 20L);
    }
}
