package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class CreateCommand extends Command {
    protected PortsPlugin plugin;

    public CreateCommand(PortsPlugin instance) {
        plugin = instance;

        name = "create";
        usage = "<name> [public|private|global] [invite...]";
        permissions = new String[]{"port.create"};

        description = "Create a warp";
        help = new String[]{
            "By default a private warp will be created.",
            "You may specify public, private or global when creating.",
            "",
            "You may also invite people on creation by adding their",
            "name at the end of the create statement."
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}