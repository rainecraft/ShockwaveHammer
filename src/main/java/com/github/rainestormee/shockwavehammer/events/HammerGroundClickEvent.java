package com.github.rainestormee.shockwavehammer.events;

import com.github.rainestormee.shockwavehammer.util.Util;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class HammerGroundClickEvent implements Listener {

    private Map<UUID, Long> hammerMap;
    private final int cooldown = 25 * 1000; // He wanted it 25 seconds

    public HammerGroundClickEvent() {
        hammerMap = new HashMap<>();
    }

    @EventHandler
    public void onGroundClick(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) || !Util.checkValid(event.getItem())) return;
        if (hammerMap.containsKey(event.getPlayer().getUniqueId())) {
            long timeDifference = System.currentTimeMillis() - hammerMap.get(event.getPlayer().getUniqueId());
            if (!(timeDifference > cooldown)) {
                event.getPlayer().sendMessage("§cYour hammer is currently too hot to be used. Cooldown left: " + (Math.ceil((cooldown - timeDifference) / 1000F)) + " seconds.");
                return;
            }
            }
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_HIT, 0.8F, 1);
        event.getPlayer().getWorld().getEntities().stream()
                .filter(o -> !o.getUniqueId().equals(event.getPlayer().getUniqueId()))
                .filter(other -> other.getLocation().distanceSquared(event.getPlayer().getLocation()) < 7).forEach(p -> {
            p.setVelocity(p.getLocation().toVector().subtract(event.getPlayer().getLocation().toVector().subtract(new Vector(0, 10, 0))).normalize().multiply(1.5));
        });
        hammerMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        if (!Util.deteriorateItem(event.getItem())) event.getPlayer().getInventory().remove(event.getItem());
    }
}
