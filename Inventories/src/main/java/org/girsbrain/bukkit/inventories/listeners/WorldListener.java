package org.girsbrain.bukkit.inventories.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldSaveEvent;

import org.girsbrain.bukkit.inventories.InventoriesPlugin;

/**
 * @author jlogsdon
 */
public class WorldListener extends org.bukkit.event.world.WorldListener {
    private final InventoriesPlugin plugin;

    public WorldListener(InventoriesPlugin instance) {
        plugin = instance;
    }

    @Override
    public void onWorldSave(WorldSaveEvent event) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            plugin.getManager().saveInventory(player);
        }

        if (!plugin.getManager().saveToDisk()) {
            plugin.getLogger().severe("Failed to save inventories to disk!");
        }
    }
}