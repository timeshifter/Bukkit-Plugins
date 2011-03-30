package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class DeleteCommand extends Command {
    protected PortsPlugin plugin;

    public DeleteCommand(PortsPlugin instance) {
        plugin = instance;

        name = "delete";
        usage = "<name>";
        permissions = new String[]{"port.delete"};

        description = "Delete a warp";
        help = new String[]{
            "Permanently delete a warp"
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}