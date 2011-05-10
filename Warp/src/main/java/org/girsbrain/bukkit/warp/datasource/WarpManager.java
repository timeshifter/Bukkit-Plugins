package org.girsbrain.bukkit.warp.datasource;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.filters.*;

/**
 * @author jlogsdon
 */
public class WarpManager {
    static private List<Warp> warps = new ArrayList<Warp>();

    static public void setWarps(List<Warp> warps) {
        WarpManager.warps = warps;
    }

    static public int count() {
        return warps.size();
    }

    static public boolean add(Warp warp) {
        if (warps.contains(warp)) {
            return false;
        }
        if (!WarpDataSource.saveWarp(warp)) {
            return false;
        }

        warps.add(warp);
        return true;
    }

    static public boolean delete(Warp warp) {
        if (!WarpDataSource.deleteWarp(warp)) {
            return false;
        }

        warps.remove(warp);
        return true;
    }

    public static boolean update(Warp warp) {
        if (!warps.contains(warp)) {
            return false;
        }
        return WarpDataSource.saveWarp(warp);
    }

    /**
     * Get a filterable list of warps.
     *
     * @return
     */
    static public WarpSet set() {
        return new WarpSet(new ArrayList<Warp>(warps));
    }

    /**
     * Find a single warp by exact match
     *
     * @param player
     * @param name
     * @return
     */
    static public Warp get(Player player, String name) {
        return find(player, player.getName(), name);
    }

    /**
     * Find a single warp by exact match
     *
     * @param player
     * @param owner
     * @param name
     * @return
     */
    static public Warp get(Player player, String owner, String name) {
        WarpSet set = list(player);

        set.filter(new FilterByOwner(owner));
        set.filter(new FilterByName(name, FilterByName.Matcher.EXACT));

        return set.size() == 1 ? set.first() : null;
    }

    /**
     * Find a single warp
     *
     * @param player
     * @param name
     * @return
     */
    static public Warp find(Player player, String name) {
        return find(player, player.getName(), name);
    }

    /**
     * Find a single warp
     *
     * @param player
     * @param owner
     * @param name
     * @return
     */
    static public Warp find(Player player, String owner, String name) {
        WarpSet set = list(player);

        set.filter(new FilterByOwner(owner, FilterByOwner.Matcher.BEGINS));
        set.filter(new FilterByName(name, FilterByName.Matcher.BEGINS));

        return set.size() == 1 ? set.first() : null;
    }

    /**
     * Get a list of warps available to a player
     *
     * @param player
     * @return
     */
    static public WarpSet list(Player player) {
        return set().filter(new FilterByAccess(player));
    }

    /**
     * Search for warps by the given criteria
     *
     * @param queries
     * @return
     */
    static public WarpSet search(String[] queries) {
        WarpSet set = set();

        for (String query : queries) {
            query = query.toLowerCase();

            if (query.startsWith("w:")) {
                set.filter(new FilterByWorld(query.substring(2)));
            } else if (query.startsWith("o:")) {
                set.filter(new FilterByOwner(query.substring(2)));
            } else if (query.startsWith("t:")) {
                set.filter(new FilterByType(query.substring(2)));
            } else {
                set.filter(new FilterByName(query));
            }
        }

        return set;
    }
}