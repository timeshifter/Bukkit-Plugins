package org.girsbrain.utils.permissions;

import org.bukkit.plugin.Plugin;
import org.bukkit.Server;

import com.nijikokun.bukkit.Permissions.Permissions;
import org.anjocaido.groupmanager.GroupManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author jlogsdon
 */
public class PermissionsHandler {
    private enum HandlerType {
        GROUP_MANAGER,
        PERMISSIONS,
        NONE
    };

    private static HandlerType handler;
    private static Plugin plugin;

    public static void initialize(Server server) {
        if (handler != null) {
            return;
        }

        Plugin groupManager = server.getPluginManager().getPlugin("GroupManager");
        Plugin permissions = server.getPluginManager().getPlugin("Permissions");

        if (groupManager != null) {
            handler = HandlerType.GROUP_MANAGER;
            plugin = groupManager;
        } else if (permissions != null) {
            handler = HandlerType.PERMISSIONS;
            plugin = permissions;
        } else {
            handler = HandlerType.NONE;
        }
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            return hasPermission((Player) sender, permission);
        }

        return true;
    }

    public static boolean hasPermission(Player player, String permission) {
        switch (handler) {
            case PERMISSIONS:
                return ((Permissions)plugin).getHandler().permission(player, permission);
            case GROUP_MANAGER:
                return ((GroupManager)plugin).getWorldsHolder().getWorldPermissions(player).has(player, permission);
            case NONE:
                return player.isOp();
            default:
                return player.isOp();
        }
    }
}