package com.github.mouse0w0.wowspigot.network.handler;

import com.github.mouse0w0.wow.keybinding.ServerKeyBinding;
import com.github.mouse0w0.wow.network.PacketHandler;
import com.github.mouse0w0.wow.network.packet.client.KeyBindingActionPacket;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wowspigot.WowSpigot;
import org.bukkit.entity.Player;

public class KeyBindingActionPacketHandler implements PacketHandler<KeyBindingActionPacket> {
    @Override
    public void handle(Object o, KeyBindingActionPacket keyBindingActionPacket) {
        ServerKeyBinding keyBinding = WowSpigot.getKeyBindingRegistry().getValue(keyBindingActionPacket.getKeyBindingId());
        User user = WowSpigot.getUser((Player) o);
        if(keyBindingActionPacket.isPressed())
            keyBinding.onPress(user);
        else
            keyBinding.onRelease(user);
    }
}
