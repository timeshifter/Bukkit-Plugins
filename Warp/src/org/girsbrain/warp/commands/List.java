package org.girsbrain.warp.commands;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.commands.ICommand;
import org.girsbrain.utils.Util;
import org.girsbrain.warp.Warp;
import org.girsbrain.warp.WarpPlugin;

/**
 * @author jlogsdon
 */
public class List implements ICommand {
    private static final int WARPS_PER_PAGE = 8;

    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "list";
    }

    public String getHelp() {
        return "/warp list <page>";
    }

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        java.util.List<Warp> warps = instance.getWarpManager().list((Player) sender);
        int pages = (int) Math.ceil(warps.size() / (double) WARPS_PER_PAGE);

        int page = 1;
        Player forPlayer = (Player) sender;

        if (args.length >= 1) {
            if (Util.isInteger(args[0])) {
                page = Integer.parseInt(args[0]);
            } else {
                if (instance.hasPermission(forPlayer, "warp.other")) {
                    sender.sendMessage(ChatColor.RED + "Permission denied!");
                    return true;
                }

                forPlayer = instance.getServer().getPlayer(args[0]);
                if (null == forPlayer) {
                    sender.sendMessage(ChatColor.RED + "Unable to find player: " + args[0]);
                    return true;
                }
            }
        }
        if (page < 1 || page > pages) {
            sender.sendMessage(ChatColor.RED + "Invalid page number! " + pages + " total pages.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "------------- Page " + page + " / " + pages + " -------------");

        int start = (page - 1) * WARPS_PER_PAGE;
        int end = Math.min(start + WARPS_PER_PAGE, warps.size());
        for (int i = start; i < end; i++) {
            Warp warp = warps.get(i);
            Location loc = warp.getLocation();

            String location = String.format(" %s@(%.0f, %.0f, %.0f)", ChatColor.AQUA, loc.getX(), loc.getY(), loc.getZ());
            String owner = String.format("%s%s by %s%s", ChatColor.WHITE, (warp.isPublic() ? "+" : "-"), ChatColor.YELLOW, warp.getOwner());
            ChatColor color = ChatColor.RED;

            if (warp.isOwner((Player) sender)) {
                color = ChatColor.AQUA;
            } else if (warp.isPublic()) {
                color = ChatColor.GREEN;
            } else if (warp.isGlobal()) {
                color = ChatColor.YELLOW;
            }

            sender.sendMessage(color + warp.getName() + owner + location);
        }

        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return instance.hasPermission((Player) sender, "warp.own")
                || instance.hasPermission((Player) sender, "warp.other")
                || instance.hasPermission((Player) sender, "warp.admin");
    }
}