package org.girsbrain.warp.commands;

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
public class Search implements ICommand {
    private static final int WARPS_PER_PAGE = 7;

    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "search";
    }

    public String getHelp() {
        return "/warp search <query>";
    }

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String query = args[0];
        int page = 1;

        if (args.length >= 2) {
            if (Util.isInteger(args[0])) {
                page = Integer.parseInt(args[0]);
                query = args[1];
            } else if (Util.isInteger(args[1])) {
                page = Integer.parseInt(args[1]);
            }
        }

        java.util.List<Warp> warps = instance.getWarpManager().search((Player) sender, query);
        int pages = (int) Math.ceil(warps.size() / (double) WARPS_PER_PAGE);
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

        return WarpPlugin.hasPermission((Player) sender, "warp.search")
                || WarpPlugin.hasPermission((Player) sender, "warp.admin");
    }
}