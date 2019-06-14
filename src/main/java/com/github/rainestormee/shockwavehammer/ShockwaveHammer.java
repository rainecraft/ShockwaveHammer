package com.github.rainestormee.shockwavehammer;

import com.github.rainestormee.shockwavehammer.events.GroundClickEvent;
import com.github.rainestormee.shockwavehammer.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShockwaveHammer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new GroundClickEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ((Player) sender).getInventory().addItem(Util.getGoldenHammer());
        return true;
    }
}
