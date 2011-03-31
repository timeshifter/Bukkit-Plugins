package org.girsbrain.bukkit.inventories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author jlogsdon
 */
public class InventoryManager {
    private final InventoriesPlugin plugin;
    private ConcurrentHashMap<String, InventoryItem[][]> inventories = new ConcurrentHashMap<String, InventoryItem[][]>();

    public InventoryManager(InventoriesPlugin instance) {
        plugin = instance;
    }

    public boolean hasInventory(Player player) {
        return hasInventory(player, player.getWorld().getName());
    }

    public boolean hasInventory(Player player, String world) {
        return inventories.containsKey(inventoryKey(player, world));
    }

    public InventoryItem[][] getInventory(Player player) {
        return getInventory(player, player.getWorld().getName());
    }

    public InventoryItem[][] getInventory(Player player, String world) {
        return inventories.get(inventoryKey(player, world));
    }

    /**
     * Load a players inventory from memory.
     *
     * @param realPlayer
     */
    public void loadInventory(Player player, String world) {
        if (!hasInventory(player, world)) {
            player.getInventory().clear();
            player.getInventory().setBoots(null);
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            saveInventory(player, world);
            return;
        }

        InventoryItem[][] stack = getInventory(player, world);
        ItemStack[] armor = objectToArmor(stack[1]);

        player.getInventory().setContents(objectToInventory(stack[0]));
        player.getInventory().setBoots(armor[0]);
        player.getInventory().setLeggings(armor[1]);
        player.getInventory().setChestplate(armor[2]);
        player.getInventory().setHelmet(armor[3]);
    }

    public void loadInventory(Player player) {
        loadInventory(player, player.getWorld().getName());
    }

    public boolean loadFromDisk() {
        File readPath = new File(plugin.getDataFolder(), "inventories.dat");
        FileInputStream readStream = null;
        ObjectInputStream objStream = null;

        if (!readPath.exists()) {
            return false;
        }

        try {
            readStream = new FileInputStream(readPath);
            objStream = new ObjectInputStream(readStream);
            inventories = (ConcurrentHashMap<String, InventoryItem[][]>) objStream.readObject();
            return true;
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        } finally {
            if (objStream != null) {
                try {
                    objStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Saves a players inventory to memory.
     *
     * @param realPlayer
     * @param fromWorld
     */
    public void saveInventory(Player player, String fromWorld) {
        InventoryItem[][] inventory = new InventoryItem[2][];
        inventory[0] = armorStackToObject(player);
        inventory[1] = inventoryToObject(player);
        inventories.put(inventoryKey(player, fromWorld), inventory);
    }

    public void saveInventory(Player player) {
        saveInventory(player, player.getWorld().getName());
    }

    public boolean saveToDisk() {
        File savePath = new File(plugin.getDataFolder(), "inventories.dat");
        FileOutputStream saveStream = null;
        ObjectOutputStream objStream = null;

        try {
            saveStream = new FileOutputStream(savePath);
            objStream = new ObjectOutputStream(saveStream);
            objStream.writeObject(inventories);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (objStream != null) {
                try {
                    objStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private String inventoryKey(Player player, String world) {
        if (plugin.getWorldShares().containsKey(world)) {
            world = plugin.getWorldShares().get(world);
        }
        return player.getName() + ":" + world;
    }
    private String inventoryKey(Player player) {
        return inventoryKey(player, player.getWorld().getName());
    }

    private InventoryItem[] itemStackToObject(ItemStack[] stack) {
        InventoryItem[] obj = new InventoryItem[stack.length];
        int i = 0;
        for (ItemStack item : stack) {
            obj[i++] = new InventoryItem(item);
        }
        return obj;
    }

    private InventoryItem[] armorStackToObject(Player player) {
        return itemStackToObject(player.getInventory().getArmorContents());
    }

    private InventoryItem[] inventoryToObject(Player player) {
        return itemStackToObject(player.getInventory().getContents());
    }

    private ItemStack[] objectToItemStack(InventoryItem[] obj) {
        ItemStack[] stack = new ItemStack[obj.length];
        int i = 0;
        for (InventoryItem item : obj) {
            stack[i++] = item.toItemStack();
        }
        return stack;
    }

    private ItemStack[] objectToArmor(InventoryItem[] armor) {
        ItemStack[] stack = objectToItemStack(armor);
        for (int i = 0; i < stack.length; i++) {
            if (stack[i].getAmount() == 0) {
                stack[i] = null;
            }
        }
        return stack;
    }

    private ItemStack[] objectToInventory(InventoryItem[] inventory) {
        return objectToItemStack(inventory);
    }
}