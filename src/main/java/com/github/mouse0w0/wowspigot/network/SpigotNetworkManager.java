package com.github.mouse0w0.wowspigot.network;

import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.network.NetworkException;
import com.github.mouse0w0.wow.network.NetworkManagerBase;
import com.github.mouse0w0.wow.network.Packet;
import com.github.mouse0w0.wow.network.packet.common.VerificationPacket;
import com.github.mouse0w0.wowspigot.WowSpigot;
import com.github.mouse0w0.wowspigot.network.handler.VerificationPacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SpigotNetworkManager extends NetworkManagerBase {

    private final Messenger messenger = Bukkit.getMessenger();

    public SpigotNetworkManager() {
        register(VerificationPacket.class, new VerificationPacketHandler());
    }

    public void init() {
        messenger.registerIncomingPluginChannel(WowSpigot.getInstance(), WowPlatform.NAME, new PluginMessageListener() {
            @Override
            public void onPluginMessageReceived(String channel, Player player, byte[] message) {
                handle(player, Unpooled.wrappedBuffer(message));
            }
        });
        messenger.registerOutgoingPluginChannel(WowSpigot.getInstance(), WowPlatform.NAME);
    }

    @Override
    public void send(Object target, Packet packet) {
        if(!(target instanceof Player))
            throw new NetworkException("Couldn't send packet to this target.");

        Player player = (Player) target;
        ByteBuf buf = createBuffer(packet.getClass());
        packet.write(buf);
        player.sendPluginMessage(WowSpigot.getInstance(), WowPlatform.NAME, buf.array());
    }
}
