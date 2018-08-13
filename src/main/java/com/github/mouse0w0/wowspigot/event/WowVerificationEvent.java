package com.github.mouse0w0.wowspigot.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class WowVerificationEvent extends PlayerEvent {

    private final int internalVersion;

    public WowVerificationEvent(Player who, int internalVersion) {
        super(who);
        this.internalVersion = internalVersion;
    }

    public int getInternalVersion() {
        return internalVersion;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
