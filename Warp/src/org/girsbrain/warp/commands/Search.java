package org.girsbrain.warp.commands;

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
public class Search implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "search";
    }

    public String getHelp() {
        return "<query>";
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
        Paginator displayer = new Paginator(warps, (Player) sender);
        displayer.display((Player) sender, page);
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.search");
    }
}