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
public class Uninvite implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "uninvite";
    }

    public String getHelp() {
        return "/warp uninvite <name> <player>";
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

        for (int i = 1; i < args.length; i++) {
            warp.removeInvite(args[i]);
        }

        if (!instance.getWarpManager().save(warp)) {
            sender.sendMessage(ChatColor.RED + "Failed to update warp! Unknown error!");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Invite list updated!");
        }
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.invite");
    }
}