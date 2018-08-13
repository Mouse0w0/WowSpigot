package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.network.packet.common.VerificationPacket;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WowSpigotListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(WowSpigot.getInstance(), ()->WowSpigot.getNetwork().send(event.getPlayer(), new VerificationPacket(WowPlatform.INTERNAL_VERSION)), 20L);
    }
}
