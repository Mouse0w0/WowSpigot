package com.github.mouse0w0.wowspigot;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class WowConfig {

    private static int verificationRetryCount;
    private static boolean mustUseWow;
    private static UUID serverUUID;
    private static boolean debug;

    public static int getVerificationRetryCount() {
        return verificationRetryCount;
    }

    public static boolean isMustUseWow() {
        return mustUseWow;
    }

    public static UUID getServerUUID() {
        return serverUUID;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void reload() {
        WowSpigot.getInstance().reloadConfig();
        FileConfiguration config = WowSpigot.getInstance().getConfig();
        verificationRetryCount = config.getInt("VerificationRetryCount",3);
        mustUseWow = config.getBoolean("MustUseWow", false);
        debug = config.getBoolean("Debug", false);

        if(!config.contains("ServerUUID")) {
            serverUUID = UUID.randomUUID();
            config.set("ServerUUID", serverUUID.toString());
            WowSpigot.getInstance().saveConfig();
        } else {
            serverUUID = UUID.fromString(config.getString("ServerUUID"));
        }
    }
}
