package org.girsbrain.bukkit.warp.filters;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 *
 * @author jlogsdon
 */
public class FilterByGeneric extends WarpFilter {
    private String query;

    public FilterByGeneric(String query) {
        this.query = query.toLowerCase();
    }

    @Override
    public boolean apply(Warp target) {
        if (target.getName().toLowerCase().startsWith(query)) {
            return true;
        }
        if (target.isOwner(query)) {
            return true;
        }
        if (target.getWorld().equalsIgnoreCase(query)) {
            return true;
        }

        return false;
    }
}