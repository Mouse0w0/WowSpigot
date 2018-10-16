package com.github.mouse0w0.wowspigot.profile;

import com.github.mouse0w0.wow.profile.User;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotUser implements User {

    private final Player player;
    private final int version;

    public SpigotUser(Player player, int version) {
        this.player = player;
        this.version = version;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public Object getSource() {
        return player;
    }

    @Override
    public boolean isSupport() {
        return version > 0;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public Player getPlayer() {
        return player;
    }
}
