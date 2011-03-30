package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class SearchCommand extends Command {
    protected PortsPlugin plugin;

    public SearchCommand(PortsPlugin instance) {
        plugin = instance;

        name = "search";
        usage = "<query> [page]";
        permissions = new String[]{"ports.list.own", "ports.list.other"};

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