package com.github.mouse0w0.wowspigot.event;

import com.github.mouse0w0.wow.profile.User;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class WowVerificationEvent extends PlayerEvent {

    private final User user;

    public WowVerificationEvent(Player who, User user) {
        super(who);
        this.user = user;
    }

    public User getUser() {
        return user;
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
