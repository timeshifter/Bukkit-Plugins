package org.girsbrain.bukkit.ports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakePrivateCommand extends Command {
    protected PortsPlugin plugin;

    public MakePrivateCommand(PortsPlugin instance) {
        plugin = instance;

        name = "private";
        usage = "<warp>";
        permissions = new String[]{"ports.share.public"};

        description = "Makes a warp private";
        help = new String[]{
            "Private warps are only accessible by you and",
            "players you have invited to use it.",
            "",
            "See also: " + ChatColor.AQUA + "/warp invite"
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}