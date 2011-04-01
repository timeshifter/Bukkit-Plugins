package org.girsbrain.bukkit.warp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.WarpManager;
import org.girsbrain.bukkit.warp.datasource.WarpSet;
import org.girsbrain.bukkit.warp.filters.FilterByAccess;
import org.girsbrain.bukkit.warp.renderers.*;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class ListCommand extends Command {
    protected WarpPlugin plugin;

    public ListCommand(WarpPlugin instance) {
        plugin = instance;

        name = "list";
        aliases = new String[]{"ls"};
        usage = "[player] [page]";
        permissions = new String[]{"warp.use.own", "warp.use.other"};

        description = "List available warps";
        help = new String[]{
            "List all avaiable warps or available warps by player.",
            "",
            "LEGEND",
            ChatColor.RED + "  Private",
            ChatColor.GREEN + "  Public",
            ChatColor.YELLOW + "  Global"
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        WarpSet set = WarpManager.set();
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