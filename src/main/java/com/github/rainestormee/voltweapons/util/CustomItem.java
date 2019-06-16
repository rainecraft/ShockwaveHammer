package com.github.rainestormee.voltweapons.util;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CustomItem {

    SHOCKWAVE_AXE("hammer", Material.GOLD_AXE, "§6Shockwave Hammer", 25, new ArrayList<>(Arrays.asList("§7Right click the ground to unleash a", "§7shockwave that will blast away players near you.")), 25000, "§cYour hammer is currently too hot to be used.", Sound.ENDERDRAGON_HIT),
    INVIS_FEATHER("invis", Material.FEATHER, "Invisibility Feather", 25, new ArrayList<>(Arrays.asList("Some", "LORE")), 45000, "Some cooldown message.", Sound.CHICKEN_EGG_POP),
    TNT_ARROW("tnt", Material.TNT, "Explosive Arrow", 25, new ArrayList<>(Arrays.asList("", "")), 30000, "Some cooldown message", Sound.FUSE);

    private String name;
    private Material material;
    private String title;
    private int durability;
    private List<String> lore;
    private int cooldown;
    private String errormesage;
    private Sound sound;

    CustomItem(String name, Material material, String title, int durability, List<String> lore, int cooldown, String errormessage, Sound sound) {
        this.name = name;
        this.material = material;
        this.title = title;
        this.durability = durability;
        this.lore = lore;
        this.cooldown = cooldown;
        this.errormesage = errormessage;
        this.sound = sound;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public int getDurability() {
        return durability;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getErrormesage() {
        return errormesage;
    }

    public static Boolean deteriorateItem(ItemStack stack) {
        ItemMeta im = stack.getItemMeta();
        List<String> lore = im.getLore();
        int uses = Integer.valueOf((lore.get(lore.size() - 1)).replace("§cUses left: §a", "")) - 1;
        if (uses == 0) return false;
        updateLore(stack, lore, uses);
        return true;
    }

    public static ItemStack createItem(CustomItem item) {
        ItemStack itemStack = CustomItem.updateLore(new ItemStack(item.getMaterial(), 1), item.getLore(), item.getDurability());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(item.getTitle());
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        return itemStack;
    }

    public static ItemStack updateLore(ItemStack item, List<String> lore, int uses) {
        ItemMeta meta = item.getItemMeta();
        if (lore.get(lore.size() - 1).contains("§cUses left: §a")) {
            lore.remove(lore.size() - 1);
        }
        lore.add("§cUses left: §a" + uses);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static CustomItem fromTitle(String name) {
        return Arrays.stream(CustomItem.values()).filter(c -> c.getTitle().equals(name)).findFirst().orElse(null);
    }

    public static CustomItem fromName(String name) {
        return Arrays.stream(CustomItem.values()).filter(c -> c.getName().equals(name.toLowerCase())).findFirst().orElse(null);
}

    public static boolean checkValid(ItemStack itemStack) {
        return (CustomItem.fromTitle(itemStack.getItemMeta().getDisplayName()) != null);
    }

    public Sound getSound() {
        return sound;
    }

    public String getName() {
        return name;
    }
}
