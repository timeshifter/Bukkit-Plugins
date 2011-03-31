package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;

import org.girsbrain.bukkit.warp.WarpPlugin;
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
    public void execute(CommandSender sender, String[] args) {
    }
}