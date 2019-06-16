//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snowgears.grapplinghook;

import com.github.rainestormee.voltweapons.VoltWeapons;
import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GrapplingListener implements Listener {
    public VoltWeapons plugin;

    public GrapplingListener(VoltWeapons instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof FishHook) {
            FishHook hook = (FishHook) event.getDamager();
            if (!(hook.getShooter() instanceof Player)) {
                return;
            }

            Player player = (Player) hook.getShooter();
            if (!CustomItem.checkValid(player.getItemInHand())) {
                return;
            }

            if (event.getEntity() instanceof Player) {
                Player hooked = (Player) event.getEntity();
                hooked.sendMessage(ChatColor.YELLOW + "You have been hooked by " + ChatColor.RESET + player.getName() + ChatColor.YELLOW + "!");
                player.sendMessage(ChatColor.GOLD + "You have hooked " + ChatColor.RESET + hooked.getName() + ChatColor.YELLOW + "!");
            } else {
                String entityName = event.getEntityType().toString().replace("_", " ").toLowerCase();
                player.sendMessage(ChatColor.GOLD + "You have hooked a " + entityName + "!");
            }
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onGrapple(PlayerGrappleEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            event.getHookItem().setDurability((short) -10);
            Entity e = event.getPulledEntity();
            Location loc = event.getPullLocation();
            if (player.equals(e)) {
                if (player.getLocation().distance(loc) < 6.0D) {
                    this.pullPlayerSlightly(player, loc);
                } else {
                    this.pullEntityToLocation(player, loc);
                }
            } else {
                this.pullEntityToLocation(e, loc);
                if (e instanceof Item) {
                    ItemStack is = ((Item) e).getItemStack();
                    String itemName = is.getType().toString().replace("_", " ").toLowerCase();
                    player.sendMessage(ChatColor.GOLD + "You have hooked a stack of " + is.getAmount() + " " + itemName + "!");
                }
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void fishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (!CustomItem.checkValidItem(CustomItem.GRAPPLE_HOOK, player.getItemInHand())) return;
        PlayerGrappleEvent gevent;
        switch (event.getState()) {
            case CAUGHT_FISH: {
                event.setCancelled(true);
                break;
            }
            case CAUGHT_ENTITY: {
                gevent = new PlayerGrappleEvent(player, event.getCaught(), player.getLocation());
                plugin.getServer().getPluginManager().callEvent(gevent);
                break;
            }
            case IN_GROUND: {
            }
            case FAILED_ATTEMPT: {
                gevent = new PlayerGrappleEvent(player, player, player.getLocation());
                player.getServer().getPluginManager().callEvent(gevent);
                break;
            }
        }
    }

    private void pullPlayerSlightly(Player p, Location loc) {
        if (loc.getY() > p.getLocation().getY()) {
            p.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
        } else {
            Location playerLoc = p.getLocation();
            Vector vector = loc.toVector().subtract(playerLoc.toVector());
            p.setVelocity(vector);
        }
    }

    private void pullEntityToLocation(Entity e, Location loc) {
        Location entityLoc = e.getLocation();
        entityLoc.setY(entityLoc.getY() + 0.5D);
        e.teleport(entityLoc);
        double g = -0.08D;
        double d = loc.distance(entityLoc);
        double v_x = (1.0D + 0.07D * d) * (loc.getX() - entityLoc.getX()) / d;
        double v_y = (1.0D + 0.03D * d) * (loc.getY() - entityLoc.getY()) / d - 0.5D * g * d;
        double v_z = (1.0D + 0.07D * d) * (loc.getZ() - entityLoc.getZ()) / d;
        Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }
}
