package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListCommand extends Command {
    protected WarpPlugin plugin;

    public ListCommand(WarpPlugin instance) {
        plugin = instance;

        name = "list";
        aliases = new String[]{"ls"};
        usage = "[player] [page]";
        permissions = new String[]{"warp.use.own", "warp.use.other"};

        description = "List available warps";
        help = new String[]{
            "List all avaiable warps or available warps by player."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}