package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.Warp;
import org.girsbrain.bukkit.warp.datasource.WarpManager;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class MakeGlobalCommand extends Command {
    protected WarpPlugin plugin;

    public MakeGlobalCommand(WarpPlugin instance) {
        plugin = instance;

        name = "global";
        usage = "<warp>";
        permissions = new String[]{"warp.share.global"};

        description = "Makes a warp global";
        help = new String[]{
            "Global warps are available to all players",
            "and are not namespaced to your name.",
            "",
            "Other users may warp to your public warp with:",
            ChatColor.AQUA + "/warp <warp>"
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        String name = args[0];
        Warp warp = WarpManager.get((Player) sender, args[0]);

        if (null == warp) {
            sender.sendMessage(ChatColor.RED + "No warp by the name of '" + ChatColor.GREEN + name + ChatColor.RED + "' could be found!");
            return true;
        }
        if (warp.getType() == Warp.Type.GLOBAL) {
            sender.sendMessage(ChatColor.GREEN + warp.getName() + ChatColor.RED + " is already global!");
            return true;
        }

        warp.setType(Warp.Type.GLOBAL);

        if (WarpManager.update(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Warp is now global!");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to save warp! Unknown reason!");
        }
        return true;
    }
}