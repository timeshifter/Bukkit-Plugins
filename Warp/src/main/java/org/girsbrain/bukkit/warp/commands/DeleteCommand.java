package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class DeleteCommand extends Command {
    protected WarpPlugin plugin;

    public DeleteCommand(WarpPlugin instance) {
        plugin = instance;

        name = "delete";
        usage = "<name>";
        permissions = new String[]{"warp.create"};

        description = "Delete a warp";
        help = new String[]{
            "Permanently delete a warp"
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        return true;
    }
}