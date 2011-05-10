package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.Warp;
import org.girsbrain.bukkit.warp.datasource.WarpManager;

import org.girsbrain.utils.command.Command;
import org.girsbrain.utils.permissions.PermissionsHandler;

/**
 * @author jlogsdon
 */
public class GoCommand extends Command {
    protected WarpPlugin plugin;

    public GoCommand(WarpPlugin instance) {
        plugin = instance;

        name = "go";
        usage = "<name>";
        permissions = new String[]{"warp.use.invited", "warp.use.public", "warp.use.global"};

        description = "Use a warp";
        help = new String[]{
            "Use a warp."
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        String name = args[0];
        String owner = ((Player)sender).getName();

        if (args.length == 2) {
            owner = args[0];
            name = args[1];
        }

        Warp warp = WarpManager.find((Player) sender, owner, name);

        if (null == warp) {
            sender.sendMessage(ChatColor.RED + "No warp owned by '" + ChatColor.GOLD + owner + ChatColor.GOLD + "' with the name of '" + ChatColor.GREEN + name + ChatColor.RED + "'!");
            return true;
        }

        if (warp.isGlobal() && !PermissionsHandler.hasPermission(sender, "warp.use.global")) {
            sender.sendMessage(ChatColor.RED + "You may not use global warps!");
            return true;
        } else if (warp.isPublic() && !PermissionsHandler.hasPermission(sender, "warp.use.public")) {
            sender.sendMessage(ChatColor.RED + "You may not use public warps!");
            return true;
        } else if (warp.isInvited((Player)sender) && !PermissionsHandler.hasPermission(sender, "warp.use.invited")) {
            sender.sendMessage(ChatColor.RED + "You may not use shared warps!");
            return true;
        }

        warp.teleportPlayer((Player) sender);
        return true;
    }
}