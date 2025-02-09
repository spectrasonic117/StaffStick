package com.spectrasonic.StaffStick.Utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;

import java.util.*;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private boolean unbreakable = false;
    private final List<ItemFlag> itemFlags = new ArrayList<>();

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
        meta.lore(Arrays.stream(loreLines)
                .map(MiniMessage.miniMessage()::deserialize)
                .toList());
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        meta.setCustomModelData(customModelData);
        return this;
    }

    /**
     * Añade un encantamiento al ítem utilizando el nombre del encantamiento.
     *
     * @param enchantName El nombre del encantamiento (ej. "DAMAGE_ALL").
     * @param level       El nivel del encantamiento.
     * @return La instancia actual de ItemBuilder.
     */
    public ItemBuilder addEnchantment(String enchantName, int level) {
        NamespacedKey key = NamespacedKey.minecraft(enchantName.toLowerCase());
        Enchantment enchantment = Enchantment.getByKey(key);
        
        if (enchantment == null) {
            throw new IllegalArgumentException("Encantamiento inválido: " + enchantName);
        }
        
        // Verificamos si el nivel es válido
        if(level < 1 || level > enchantment.getMaxLevel()) {
            throw new IllegalArgumentException("Nivel inválido para el encantamiento: " + level);
    }
    
    enchantments.put(enchantment, level);
    return this;
}

    /**
     * Añade una bandera de ítem utilizando el nombre de la bandera.
     *
     * @param flagName El nombre de la bandera (ej. "HIDE_ENCHANTS").
     * @return La instancia actual de ItemBuilder.
     */
    public ItemBuilder addItemFlag(String flagName) {
        try {
            ItemFlag flag = ItemFlag.valueOf(flagName.toUpperCase());
            itemFlags.add(flag);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Banda de ítem inválida: " + flagName);
        }
        return this;
    }

    /**
     * Define si el ítem es irrompible.
     *
     * @param unbreakable true si el ítem debe ser irrompible, false en caso contrario.
     * @return La instancia actual de ItemBuilder.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Construye el ItemStack final con todas las propiedades establecidas.
     *
     * @return El ItemStack construido.
     */
    public ItemStack build() {
        // Aplicar encantamientos
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        // Definir si es irrompible
        meta.setUnbreakable(unbreakable);

        // Añadir banderas de ítem
        for (ItemFlag flag : itemFlags) {
            meta.addItemFlags(flag);
        }

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
            .addEnchantment("ENCHANTMENT_NAME", INT)
            .setUnbreakable(BOOL)
            .addItemFlag("ITEMFLAG_NAME")
            .setCustomModelData(INT)
            .build();
    target.getInventory().addItem(StaffStickItem);
        
*/
