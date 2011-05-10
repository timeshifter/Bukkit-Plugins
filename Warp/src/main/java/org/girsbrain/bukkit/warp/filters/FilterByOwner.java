package org.girsbrain.bukkit.warp.filters;

import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 *
 * @author jlogsdon
 */
public class FilterByOwner extends WarpFilter {
    public enum Matcher {
        CONTAINS,
        BEGINS,
        EXACT
    }

    private String owner;
    private Matcher matcher = Matcher.EXACT;

    public FilterByOwner(Player owner) {
        this.owner = owner.getName().toLowerCase();
    }

    public FilterByOwner(Player owner, Matcher matcher) {
        this.owner = owner.getName().toLowerCase();
        this.matcher = matcher;
    }

    public FilterByOwner(String owner) {
        this.owner = owner.toLowerCase();
    }

    public FilterByOwner(String owner, Matcher matcher) {
        this.owner = owner.toLowerCase();
        this.matcher = matcher;
    }

    @Override
    public boolean apply(Warp target) {
        switch (matcher) {
            case BEGINS:
                return target.getOwner().toLowerCase().startsWith(owner);
            case CONTAINS:
                return target.getOwner().toLowerCase().contains(owner);
            case EXACT:
                return target.getOwner().equalsIgnoreCase(owner);
        }

        return false;
    }
}