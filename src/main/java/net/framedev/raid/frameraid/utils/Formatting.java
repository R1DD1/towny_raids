package net.framedev.raid.frameraid.utils;

import org.bukkit.ChatColor;

public class Formatting {
    public static String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
