package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.Warp;
import org.girsbrain.bukkit.warp.datasource.WarpManager;
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
        if (args.length < 2) {
            return false;
        }

        String name = args[0];
        Warp warp = WarpManager.get((Player) sender, args[0]);

        if (null == warp) {
            sender.sendMessage(ChatColor.RED + "No warp by the name of '" + ChatColor.GREEN + name + ChatColor.RED + "' could be found!");
            return true;
        }

        for (int i = 1; i < args.length; i++) {
            warp.removeInvite(args[i]);
        }

        if (WarpManager.update(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Players invited!");
        } else {
            sender.sendMessage(ChatColor.RED + "Unable to update warp! Unknown reason!");
        }
        return true;
    }
}