package org.girsbrain.bukkit.warp.renderers;

import org.bukkit.ChatColor;
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
        return (int) Math.ceil((double)set.size() / (double)perPage);
    }

    public int getStart() {
        return (page - 1) * perPage;
    }

    public int getEnd() {
        return Math.min(getStart() + perPage, set.size());
    }

    public void setPage(int page) {
        if (page > 0 && page <= getPages()) {
            this.page = page;
        }
    }

    @Override
    public void render(CommandSender sender) {
        StringBuilder header = new StringBuilder();
        header.append(ChatColor.GOLD);
        header.append(set.size());
        header.append(" warps (");
        header.append(ChatColor.WHITE);
        header.append("Page ");
        header.append(ChatColor.GREEN);
        header.append(page);
        header.append(ChatColor.WHITE);
        header.append(" of ");
        header.append(ChatColor.GREEN);
        header.append(getPages());
        header.append(ChatColor.GOLD);
        header.append(")");
        sender.sendMessage(header.toString());

        for (int i = getStart(); i < getEnd(); i++) {
            sender.sendMessage(renderWarp(sender, set.get(i)));
        }
    }
}