package com.github.rainestormee.voltweapons.events;

import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HammerUseEvent extends ItemHandler implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        this.handleEvent(event.getPlayer(), CustomItem.SHOCKWAVE_AXE, event.getItem(), event.getAction());
    }

    @Override
    public void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action) {
        player.getWorld().getPlayers().stream()
                .filter(o -> !o.getUniqueId().equals(player.getUniqueId()))
                .filter(other -> other.getLocation().distanceSquared(player.getLocation()) < 7).forEach(p -> {
            p.setVelocity(p.getLocation().toVector().subtract(player.getLocation().toVector().subtract(new Vector(0, 1, 0))).multiply(1.2));
        });
    }
}
