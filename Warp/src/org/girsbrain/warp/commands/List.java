package org.girsbrain.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.commands.ICommand;
import org.girsbrain.warp.utils.Util;
import org.girsbrain.warp.Warp;
import org.girsbrain.warp.WarpPlugin;
import org.girsbrain.warp.utils.Paginator;

/**
 * @author jlogsdon
 */
public class List implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "list";
    }

    public String getHelp() {
        return "<page>";
    }

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        java.util.List<Warp> warps = instance.getWarpManager().list((Player) sender);
        String forPlayer = null;
        int page = 1;

        if (args.length >= 1) {
            if (Util.isInteger(args[0])) {
                page = Integer.parseInt(args[0]);
            } else {
                if (!WarpPlugin.hasPermission((Player) sender, "warp.other")) {
                    sender.sendMessage(ChatColor.RED + "Permission denied!");
                    return true;
                }

                forPlayer = args[0];
            }
        }

        Paginator displayer = new Paginator(warps, (Player) sender, forPlayer);
        displayer.display((Player) sender, page);
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.own")
                || WarpPlugin.hasPermission((Player) sender, "warp.other");
    }
}