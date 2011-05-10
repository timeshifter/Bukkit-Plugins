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
public class CreateCommand extends Command {
    protected WarpPlugin plugin;

    public CreateCommand(WarpPlugin instance) {
        plugin = instance;

        name = "create";
        usage = "<name> [public|private|global] [invite...]";
        permissions = new String[]{"warp.create"};

        description = "Create a warp";
        help = new String[]{
            "By default a private warp will be created.",
            "You may specify public, private or global when creating.",
            "",
            "You may also invite people on creation by adding their",
            "name at the end of the create statement."
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        String name = args[0];
        if (null != WarpManager.get((Player) sender, name)) {
            sender.sendMessage(ChatColor.RED + "A warp by that name already exists.");
            return true;
        }

        Warp warp = new Warp((Player) sender, name);
        int alength = 1;

        // Handle visibility
        if (args.length > alength) {
            Warp.Type type = Warp.Type.fromString(args[2]);
            if (type != null) {
                warp.setType(type);
                alength++;
            }
        }

        // Handle invitees
        if (args.length > alength) {
            for (; alength < args.length; alength++) {
                warp.addInvite(args[alength]);
            }
        }

        if (WarpManager.add(warp)) {
            sender.sendMessage(ChatColor.GREEN + "Successfully created the warp " + ChatColor.RED + warp.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to create warp! Unknown error!");
        }
        return true;
    }
}