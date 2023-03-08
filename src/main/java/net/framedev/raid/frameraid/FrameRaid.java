package net.framedev.raid.frameraid;

import com.palmergames.bukkit.towny.object.Town;
import net.framedev.raid.frameraid.commands.RaidCommand;
import net.framedev.raid.frameraid.listeners.RaidListeners;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class FrameRaid extends JavaPlugin {

    private static FrameRaid instance;
    public static HashMap<Town, Town> raidMap;
    public static HashMap<Town, Inventory> activeRaid;
    public static HashMap<Town, ArrayList<Town>> townRaids;

    @Override
    public void onEnable() {
        instance = this;
        raidMap = new HashMap<>();
        townRaids = new HashMap<>();
        activeRaid = new HashMap<>();
        getServer().getPluginManager().registerEvents(new RaidListeners(), this);
        getServer().getPluginCommand("raid").setExecutor(new RaidCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FrameRaid getInstance() {return instance;}

}
