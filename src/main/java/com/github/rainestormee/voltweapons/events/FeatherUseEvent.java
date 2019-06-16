package com.github.rainestormee.voltweapons.events;

import com.github.rainestormee.voltweapons.VoltWeapons;
import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FeatherUseEvent extends ItemHandler implements Listener {

    private VoltWeapons plugin;
    private List<Player> hidden;

    public FeatherUseEvent(VoltWeapons plugin) {
        this.plugin = plugin;
        this.hidden = new ArrayList<>();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        this.handleEvent(event.getPlayer(), CustomItem.INVIS_FEATHER, event.getItem(), event.getAction());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        hidden.forEach(p -> event.getPlayer().hidePlayer(p));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().getWorld().getPlayers().forEach(p -> p.showPlayer(event.getPlayer()));
    }

    @Override
    public void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action) {
        hidden.add(player);
        player.getServer().getOnlinePlayers().forEach(p -> {
            p.hidePlayer(player);
        });
        player.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            hidden.remove(player);
            player.getServer().getOnlinePlayers().forEach(p -> {
                if (!p.canSee(player)) p.showPlayer(player);
            });
        }, 5000L);
    }
}
/**
 * - An enchanted Feather
 * - When right clicked, it gives you complete vanish for 5 seconds.
 * - Uses: 25
 * - Cooldown: 45s
 */