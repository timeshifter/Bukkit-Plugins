package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class UninviteCommand extends Command {
    protected WarpPlugin plugin;

    public UninviteCommand(WarpPlugin instance) {
        plugin = instance;

        name = "uninvite";
        usage = "<warp> <player>";
        permissions = new String[]{"warp.share.invite"};

        description = "Uninvite a user from a warp";
        help = new String[]{"Removes a users access to a private warp."};
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