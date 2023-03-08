package net.framedev.raid.frameraid.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HeadBuilder {
    private final ItemStack item;

    public HeadBuilder(Material material, int amount) {
        item = new ItemStack(material, amount, (short)3);
    }

    public HeadBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public HeadBuilder addLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            itemLore = new ArrayList<>();
        }
        for (String line : lore) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return this;
    }

    public HeadBuilder addLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            itemLore = new ArrayList<>();
        }
        for (String line : lore) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return this;
    }

    public HeadBuilder setHead(Player player) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
