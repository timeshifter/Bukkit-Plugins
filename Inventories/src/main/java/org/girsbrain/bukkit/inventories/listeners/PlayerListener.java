package org.girsbrain.bukkit.inventories.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.girsbrain.bukkit.inventories.InventoriesPlugin;

/**
 * @author jlogsdon
 */
public class PlayerListener extends org.bukkit.event.player.PlayerListener {
    private final InventoriesPlugin plugin;

    public PlayerListener(InventoriesPlugin instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        plugin.getManager().loadInventory(player);
        plugin.getPlayerWorlds().put(player.getName(), world.getName());
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getManager().saveInventory(player);
        plugin.getPlayerWorlds().remove(player.getName());
    }
}