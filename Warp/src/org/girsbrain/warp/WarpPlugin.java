package org.girsbrain.warp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.anjocaido.groupmanager.GroupManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.girsbrain.commands.CommandManager;
import org.girsbrain.warp.commands.*;
import org.girsbrain.warp.datasource.ConnectionManager;
import org.girsbrain.warp.datasource.WarpDataSource;

/**
 * @author jlogsdon
 */
public class WarpPlugin extends JavaPlugin {
    private final CommandManager commandManager = new CommandManager(this);
    private final ArrayList<Player> debugging = new ArrayList<Player>();
    private WarpManager warpManager;
    public static PermissionHandler Permissions = null;

    public WarpManager getWarpManager() {
        return warpManager;
    }

    @Override
    public void onDisable() {
        Log.info("Plugin disabled");
    }

    @Override
    public void onEnable() {
        // Try for GroupManager
        {
            Plugin plugin = this.getServer().getPluginManager().getPlugin("GroupManager");
            if (plugin != null) {
                if (!plugin.isEnabled()) {
                    this.getServer().getPluginManager().enablePlugin(plugin);
                }
                GroupManager gm = (GroupManager) plugin;
                Permissions = gm.getPermissionHandler();
                Log.info("GroupManager setup!");
            }
        }
        // Try for Permissions
        if (Permissions == null) {
            Plugin plugin = this.getServer().getPluginManager().getPlugin("Permissions");
            if (plugin != null) {
                if (!plugin.isEnabled()) {
                    this.getServer().getPluginManager().enablePlugin(plugin);
                }
                Permissions = ((Permissions) plugin).getHandler();
                Log.info("Permissions setup!");
            }
        }

        if (Permissions == null) {
            Log.severe("Unable to load GroupManager or Permissions! Op status will be used instead.");
        }

        registerCommands();
        setupDatabase();

        Log.info("Plugin enabled [Version " + getDescription().getVersion() + "]");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return commandManager.handler(sender, command, commandLabel, args);
    }

    private void setupDatabase() {
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        String path = folder.getAbsolutePath() + File.separator + "warps.db";

        Connection conn = ConnectionManager.createConnection("jdbc:sqlite:" + path);
        if (conn == null) {
            Log.severe("Could not establish SQL connection! Disabling Warps!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!WarpDataSource.initialize()) {
            Log.severe("Failed to initialize data source");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        warpManager = new WarpManager(this);
    }

    private void registerCommands() {
        registerCommand(Create.class);
        registerCommand(Delete.class);
        registerCommand(Goto.class);
        registerCommand(List.class);
        registerCommand(ListGlobal.class);
        registerCommand(Search.class);
        registerCommand(MakePublic.class);
        registerCommand(MakePrivate.class);
        registerCommand(MakeGlobal.class);
        registerCommand(Invite.class);
        registerCommand(Uninvite.class);
    }

    public boolean toggleDebug(Player player) {
        if (!debugging.contains(player)) {
            debugging.add(player);
            return true;
        } else {
            debugging.remove(player);
            return false;
        }
    }

    public boolean isDebugging(Player player) {
        return debugging.contains(player);
    }

    protected void registerEvent(Listener listener, Type type) {
        registerEvent(listener, type, Priority.Normal);
    }

    protected void registerEvent(Listener listener, Type type, Priority priority) {
        Log.info("-> " + type.toString());

        getServer().getPluginManager().registerEvent(type, listener, priority, this);
    }

    protected void registerCommand(Class<?> clazz) {
        commandManager.registerCommand(clazz);
    }

    protected void createDefaultConfiguration(Class<?> plugin, String name) {
        File actual = new File(getDataFolder(), name);
        if (actual.exists()) {
            return;
        }

        InputStream input = plugin.getResourceAsStream("/defaults/" + name);
        if (input == null) {
            return;
        }

        FileOutputStream output = null;
        try {
            output = new FileOutputStream(actual);
            byte[] buf = new byte[8192];
            int length = 0;

            while ((length = input.read(buf)) > 0) {
                output.write(buf, 0, length);
            }

            Log.info("Default configuration file written: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {}

            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {}
        }
    }

    public static boolean hasPermission(Player player, String node) {
        if (Permissions == null) {
            return player.isOp();
        } else {
            return Permissions.has(player, node)
                    || Permissions.has(player, "warp.admin");
        }
    }

    public static boolean isAdmin(Player player) {
        if (Permissions == null) {
            return player.isOp();
        } else {
            return Permissions.has(player, "warp.admin");
        }
    }
}