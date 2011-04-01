package org.girsbrain.bukkit.warp.renderers;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.datasource.WarpSet;

/**
 * @author jlogsdon
 */
public class PlayerRenderer extends Renderer {
    private int perPage = 7;
    private int page = 1;
    private final WarpSet set;

    public PlayerRenderer(WarpSet set) {
        this.set = set;
    }

    public int getPages() {
        return (int) Math.ceil(set.size() / perPage);
    }

    public void setPage(int page) {
        if (page > 0 && page <= getPages()) {
            this.page = page;
        }
    }

    @Override
    public void render(CommandSender sender) {
    }
}