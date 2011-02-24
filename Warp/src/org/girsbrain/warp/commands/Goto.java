package org.girsbrain.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.commands.ICommand;
import org.girsbrain.warp.Warp;
import org.girsbrain.warp.WarpPlugin;

/**
 * @author jlogsdon
 */
public class Goto implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "goto";
    }

    public String getHelp() {
        return "/warp goto <name>";
    }

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }

        String player = ((Player) sender).getName();
        String name = args[0];
        
        if (args.length > 1) {
            if (!WarpPlugin.hasPermission((Player) sender, "warp.other")) {
                sender.sendMessage(ChatColor.RED + "Permission denied!");
                return true;
            }
            player = args[1];
        }

        Warp warp = instance.getWarpManager().find(player, name);
        if (null == warp) {
            sender.sendMessage(ChatColor.RED + "Warp not found!");
            return true;
        }

        ((Player) sender).teleportTo(warp.getLocation());
        sender.sendMessage(ChatColor.AQUA + "Woosh!");
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