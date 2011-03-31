package org.girsbrain.bukkit.inventories;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import org.girsbrain.bukkit.inventories.listeners.*;
import org.girsbrain.utils.BasePlugin;

/**
 * @author jlogsdon
 */
public class InventoriesPlugin extends BasePlugin {
    private final PlayerListener playerListener = new PlayerListener(this);
    private final WorldListener worldListener = new WorldListener(this);
    private final InventoryManager manager = new InventoryManager(this);
    private final ConcurrentHashMap<String, String> playerWorlds = new ConcurrentHashMap<String, String>();
    private final HashMap<String, String> worldShares = new HashMap<String, String>();
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    protected final Class clazz = getClass();

    @Override
    public void onEnable() {
        if (!loadConfiguration()) {
            return;
        }

        super.onEnable();

        getLogger().info("Loading world shares");
        List<String> primaryWorlds = config.getKeys("shares");
        for (String world : primaryWorlds) {
            List<Object> shares = config.getList("shares." + world);
            if (null == shares) {
                getLogger().info("  " + world + " has no shares");
                continue;
            }

            getLogger().info("  " + world);
            for (Object share : shares) {
                getLogger().info("    " + share);
                worldShares.put(share.toString(), world);
            }
        }

        if (!manager.loadFromDisk()) {
            getLogger().severe("Failed to load inventories!");
        }
    }

    @Override
    protected void registerEvents() {
        registerEvent(playerListener, Event.Type.PLAYER_JOIN);
        registerEvent(playerListener, Event.Type.PLAYER_QUIT);
        registerEvent(worldListener, Event.Type.WORLD_SAVE);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new WorldChangeTask(), 20L, 20L);
    }

    public ConcurrentHashMap<String, String> getPlayerWorlds() {
        return playerWorlds;
    }

    public InventoryManager getManager() {
        return manager;
    }

    public HashMap<String, String> getWorldShares() {
        return worldShares;
    }

    /**
     * Check if a player has changed worlds.
     */
    class WorldChangeTask implements Runnable {
        @Override
        public void run() {
            for (String player : playerWorlds.keySet()) {
                Player realPlayer = getServer().getPlayer(player);
                String fromWorld = playerWorlds.get(player);

                if (!fromWorld.equals(realPlayer.getWorld().getName())) {
                    manager.saveInventory(realPlayer, fromWorld);
                    manager.loadInventory(realPlayer);
                    playerWorlds.put(player, realPlayer.getWorld().getName());
                }
            }
        }
    }
}