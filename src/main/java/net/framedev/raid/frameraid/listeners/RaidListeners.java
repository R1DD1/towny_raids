package net.framedev.raid.frameraid.listeners;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import net.framedev.raid.frameraid.FrameRaid;
import net.framedev.raid.frameraid.events.AcceptRaidEvent;
import net.framedev.raid.frameraid.events.RejectRaidRequestEvent;
import net.framedev.raid.frameraid.events.SendRaidRequestEvent;
import net.framedev.raid.frameraid.utils.Formatting;
import net.framedev.raid.frameraid.utils.HeadBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class RaidListeners implements Listener {

    private final FrameRaid plugin = FrameRaid.getInstance();

    @EventHandler
    public void onSend(SendRaidRequestEvent e) {
        plugin.getServer().broadcastMessage(Formatting.translate(plugin.getConfig().getString("messages.send_raid").replace("%defend%", e.getDefendTown().getName()).replace("%attack%", e.getAttackTown().getName())));
        Bukkit.getScheduler().runTaskLater(plugin, ()-> {
            if (FrameRaid.raidMap.get(e.getDefendTown()) != null) {
                RejectRaidRequestEvent event = new RejectRaidRequestEvent(e.getPlayer(), e.getAttackTown(), e.getDefendTown());
                Bukkit.getPluginManager().callEvent(event);
            }
        }, plugin.getConfig().getInt("settings.timer_for_auto_cancel") * 20L);
    }

    @EventHandler
    public void onAccept(AcceptRaidEvent e) throws NotRegisteredException {
        if (e.getDefendTown().hasNation()&& e.getAttackTown().hasNation()) {
            if (e.getDefendTown().getNation() == e.getAttackTown().getNation()) {
                plugin.getServer().broadcastMessage(Formatting.translate(plugin.getConfig().getString("messages.same_nation").replace("%defend%", e.getDefendTown().getName()).replace("%attack%", e.getAttackTown().getName())));
                return;
            }
        }
        FrameRaid.raidMap.remove(e.getDefendTown());
        plugin.getServer().broadcastMessage(Formatting.translate(plugin.getConfig().getString("messages.accept_raid").replace("%defend%", e.getDefendTown().getName()).replace("%attack%", e.getAttackTown().getName())));
        //def inv
        Inventory defInv = FrameRaid.activeRaid.getOrDefault(e.getDefendTown(), Bukkit.createInventory(null, 9));
        defInv.addItem(new HeadBuilder(Material.SKULL_ITEM, 1).addLore(plugin.getConfig().getStringList("raid_item.lore")).setHead(e.getDefendTown().getMayor().getPlayer()).setName(plugin.getConfig().getString("raid_item.display_name")).build());
        FrameRaid.activeRaid.put(e.getDefendTown(), defInv);

        //attack inv
        Inventory attackInv = FrameRaid.activeRaid.getOrDefault(e.getAttackTown(), Bukkit.createInventory(null, 9));
        attackInv.addItem(new HeadBuilder(Material.SKULL_ITEM, 1).addLore(plugin.getConfig().getStringList("raid_item.lore")).setHead(e.getAttackTown().getMayor().getPlayer()).setName(plugin.getConfig().getString("raid_item.display_name")).build());
        FrameRaid.activeRaid.put(e.getAttackTown(), attackInv);

        Bukkit.getScheduler().runTaskLater(plugin, ()-> {
            Bukkit.broadcastMessage(Formatting.translate(plugin.getConfig().getString("messages.end_prepare")));
            e.getAttackTown().setPVP(true);
            e.getDefendTown().setPVP(true);
        }, plugin.getConfig().getInt("settings.prepare_time") * 20L);
    }

    @EventHandler
    public void onReject(RejectRaidRequestEvent e) {
        FrameRaid.raidMap.remove(e.getDefendTown());
        plugin.getServer().broadcastMessage(Formatting.translate(plugin.getConfig().getString("messages.cancel_raid").replace("%defend%", e.getDefendTown().getName()).replace("%attack%", e.getAttackTown().getName())));
    }
}
