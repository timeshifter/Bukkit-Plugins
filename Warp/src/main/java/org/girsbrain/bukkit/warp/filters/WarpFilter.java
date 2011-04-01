package org.girsbrain.bukkit.warp.filters;

import org.girsbrain.bukkit.warp.datasource.Warp;

/**
 * @author jlogsdon
 */
abstract public class WarpFilter {
    abstract public boolean apply(Warp target);
}