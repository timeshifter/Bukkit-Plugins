package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakePublicCommand extends Command {
    protected WarpPlugin plugin;

    public MakePublicCommand(WarpPlugin instance) {
        plugin = instance;

        name = "public";
        usage = "<warp>";
        permissions = new String[]{"warp.share.public"};

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