package com.github.rainestormee.voltweapons.events;

import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ItemHandler {

    private Map<UUID, Long> cooldowns = new HashMap<>();

    public void handleEvent(Player player, CustomItem item, ItemStack itemStack, Action action) {
        if (!ItemHandler.isRightClick(action) || !CustomItem.checkValid(itemStack) || !item.equals(CustomItem.fromTitle(itemStack.getItemMeta().getDisplayName())))
            return;
        if (cooldowns.containsKey(player.getUniqueId())) {
            long timeDifference = System.currentTimeMillis() - cooldowns.get(player.getUniqueId());
            int cooldown = item.getCooldown();
            if (!(timeDifference > cooldown)) {
                player.sendMessage(item.getErrormesage() + " Cooldown left: " + (Math.ceil((cooldown - timeDifference) / 1000F)) + " seconds.");
                return;
            }
        }
        player.getWorld().playSound(player.getLocation(), item.getSound(), 0.8F, 1);
        runLogic(player, item, itemStack, action);
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        if (!CustomItem.deteriorateItem(itemStack)) {
            player.getInventory().remove(itemStack);
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 0.8F, 1);
        }
    }

    public abstract void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action);

    public static boolean isRightClick(Action action) {
        return action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
    }
}
