package com.github.mouse0w0.wowspigot;

import org.bukkit.configuration.file.FileConfiguration;

public class WowConfig {

    private static int verificationRetryCount;
    private static boolean mustUseWow;

    public static int getVerificationRetryCount() {
        return verificationRetryCount;
    }

    public static boolean isMustUseWow() {
        return mustUseWow;
    }

    public static void reload() {
        WowSpigot.getInstance().reloadConfig();
        FileConfiguration config = WowSpigot.getInstance().getConfig();
        verificationRetryCount = config.getInt("VerificationRetryCount",3);
        mustUseWow = config.getBoolean("MustUseWow", false);
    }
}
