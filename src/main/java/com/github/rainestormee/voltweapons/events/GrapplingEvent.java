package com.github.rainestormee.voltweapons.events;

import com.github.rainestormee.voltweapons.util.CustomItem;
import com.snowgears.grapplinghook.PlayerGrappleEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class GrapplingEvent extends ItemHandler implements Listener {

    @EventHandler
    public void onGrapple(PlayerGrappleEvent event) {
        // this.handleEvent(event.getPlayer(), CustomItem.GRAPPLE_HOOK, event.getHookItem(), Action.RIGHT_CLICK_AIR);
    }

    @Override
    public void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action) {
        // TODO: Grapple logic
    }
}
