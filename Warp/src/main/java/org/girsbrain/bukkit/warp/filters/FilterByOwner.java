package org.girsbrain.bukkit.warp.filters;

import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 *
 * @author jlogsdon
 */
public class FilterByOwner extends WarpFilter {
    private String owner;

    public FilterByOwner(Player owner) {
        this.owner = owner.getName();
    }

    public FilterByOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean apply(Warp target) {
        return target.isOwner(owner);
    }
}