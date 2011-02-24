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
public class Delete implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "delete";
    }

    public String getHelp() {
        return "/warp delete <name>";
    }

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }

        String player = ((Player) sender).getName();
        String name = args[0];

        Warp warp = instance.getWarpManager().get(player, name);
        if (null == warp) {
            sender.sendMessage(ChatColor.RED + "Warp not found!");
            return true;
        }

        if (instance.getWarpManager().delete(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Warp deleted!");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to delete warp! Unknown error!");
        }
        return false;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return instance.hasPermission((Player) sender, "warp.own")
                || instance.hasPermission((Player) sender, "warp.admin");
    }
}