package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;

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
    public void execute(CommandSender sender, String[] args) {
    }
}