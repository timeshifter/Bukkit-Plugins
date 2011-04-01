package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakeGlobalCommand extends Command {
    protected WarpPlugin plugin;

    public MakeGlobalCommand(WarpPlugin instance) {
        plugin = instance;

        name = "global";
        usage = "<warp>";
        permissions = new String[]{"warp.share.global"};

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
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        return true;
    }
}