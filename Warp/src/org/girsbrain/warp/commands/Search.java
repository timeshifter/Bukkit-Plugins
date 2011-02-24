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
        List.sendList((Player) sender, page, warps, null);
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.search");
    }
}