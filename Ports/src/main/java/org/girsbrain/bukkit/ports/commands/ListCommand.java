package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListCommand extends Command {
    protected PortsPlugin plugin;

    public ListCommand(PortsPlugin instance) {
        plugin = instance;

        name = "list";
        aliases = new String[]{"ls"};
        usage = "[player] [page]";
        permissions = new String[]{"ports.list.own", "ports.list.other"};

        description = "List available warps";
        help = new String[]{
            "List all avaiable warps or available warps by player."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}