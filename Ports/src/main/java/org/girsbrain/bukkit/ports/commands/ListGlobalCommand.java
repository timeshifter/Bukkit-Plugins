package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListGlobalCommand extends Command {
    protected PortsPlugin plugin;

    public ListGlobalCommand(PortsPlugin instance) {
        plugin = instance;

        name = "listg";
        aliases = new String[]{"globals"};
        usage = "[page]";
        permissions = new String[]{"ports.list.global"};

        description = "Lists global warps";
        help = new String[]{
            "List all available global warps."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}