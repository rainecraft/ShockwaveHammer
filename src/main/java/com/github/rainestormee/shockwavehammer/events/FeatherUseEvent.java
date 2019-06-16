package com.github.rainestormee.shockwavehammer.events;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FeatherUseEvent implements Listener {

    public void featherUse(PlayerInteractEvent event) {
        event.getAction().equals(Action.RIGHT_CLICK_AIR);
    }
}
/**
 - An enchanted Feather
 - When right clicked, it gives you complete vanish for 5 seconds.
 - Uses: 25
 - Cooldown: 45s
 */