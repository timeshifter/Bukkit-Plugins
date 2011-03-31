package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class SearchCommand extends Command {
    protected WarpPlugin plugin;

    public SearchCommand(WarpPlugin instance) {
        plugin = instance;

        name = "search";
        usage = "<query> [page]";
        permissions = new String[]{"warp.use.own", "warp.use.shared"};

        description = "Search warps you have access to";
        help = new String[]{
            "Query all available warps. The query will search",
            "against the warps name and creator."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}