package org.girsbrain.bukkit.warp.filters;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 * @author jlogsdon
 */
public class FilterByType extends WarpFilter {
    private Warp.Type type;

    public FilterByType(Warp.Type type) {
        this.type = type;
    }

    public FilterByType(String type) {
        this.type = Warp.Type.fromStringStrict(type);
    }

    @Override
    public boolean apply(Warp target) {
        return this.type == null || target.getType().equals(type);
    }
}