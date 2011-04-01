package org.girsbrain.bukkit.warp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.bukkit.warp.datasource.WarpManager;
import org.girsbrain.bukkit.warp.datasource.WarpSet;
import org.girsbrain.bukkit.warp.renderers.*;
import org.girsbrain.utils.command.Command;

/**
 * @author jlogsdon
 */
public class SearchCommand extends Command {
    protected WarpPlugin plugin;

    public SearchCommand(WarpPlugin instance) {
        plugin = instance;

        name = "search";
        usage = "[page] <query>";
        permissions = new String[]{"warp.use.own", "warp.use.shared"};

        description = "Search warps you have access to";
        help = new String[]{
            "Query all available warps. The query will search",
            "against the warps name and creator."
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String[] sendArgs = args;
        int page = 0;

        try {
            page = Integer.parseInt(args[0]);
            sendArgs = new String[args.length - 1];
            System.arraycopy(args, 1, sendArgs, 0, sendArgs.length);
        } catch (NumberFormatException ex) {
        }

        WarpSet set = WarpManager.search(sendArgs);
        Renderer renderer = new ConsoleRenderer(set);

        if (sender instanceof Player) {
            renderer = new PlayerRenderer(set);
            ((PlayerRenderer) renderer).setPage(page);
        }

        renderer.render(sender);
        return true;
    }
}