package org.girsbrain.bukkit.warp.datasource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.girsbrain.bukkit.warp.filters.WarpFilter;

/**
 * @author jlogsdon
 */
public class WarpSet implements Iterable<Warp> {
    private List<Warp> warps;

    public WarpSet(List<Warp> warps) {
        this.warps = warps;
    }

    public WarpSet filter(WarpFilter filter) {
        List<Warp> newWarps = new ArrayList<Warp>();

        for (Warp warp : warps) {
            if (filter.apply(warp)) {
                newWarps.add(warp);
            }
        }

        warps = newWarps;
        return this;
    }

    public WarpSet filterNegate(WarpFilter filter) {
        List<Warp> newWarps = new ArrayList<Warp>();

        for (Warp warp : warps) {
            if (filter.apply(warp)) {
                newWarps.add(warp);
            }
        }

        warps = newWarps;
        return this;
    }

    public Warp get(int idx) {
        if (idx < 0 || size() < idx) {
            throw new IndexOutOfBoundsException();
        }

        return warps.get(idx);
    }

    public Warp first() {
        return get(0);
    }

    public Warp last() {
        return get(size() - 1);
    }

    public int size() {
        return warps.size();
    }

    @Override
    public Iterator<Warp> iterator() {
        return warps.iterator();
    }
}