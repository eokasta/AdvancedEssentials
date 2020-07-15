package com.github.eokasta.advancedessentials.utils;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PlayerUtil {

    public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            final Object chatTitle = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + Helper.format(title) + "\"}");
            final Constructor<?> titleConstructor = ReflectionUtil.getNMSClass("PacketPlayOutTitle").getConstructor(
                    ReflectionUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtil.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object packet = titleConstructor.newInstance(
                    ReflectionUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            final Object chatsTitle = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + Helper.format(subtitle) + "\"}");
            final Constructor<?> stitleConstructor = ReflectionUtil.getNMSClass("PacketPlayOutTitle").getConstructor(
                    ReflectionUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtil.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object spacket = stitleConstructor.newInstance(
                    ReflectionUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            ReflectionUtil.sendPacket(player, packet);
            ReflectionUtil.sendPacket(player, spacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendActionText(Player player, String message) {
        try {
            Class<?> packetPlayOutChat = ReflectionUtil.getNMSClass("PacketPlayOutChat");
            Constructor<?> packetConstructor = packetPlayOutChat.getConstructor(ReflectionUtil.getNMSClass("IChatBaseComponent"), byte.class);
            Class<?> iChatBaseComponent = ReflectionUtil.getNMSClass("IChatBaseComponent");
            Class<?> chatSerializer = iChatBaseComponent.getClasses()[0];
            Method csA = chatSerializer.getMethod("a", String.class);
            Object component = csA.invoke(chatSerializer, "{\"text\": \"" + Helper.format(message) + "\"}");
            Object packet = packetConstructor.newInstance(component, (byte) 2);
            ReflectionUtil.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
