package org.girsbrain.bukkit.ports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakePublicCommand extends Command {
    protected PortsPlugin plugin;

    public MakePublicCommand(PortsPlugin instance) {
        plugin = instance;

        name = "public";
        usage = "<warp>";
        permissions = new String[]{"ports.share.public"};

        description = "Makes a warp public";
        help = new String[]{
            "Public warps are accessible to all users",
            "but remain namespaced to your player.",
            "",
            "Other users may warp to your public warp with:",
            ChatColor.AQUA + "/warp <warp> <your-name>"
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}