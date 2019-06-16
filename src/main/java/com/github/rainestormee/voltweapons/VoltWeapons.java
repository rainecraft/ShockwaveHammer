package com.github.rainestormee.voltweapons;

import com.github.rainestormee.voltweapons.events.HammerUseEvent;
import com.github.rainestormee.voltweapons.util.CustomItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class VoltWeapons extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new HammerUseEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/voltweapon [hammer/invis] {player}");
            return false;
        }
        CustomItem item = CustomItem.fromName(args[0]);
        if (item == null) {
            sender.sendMessage("VoltWeapon not found! Valid options: [hammer/invis]");
            return false;
        }
        Player receiver = null;
        if (args.length == 3) {
            receiver = sender.getServer().getOnlinePlayers().stream().filter(r -> ((Player) r).getName().equals(args[0])).findFirst().orElse(null);
        }
        if (receiver == null) {
            if (!(sender instanceof Player)) return false;
            receiver = (Player) sender;
        }
        receiver.getInventory().addItem(CustomItem.createItem(item));
        return true;
    }
}
