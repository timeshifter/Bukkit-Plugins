package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakePrivateCommand extends Command {
    protected WarpPlugin plugin;

    public MakePrivateCommand(WarpPlugin instance) {
        plugin = instance;

        name = "private";
        usage = "<warp>";
        permissions = new String[]{"warp.share.public"};

        description = "Makes a warp private";
        help = new String[]{
            "Private warps are only accessible by you and",
            "players you have invited to use it.",
            "",
            "See also: " + ChatColor.AQUA + "/warp invite"
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