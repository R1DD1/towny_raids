package net.framedev.raid.frameraid.events;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SendRaidRequestEvent extends Event implements Cancellable {

    private Player player;
    private Town attackTown;
    private Town defendTown;
    private boolean isCancelled;

    private static final HandlerList handlers = new HandlerList();

    public SendRaidRequestEvent(Player player, Town attackTown, Town defendTown) {
        this.player = player;
        this.attackTown = attackTown;
        this.defendTown = defendTown;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Town getAttackTown() {return this.attackTown;}
    public Town getDefendTown() {return this.defendTown;}


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        this.isCancelled = arg0;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}