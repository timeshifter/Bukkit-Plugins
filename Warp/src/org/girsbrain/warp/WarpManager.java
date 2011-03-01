package org.girsbrain.warp;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

import org.girsbrain.warp.datasource.WarpDataSource;

/**
 * @author jlogsdon
 */
public class WarpManager {
    private final ArrayList<Warp> warps;
    private final WarpPlugin plugin;

    public WarpManager(WarpPlugin instance) {
        warps = WarpDataSource.loadWarps(instance.getServer());
        plugin = instance;
    }

    public boolean add(Warp warp) {
        if (warps.contains(warp)) {
            return false;
        }
        if (!save(warp)) {
            return false;
        }
        warps.add(warp);
        return true;
    }

    public Warp get(Player player, String name) {
        return get(player.getName(), name);
    }

    public Warp get(Warp warp) {
        return get(warp.getOwner(), warp.getName());
    }

    public Warp get(String player, String name) {
        for (Warp warp : warps) {
            if (!warp.isOwner(player)) {
                continue;
            }
            if (!warp.getName().equals(name)) {
                continue;
            }
            return warp;
        }

        return null;
    }

    public Warp find(Player player, String name) {
        return find(player.getName(), name);
    }

    public Warp find(String player, String name) {
        ArrayList<Warp> found = new ArrayList<Warp>();

        for (Warp warp : warps) {
            if (warp.matches(player, name)) {
                // If the name is an exact match return it!
                if (warp.getName().equalsIgnoreCase(name)) {
                    return warp;
                }

                found.add(warp);
            }
        }

        if (found.size() == 1) {
            return found.get(0);
        }

        return null;
    }

    public List<Warp> list(Player player) {
        List<Warp> list = new ArrayList<Warp>();

        for (Warp warp : warps) {
            if (warp.hasAccess(player)) {
                list.add(warp);
            }
        }

        return list;
    }

    public List<Warp> listGlobal() {
        List<Warp> list = new ArrayList<Warp>();

        for (Warp warp : warps) {
            if (warp.isGlobal()) {
                list.add(warp);
            }
        }

        return list;
    }

    public List<Warp> search(Player player, String query) {
        List<Warp> list = new ArrayList<Warp>();
        query = query.toLowerCase();

        for (Warp warp : warps) {
            if (!warp.hasAccess(player)) {
                continue;
            }
            if (warp.getName().toLowerCase().contains(query)) {
                list.add(warp);
            }
        }

        return list;
    }

    public boolean save(Warp warp) {
        return WarpDataSource.saveWarp(warp);
    }

    public boolean delete(Warp warp) {
        if (WarpDataSource.deleteWarp(warp)) {
            warps.remove(warp);
            return true;
        }

        return false;
    }
}