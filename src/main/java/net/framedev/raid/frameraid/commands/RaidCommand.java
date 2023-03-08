package net.framedev.raid.frameraid.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import net.framedev.raid.frameraid.FrameRaid;
import net.framedev.raid.frameraid.events.AcceptRaidEvent;
import net.framedev.raid.frameraid.events.RejectRaidRequestEvent;
import net.framedev.raid.frameraid.events.SendRaidRequestEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class RaidCommand implements CommandExecutor {
    private FrameRaid plugin = FrameRaid.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            Town playerTown = TownyAPI.getInstance().getTown(player);
            if (playerTown != null) {
                if (args.length == 1) {

                    if (Objects.equals(args[0], "accept")) {
                        AcceptRaidEvent event = new AcceptRaidEvent(player, FrameRaid.raidMap.get(playerTown), playerTown);
                        Bukkit.getPluginManager().callEvent(event);
                    } else if (Objects.equals(args[0], "cancel")) {
                        RejectRaidRequestEvent event = new RejectRaidRequestEvent(player, FrameRaid.raidMap.get(playerTown), playerTown);
                        Bukkit.getPluginManager().callEvent(event);
                    } else if (TownyAPI.getInstance().getTown(args[0]) != null) {
                        Town defendTown = TownyAPI.getInstance().getTown(args[0]);
                        FrameRaid.raidMap.put(defendTown, playerTown);
                        SendRaidRequestEvent event = new SendRaidRequestEvent(player, playerTown, defendTown);
                        Bukkit.getPluginManager().callEvent(event);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            FrameRaid.raidMap.remove(defendTown);
                        }, plugin.getConfig().getInt("settings.timer_for_auto_cancel") * 20L);
                    }
                }
                if (args.length == 0) {
                    Inventory inventory = FrameRaid.activeRaid.getOrDefault(playerTown, Bukkit.createInventory(null, 9));
                    player.openInventory(inventory);
                }
            }
        }
        return false;
    }
}
