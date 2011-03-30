package org.girsbrain.bukkit.inventories.listeners;

import org.girsbrain.bukkit.inventories.InventoriesPlugin;

/**
 * @author jlogsdon
 */
public class WorldListener extends org.bukkit.event.world.WorldListener {
    private final InventoriesPlugin plugin;

    public WorldListener(InventoriesPlugin instance) {
        plugin = instance;
    }
}
