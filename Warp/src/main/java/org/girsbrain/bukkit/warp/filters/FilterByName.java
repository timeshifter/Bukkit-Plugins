package org.girsbrain.bukkit.warp.filters;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 *
 * @author jlogsdon
 */
public class FilterByName extends WarpFilter {
    public enum Matcher {
        CONTAINS,
        BEGINS,
        EXACT
    }

    private String name;
    private Matcher matcher = Matcher.CONTAINS;

    public FilterByName(String name) {
        this.name = name.toLowerCase();
    }

    public FilterByName(String name, Matcher matcher) {
        this.name = name.toLowerCase();
        this.matcher = matcher;
    }

    @Override
    public boolean apply(Warp target) {
        switch (matcher) {
            case BEGINS:
                return target.getName().toLowerCase().startsWith(name);
            case CONTAINS:
                return target.getName().toLowerCase().contains(name);
            case EXACT:
                return target.getName().equalsIgnoreCase(name);
        }

        return false;
    }
}