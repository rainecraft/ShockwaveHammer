package com.github.rainestormee.shockwavehammer.events;

import com.github.rainestormee.shockwavehammer.util.Util;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class GroundClickEvent implements Listener {

    private Map<UUID, Long> hammerMap;
    private final int cooldown = 25 * 1000; // He wanted it 25 seconds

    public GroundClickEvent() {
        hammerMap = new HashMap<>();
    }

    @EventHandler
    public void onGroundClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !Util.checkValid(event.getItem())) return;
        if (hammerMap.containsKey(event.getPlayer().getUniqueId())) {
            long timeDifference = System.currentTimeMillis() - hammerMap.get(event.getPlayer().getUniqueId());
            if (!(timeDifference > cooldown)) {
                event.getPlayer().sendMessage("§cYour hammer is currently too hot to be used. Cooldown left: " + (Math.ceil((cooldown - timeDifference) / 1000)) + " seconds.");
                return;
            }
            }
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_HIT, 0.8F, 1);
        event.getPlayer().getWorld().getEntities().stream()
                .filter(o -> !o.getUniqueId().equals(event.getPlayer().getUniqueId()))
                .filter(other -> other.getLocation().distanceSquared(event.getClickedBlock().getLocation()) < 4).forEach(p -> {
            p.setVelocity(p.getLocation().toVector().subtract(event.getClickedBlock().getLocation().toVector()).normalize().multiply(1.5));
        });
        hammerMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        if (!Util.deteriorateItem(event.getItem())) event.getPlayer().getInventory().remove(event.getItem());
    }
}
