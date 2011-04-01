package org.girsbrain.bukkit.warp.renderers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.girsbrain.bukkit.warp.datasource.Warp;

import org.girsbrain.bukkit.warp.datasource.WarpSet;

/**
 * @author jlogsdon
 */
public class ConsoleRenderer extends Renderer {
    private final WarpSet set;

    public ConsoleRenderer(WarpSet set) {
        this.set = set;
    }

    @Override
    public void render(CommandSender sender) {
        sender.sendMessage(String.format("%s%d warps", ChatColor.GOLD, set.size()));

        for (Warp warp : set) {
            sender.sendMessage(renderWarp(sender, warp));
        }
    }
}