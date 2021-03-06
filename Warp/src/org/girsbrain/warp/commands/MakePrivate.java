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
public class MakePrivate implements ICommand {
    public String[] getAliases() {
        return new String[]{
        };
    }

    public String getName() {
        return "private";
    }

    public String getHelp() {
        return "<name>";
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

        warp.setVisibility(Warp.Visibility.PRIVATE);
        if (!instance.getWarpManager().save(warp)) {
            sender.sendMessage(ChatColor.RED + "Failed to update warp! Unknown error!");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Warp is now private!");
        }
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.share");
    }
}