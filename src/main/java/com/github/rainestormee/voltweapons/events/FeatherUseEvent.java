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

    @Override
    public void runLogic(Player player, CustomItem item, ItemStack itemStack, Action action) {
        this.hidePlayer(player);
        player.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> this.showPlayer(player), 5 * 20L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        hidden.forEach(p -> event.getPlayer().hidePlayer(p));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        showPlayer(event.getPlayer());
    }

    public void hidePlayer(Player player) {
        hidden.add(player);
        player.getWorld().getPlayers().forEach(p -> p.hidePlayer(player));
    }

    public void showPlayer(Player player) {
        hidden.remove(player);
        player.getWorld().getPlayers().stream().filter(p -> !p.canSee(player)).forEach(p -> p.showPlayer(player));
    }
}
/**
 * - An enchanted Feather
 * - When right clicked, it gives you complete vanish for 5 seconds.
 * - Uses: 25
 * - Cooldown: 45s
 */