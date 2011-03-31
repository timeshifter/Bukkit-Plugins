package org.girsbrain.bukkit.inventories;

import java.io.Serializable;

import org.bukkit.inventory.ItemStack;

/**
 * @author jlogsdon
 */
final public class InventoryItem implements Serializable {
    private final static long serialVersionUID = 2433424709013450694L;
    private int typeId;
    private int quantity = 0;
    private short durability = 0;
    private byte data = 0;

    public InventoryItem(ItemStack item) {
        fromItemStack(item);
    }

    public void fromItemStack(ItemStack item) {
        typeId = item.getTypeId();
        quantity = item.getAmount();
        durability = item.getDurability();
        data = item.getData().getData();
    }

    public ItemStack toItemStack() {
        return new ItemStack(typeId, quantity, durability, data);
    }

    public int getTypeId() {
        return typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public short getDamage() {
        return durability;
    }

    public byte getData() {
        return data;
    }
}