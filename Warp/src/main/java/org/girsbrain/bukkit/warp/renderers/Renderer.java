package org.girsbrain.bukkit.warp.renderers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.girsbrain.bukkit.warp.datasource.Warp;
import org.girsbrain.bukkit.warp.datasource.Warp.InnerLocation;

/**
 * @author jlogsdon
 */
abstract public class Renderer {
    abstract public void render(CommandSender sender);

    /**
     * Render for CONSOLE: $NAME by $OWNER $WORLD@($X, $Y, $Z)
     *            PLAYER:  $NAME by $OWNER @($X, $Y, $Z)
     * @param sender
     * @param warp
     * @return
     */
    public static String renderWarp(CommandSender sender, Warp warp) {
        InnerLocation loc = warp.getLocation();
        String location = "";

        if (!(sender instanceof Player)) {
            location = ChatColor.RED + warp.getWorld();
        }

        location += ChatColor.AQUA + String.format("@(%.0f, %.0f, %.0f)", loc.getX(), loc.getY(), loc.getZ());
        String owner = ChatColor.LIGHT_PURPLE + warp.getOwner();
        ChatColor color = ChatColor.RED;

        if (warp.isPublic()) {
            color = ChatColor.GREEN;
        } else if (warp.isGlobal()) {
            color = ChatColor.YELLOW;
        }

        return String.format("%s%s%s by %s %s", color, warp.getName(), ChatColor.WHITE, owner, location);
    }
}