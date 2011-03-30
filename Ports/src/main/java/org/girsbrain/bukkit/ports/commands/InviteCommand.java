package org.girsbrain.bukkit.ports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class InviteCommand extends Command {
    protected PortsPlugin plugin;

    public InviteCommand(PortsPlugin instance) {
        plugin = instance;

        name = "invite";
        usage = "<warp> <player>";
        permissions = new String[]{"port.share.invite"};

        description = "Invite a user from a warp";
        help = new String[]{
            "Invites a user to your warp, giving them",
            "the ability to use it",
            "",
            "Other users may warp to your shared warp with:",
            ChatColor.AQUA + "/warp <warp> <your-name>"
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}