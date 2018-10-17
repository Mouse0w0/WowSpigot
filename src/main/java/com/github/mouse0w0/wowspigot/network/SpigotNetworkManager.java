package com.github.mouse0w0.wowspigot.network;

import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.network.NetworkException;
import com.github.mouse0w0.wow.network.NetworkManagerBase;
import com.github.mouse0w0.wow.network.Packet;
import com.github.mouse0w0.wow.network.packet.client.ClientVerificationPacket;
import com.github.mouse0w0.wow.network.packet.client.KeyBindingActionPacket;
import com.github.mouse0w0.wow.network.packet.server.RegisterKeyBindingPacket;
import com.github.mouse0w0.wow.network.packet.server.ServerVerificationPacket;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wowspigot.WowSpigot;
import com.github.mouse0w0.wowspigot.network.handler.ClientVerificationPacketHandler;
import com.github.mouse0w0.wowspigot.network.handler.KeyBindingActionPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SpigotNetworkManager extends NetworkManagerBase {

    private final Messenger messenger = Bukkit.getMessenger();

    public SpigotNetworkManager() {
        register(ServerVerificationPacket.class, null);  // id 0
        register(ClientVerificationPacket.class, new ClientVerificationPacketHandler());  // id 1
        register(RegisterKeyBindingPacket.class, null); // id 2
        register(KeyBindingActionPacket.class, new KeyBindingActionPacketHandler()); // id 3
    }

    public void init() {
        messenger.registerIncomingPluginChannel(WowSpigot.getInstance(), WowPlatform.getNetworkChannelName(), new PluginMessageListener() {
            @Override
            public void onPluginMessageReceived(String channel, Player player, byte[] message) {
                handle(player, Unpooled.wrappedBuffer(message));
            }
        });
        messenger.registerOutgoingPluginChannel(WowSpigot.getInstance(), WowPlatform.getNetworkChannelName());
    }

    @Override
    public void send(Object target, Packet packet) {
        if (target instanceof Player) {
            super.send(target, packet);
        } else if (target instanceof User) {
            super.send(((User) target).getSource(), packet);
        } else {
            throw new NetworkException("Couldn't send packet to this target.");
        }
    }

    @Override
    protected void send(Object target, ByteBuf byteBuf) {
        Player player = (Player) target;
        player.sendPluginMessage(WowSpigot.getInstance(), WowPlatform.getName(), byteBuf.array());
    }
}
