package com.github.rainestormee.shockwavehammer.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static ItemStack getGoldenHammer() {
        ItemStack is = new ItemStack(Material.GOLD_AXE, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§6Shockwave Hammer");
        setMeta(im, 25);
        im.addEnchant(Enchantment.DIG_SPEED, 1, false);
        is.setItemMeta(im);
        return is;
    }

    public static boolean deteriorateItem(ItemStack stack) {
        ItemMeta im = stack.getItemMeta();
        List<String> lore = im.getLore();
        int uses = Integer.valueOf(lore.get(2).replace("§cUses left: §a", "")) - 1;
        setMeta(im, uses);
        stack.setItemMeta(im);
        return (uses != 0);
    }

    public static void setMeta(ItemMeta meta, int amount) {
        meta.setLore(new ArrayList<String>() {
            {
                add("§7Right click the ground to unleash a");
                add("§7shockwave that will blast away players near you.");
                add("§cUses left: §a" + amount);
            }
        });
    }

    public static boolean checkValid(ItemStack itemStack) {
        return (itemStack.getType().equals(Material.GOLD_AXE) && itemStack.getItemMeta().getLore().get(0).equals("§7Right click the ground to unleash a"));
    }
}
