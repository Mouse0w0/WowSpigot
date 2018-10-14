package com.github.mouse0w0.wowspigot.keybinding;

import com.github.mouse0w0.wow.keybinding.ServerKeyBinding;
import com.github.mouse0w0.wow.registry.RegistryBase;

public class ServerKeyBindingManager extends RegistryBase<ServerKeyBinding> {

    @Override
    protected int nextId(ServerKeyBinding serverKeyBinding) {
        return 0;
    }
}
