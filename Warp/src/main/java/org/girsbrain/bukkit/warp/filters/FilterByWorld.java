package org.girsbrain.bukkit.warp.filters;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 *
 * @author jlogsdon
 */
public class FilterByWorld extends WarpFilter {
    private String world;

    public FilterByWorld(String world) {
        this.world = world;
    }

    @Override
    public boolean apply(Warp target) {
        return target.getWorld().equals(world);
    }
}