package org.girsbrain.bukkit.warp.filters;

import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 * @author jlogsdon
 */
public class FilterByAccess extends WarpFilter {
    private Player player;

    public FilterByAccess(Player player) {
        this.player = player;
    }

    @Override
    public boolean apply(Warp target) {
        return target.hasAccess(player);
    }
}