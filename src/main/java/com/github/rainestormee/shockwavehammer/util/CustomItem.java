package com.github.rainestormee.shockwavehammer.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public enum CustomItem {

    SHOCKWAVE_AXE(Material.GOLD_AXE, "§6Shockwave Hammer", 25, Arrays.asList("", ""), 25, "§cYour hammer is currently too hot to be used. Cooldown left: "),
    INVIS_FEATHER(Material.FEATHER, "Invisibility Feather", 25, Arrays.asList("Some", "LORE"), 45, "Some cooldown message. Cooldown left: ");

    private Material material;
    private String title;
    private int durability;
    private List<String> lore;
    private int cooldown;
    private String errormesage;

    CustomItem(Material material, String title, int durability, List<String> lore, int cooldown, String errormessage) {
        this.material = material;
        this.title = title;
        this.durability = durability;
        this.lore = lore;
        this.cooldown = cooldown;
        this.errormesage = errormessage;
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
        int uses = Integer.valueOf(lore.get(lore.size()).replace("§cUses left: §a", "")) - 1;
        CustomItem.setLore(stack, uses);
        stack.setItemMeta(im);
        return (uses != 0);
    }

    public static ItemStack createItem(CustomItem item) {
        ItemStack itemStack = new ItemStack(item.getMaterial(), 1);
        CustomItem.setLore(itemStack);
        return itemStack;
    }

    public static void setLore(ItemStack stack, int amount) {
        CustomItem item = CustomItem.fromName(stack.getItemMeta().getDisplayName());
        if (item == null) return;
        CustomItem.setLore(item, stack, amount);
    }

    public static void setLore(ItemStack stack) {
        CustomItem.setLore(stack, CustomItem.fromName(stack.getItemMeta().getDisplayName()).getDurability());
    }

    public static void setLore(CustomItem item, ItemStack stack, int amount) {
        List<String> lore = item.getLore();
        lore.add("§cUses left: §a" + amount);
        stack.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        stack.getItemMeta().setLore(lore);
    }

    public static CustomItem fromName(String name) {
        return Arrays.stream(CustomItem.values()).filter(c -> c.getTitle().equals(name)).findFirst().orElse(null);
    }
}
