package com.github.eokasta.advancedessentials.utils;

import org.bukkit.Bukkit;

public enum ServerVersion {
    MC_1_8("1.8"),
    MC_1_9("1.9"),
    MC_1_10("1.10"),
    MC_1_11("1.11"),
    MC_1_12("1.12"),
    MC_1_13("1.13"),
    MC_1_14("1.14"),
    UNSUPPORTED("UNSUPPORTED");
    public static final ServerVersion CURRENT;

    static {
        CURRENT = getServerVersion();
    }

    private String name;

    ServerVersion(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isOrLess(ServerVersion other) {
        return (ordinal() <= other.ordinal());
    }

    public boolean isLessThan(ServerVersion other) {
        return (ordinal() < other.ordinal());
    }

    public boolean isAtLeast(ServerVersion other) {
        return (ordinal() >= other.ordinal());
    }

    public boolean isGreaterThan(ServerVersion other) {
        return (ordinal() > other.ordinal());
    }

    public static ServerVersion getServerVersion() {
        String version = Bukkit.getBukkitVersion().split("-")[0];

        if (version.contains("1.8"))
            return MC_1_8;
        if (version.contains("1.9"))
            return MC_1_9;
        if (version.contains("1.10"))
            return MC_1_10;
        if (version.contains("1.11"))
            return MC_1_11;
        if (version.contains("1.12"))
            return MC_1_12;
        if (version.contains("1.13"))
            return MC_1_13;
        if (version.contains("1.14")) {
            return MC_1_14;
        }
        return UNSUPPORTED;
    }
}