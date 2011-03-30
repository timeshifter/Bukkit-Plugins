package org.girsbrain.bukkit.ports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.ports.PortsPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakeGlobalCommand extends Command {
    protected PortsPlugin plugin;

    public MakeGlobalCommand(PortsPlugin instance) {
        plugin = instance;

        name = "global";
        usage = "<warp>";
        permissions = new String[]{"ports.share.global"};

        description = "Makes a warp global";
        help = new String[]{
            "Global warps are available to all players",
            "and are not namespaced to your name.",
            "",
            "Other users may warp to your public warp with:",
            ChatColor.AQUA + "/warp <warp>"
        };
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }
}