package org.girsbrain.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.commands.ICommand;
import org.girsbrain.warp.Log;
import org.girsbrain.utils.StringFormatter;
import org.girsbrain.warp.Warp;
import org.girsbrain.warp.Warp.Visibility;
import org.girsbrain.warp.WarpPlugin;
import org.girsbrain.warp.WarpManager;

/**
 * @author jlogsdon
 */
public class Create implements ICommand {
    public String[] getAliases() {
        return new String[]{
            "add",
            "c"
        };
    }

    public String getName() {
        return "create";
    }

    public String getHelp() {
        return "/warp create <name> [private|public] [invites]";
    }

    /**
     * create <name> [private|public] [player [player [...]]]
     *
     * @param instance
     * @param sender
     * @param args
     */
    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args) {
        if (args.length == 0) {
            return false;
        }

        WarpManager manager = instance.getWarpManager();
        String name = args[0];

        if (args.length > 1) {
            System.arraycopy(args, 1, args, 0, args.length - 1);
        } else {
            args = new String[] {};
        }

        // Warp already exists by this name for the player
        if (null != manager.get((Player) sender, name)) {
            sender.sendMessage(ChatColor.RED + "A warp by that name already exists");
            return true;
        }

        Warp warp = new Warp((Player) sender, name);

        // Check for visibility setting
        if (args.length > 0) {
            Visibility vis = Visibility.fromString(args[0]);
            if (vis != null) {
                warp.setVisibility(vis);
                if (args.length > 1) {
                    System.arraycopy(args, 1, args, 0, args.length - 1);
                } else {
                    args = new String[] {};
                }
            }
        }

        // And now invited players
        if (args.length > 0) {
            for (String player : args) {
                warp.addInvite(player);
            }
        }

        if (manager.add(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Successfully created the warp " + ChatColor.RED + warp.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to create warp! Unknown error!");
        }
        return true;
    }

    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        return WarpPlugin.hasPermission((Player) sender, "warp.own");
    }
}