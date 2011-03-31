package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListGlobalCommand extends Command {
    protected WarpPlugin plugin;

    public ListGlobalCommand(WarpPlugin instance) {
        plugin = instance;

        name = "listg";
        aliases = new String[]{"globals"};
        usage = "[page]";
        permissions = new String[]{"warp.use.global"};

        description = "Lists global warps";
        help = new String[]{
            "List all available global warps."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}