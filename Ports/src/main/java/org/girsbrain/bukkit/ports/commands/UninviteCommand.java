package org.girsbrain.bukkit.ports.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class UninviteCommand extends Command {
    protected PortsPlugin plugin;

    public UninviteCommand(PortsPlugin instance) {
        plugin = instance;

        name = "uninvite";
        usage = "<warp> <player>";
        permissions = new String[]{"port.share.invite"};

        description = "Uninvite a user from a warp";
        help = new String[]{"Removes a users access to a private warp."};
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}