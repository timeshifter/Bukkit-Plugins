package org.girsbrain.groupmessage.bukkit;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import org.anjocaido.groupmanager.GroupManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.girsbrain.groupmessage.Log;
import org.girsbrain.groupmessage.utils.StringFormatter;

/**
 * @author jlogsdon
 */
public class GroupMessagePlugin extends JavaPlugin {
    private final GroupMessagePlayerListener playerListener = new GroupMessagePlayerListener(this);
    private final ArrayList<Player> debugging = new ArrayList<Player>();
    private final HashMap<Player, String> sticky = new HashMap<Player, String>();
    public static PermissionHandler Permissions = null;

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
            Log.severe("Unable to load GroupManager or Permissions! Disabling plugin...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Low, this);

        Log.info("Plugin enabled [Version " + getDescription().getVersion() + "]");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("mg")) {
            return onSendCommand(player, args);
        }
        else if(command.getName().equalsIgnoreCase("mgt")) {
            return onToggleCommand(player, args);
        }

        return false;
    }

    private boolean onSendCommand(Player sender, String[] args) {
        if (!Permissions.has(sender, "groupmessage.send")) {
            sender.sendMessage(ChatColor.RED + "Permission denied!");
            return true;
        }
        if (args.length < 2) {
            return false;
        }

        String group = args[0];
        sendToGroups(sender, group, args);
        return true;
    }

    private boolean onToggleCommand(Player sender, String[] args) {
        if (!Permissions.has(sender, "groupmessage.send")) {
            sender.sendMessage(ChatColor.RED + "Permission denied!");
            return true;
        }
        if (args.length < 1) {
            sticky.remove(sender);
            sender.sendMessage(ChatColor.RED + "GroupMessage sticky status removed!");
            return true;
        }

        sticky.put(sender, args[0]);
        sender.sendMessage(ChatColor.GREEN + "All chat will be sent to /mg " + args[0] + " until cleared");
        if (args.length > 1) {
            sendToGroups(sender, sticky.get(sender), StringFormatter.join(" ", args, 1));
        }

        return true;
    }

    public void sendToGroups(Player sender, String message) {
        sendToGroups(sender, sticky.get(sender), message);
    }

    public void sendToGroups(Player sender, String group, String[] args) {
        sendToGroups(sender, group, StringFormatter.join(" ", args, 1));
    }

    public void sendToGroups(Player sender, String group, String message) {
        if (group.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "Cannot send to an empty group!");
            return;
        }

        String pSender = "[" + sender.getName() + "] ";
        String pGroup = "[->" + group + "] ";

        sender.sendMessage(ChatColor.YELLOW + pGroup + message);
        Log.info("[" + sender.getName() + " -> " + group + "] " + message);

        for (Player player : getServer().getOnlinePlayers()) {
            if (player.equals(sender)) {
                continue;
            }
            String test = Permissions.getGroup(player.getName()).toLowerCase();
            if (test.startsWith(group) || Permissions.has(player, "groupmessage.omnipotent")) {
                player.sendMessage(ChatColor.YELLOW + pSender + message);
            }
        }
    }

    public boolean hasToggle(Player player) {
        return sticky.containsKey(player);
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
}