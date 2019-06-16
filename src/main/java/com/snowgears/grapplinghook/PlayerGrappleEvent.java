//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snowgears.grapplinghook;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerGrappleEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Entity entity;
    private Location pullLocation;
    private ItemStack hookItem;
    private boolean cancelled = false;

    public PlayerGrappleEvent(Player p, Entity e, Location l) {
        this.player = p;
        this.entity = e;
        this.pullLocation = l;
        this.hookItem = p.getItemInHand();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Entity getPulledEntity() {
        return this.entity;
    }

    public Location getPullLocation() {
        return this.pullLocation;
    }

    public ItemStack getHookItem() {
        return this.hookItem;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean set) {
        this.cancelled = set;
    }
}
