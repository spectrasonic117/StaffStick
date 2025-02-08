package com.spectrasonic.StaffStick.Utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public static ItemBuilder setMaterial(String materialName) {
        Material material = Material.matchMaterial(materialName.toUpperCase());
        if (material == null) throw new IllegalArgumentException("Invalid material: " + materialName);
        return new ItemBuilder(material);
    }

    private ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.displayName(MiniMessage.miniMessage().deserialize(name));
        return this;
    }

    public ItemBuilder setLore(String... loreLines) {
        meta.lore(java.util.Arrays.stream(loreLines)
                .map(MiniMessage.miniMessage()::deserialize)
                .toList());
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        meta.setCustomModelData(customModelData);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}


/* Modo de uso del ItemBuilder
    
     var BUILDITEM = ItemBuilder.setMaterial("MATERIAL_ITEM")
                .setName("ITEMNAME")
                .setLore("LORE 1",
                        "LORE 2",
                        "LORE 3")
                .setCustomModelData(INT)
                .build();
        target.getInventory().addItem(StaffStickItem);
        
*/
