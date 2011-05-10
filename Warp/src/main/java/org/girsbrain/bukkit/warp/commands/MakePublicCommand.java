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
public class MakePublicCommand extends Command {
    protected WarpPlugin plugin;

    public MakePublicCommand(WarpPlugin instance) {
        plugin = instance;

        name = "public";
        usage = "<warp>";
        permissions = new String[]{"warp.share.public"};

        description = "Makes a warp public";
        help = new String[]{
            "Public warps are accessible to all users",
            "but remain namespaced to your player.",
            "",
            "Other users may warp to your public warp with:",
            ChatColor.AQUA + "/warp <warp> <your-name>"
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
        if (warp.getType() == Warp.Type.PUBLIC) {
            sender.sendMessage(ChatColor.GREEN + warp.getName() + ChatColor.RED + " is already public!");
            return true;
        }

        warp.setType(Warp.Type.PUBLIC);

        if (WarpManager.update(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Warp is now public!");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to save warp! Unknown reason!");
        }
        return true;
    }
}