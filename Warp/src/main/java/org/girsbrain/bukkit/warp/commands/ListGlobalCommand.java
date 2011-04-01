package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.Warp;
import org.girsbrain.bukkit.warp.datasource.WarpManager;
import org.girsbrain.bukkit.warp.datasource.WarpSet;
import org.girsbrain.bukkit.warp.filters.FilterByAccess;
import org.girsbrain.bukkit.warp.filters.FilterByType;
import org.girsbrain.bukkit.warp.renderers.*;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListGlobalCommand extends Command {
    protected WarpPlugin plugin;

    public ListGlobalCommand(WarpPlugin instance) {
        plugin = instance;

        name = "listg";
        aliases = new String[]{"globals"};
        usage = "[page]";
        permissions = new String[]{"warp.use.global"};

        description = "Lists global warps";
        help = new String[]{
            "List all available global warps."
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        WarpSet set = WarpManager.set().filter(new FilterByType(Warp.Type.GLOBAL));
        Renderer renderer = new ConsoleRenderer(set);

        if (sender instanceof Player) {
            set.filter(new FilterByAccess((Player) sender));
            renderer = new PlayerRenderer(set);

            if (args.length > 0) {
                ((PlayerRenderer)renderer).setPage(Integer.parseInt(args[0]));
            }
        }

        renderer.render(sender);
        return true;
    }
}