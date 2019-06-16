package com.github.rainestormee.voltweapons.events;

import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExplosionArrow extends ItemHandler implements Listener {

    private List<UUID> primedPlayers;

    public ExplosionArrow() {
        primedPlayers = new ArrayList<>();
    }

    @EventHandler
    public void onExplosionArrowPrepare(PlayerInteractEvent event) {
        if (primedPlayers.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage("You already have a primed arrow!");
            return;
        }
        this.handleEvent(event.getPlayer(), CustomItem.TNT_ARROW, event.getItem(), event.getAction());
    }

    @Override
    public void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action) {
        primedPlayers.add(player.getUniqueId());
        player.sendMessage("YOUR NEXT ARROW IS GOING TO EXPLODE!1!1!!");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        try {
            Player player = ((Player) event.getEntity().getShooter());
            if (!primedPlayers.contains(player.getUniqueId())) return;
            this.createExplosion(player.getWorld(), event.getEntity().getLocation());
            primedPlayers.remove(player.getUniqueId());
        } catch (ClassCastException ignored) {}
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        primedPlayers.remove(event.getPlayer().getUniqueId());
    }

    public boolean createExplosion(World world, Location loc) {
        return world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), 2 * 4F, false, false);
    }
}