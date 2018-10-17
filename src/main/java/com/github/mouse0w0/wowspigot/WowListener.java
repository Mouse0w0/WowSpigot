package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.WowPlatform;
import com.github.mouse0w0.wow.network.packet.server.RegisterKeyBindingPacket;
import com.github.mouse0w0.wow.profile.User;
import com.github.mouse0w0.wowspigot.event.WowVerificationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WowListener implements Listener {

    @EventHandler
    public void onVerficated(WowVerificationEvent event) {
        User user = event.getUser();
        if(user.isSupport()) {
            WowPlatform.getNetwork().send(user, new RegisterKeyBindingPacket(WowSpigot.getKeyBindingRegistry()));
        }
    }
}
