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
public class MakePrivateCommand extends Command {
    protected WarpPlugin plugin;

    public MakePrivateCommand(WarpPlugin instance) {
        plugin = instance;

        name = "private";
        usage = "<warp>";
        permissions = new String[]{"warp.share.public"};

        description = "Makes a warp private";
        help = new String[]{
            "Private warps are only accessible by you and",
            "players you have invited to use it.",
            "",
            "See also: " + ChatColor.AQUA + "/warp invite"
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
        if (warp.getType() == Warp.Type.PRIVATE) {
            sender.sendMessage(ChatColor.GREEN + warp.getName() + ChatColor.RED + " is already private!");
            return true;
        }

        warp.setType(Warp.Type.PRIVATE);

        if (WarpManager.update(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Warp is now private!");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to save warp! Unknown reason!");
        }
        return true;
    }
}