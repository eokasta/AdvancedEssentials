package com.github.eokasta.advancedessentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.MessageFormat;

public class Helper {

    public static final String COMMAND_PERMISSION = "advancedessentials.command.";

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String format(String s, String... args) {
        return format(String.format(s, args));
    }

    public static String serializeLocation(Location location) {

        return location.getWorld().getName() +
                ";" + location.getX() +
                ";" + location.getY() +
                ";" + location.getZ() +
                ";" + location.getPitch() +
                ";" + location.getYaw();
    }

    public static Location deserializeLocation(String string) {
        String[] split = string.split(";");

        final World world = Bukkit.getWorld(split[0]);
        final double x = Double.parseDouble(split[1]);
        final double y = Double.parseDouble(split[2]);
        final double z = Double.parseDouble(split[3]);

        final float pitch = Float.parseFloat(split[4]);
        final float yaw = Float.parseFloat(split[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Double getDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            return null;
        }
    }

}
