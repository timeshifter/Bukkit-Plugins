package org.girsbrain.utils.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.utils.permissions.PermissionsHandler;

/**
 *
 * @author jlogsdon
 */
abstract public class Command {
    protected String[] permissions = new String[0];
    protected String[] aliases = new String[0];
    protected String name;
    protected String description;
    protected String usage;
    protected String[] help;

    public final String[] getAliases() {
        return aliases;
    }

    public final String getDescription() {
        return description;
    }

    public final String getName() {
        return name;
    }

    public final String[] getPermissions() {
        return permissions;
    }

    public final String getUsage() {
        return usage;
    }

    public String getSignature(String mainCommand) {
        StringBuilder signature = new StringBuilder();
        signature.append(ChatColor.RED).append("/").append(mainCommand);
        signature.append(" ").append(ChatColor.YELLOW).append(getName());

        if (usage != null) {
            signature.append(" ").append(ChatColor.GREEN).append(getUsage());
        }

        return signature.toString();
    }

    public void sendUsage(CommandSender sender, String mainCommand) {
        StringBuilder toSend = new StringBuilder(getSignature(mainCommand));

        if (description != null && (usage == null || !(sender instanceof Player))) {
            toSend.append(ChatColor.WHITE).append(" - ").append(getDescription());
        }

        sender.sendMessage(toSend.toString());
    }

    public void sendHelp(CommandSender sender, String mainCommand) {
        sender.sendMessage(getSignature(mainCommand));

        if (help == null && description != null) {
            sender.sendMessage(ChatColor.YELLOW + getDescription());
        } else if (help != null) {
            for (String line : help) {
                sender.sendMessage(ChatColor.YELLOW + line);
            }
        }
    }

    public final boolean matches(String command) {
        if (name.equalsIgnoreCase(command)) {
            return true;
        }
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(command)) {
                return true;
            }
        }

        return false;
    }

    public final boolean hasPermission(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return true;
        }

        for (String permission : getPermissions()) {
            if (PermissionsHandler.hasPermission(sender, permission)) {
                return true;
            }
        }

        return false;
    }

    abstract public void execute(CommandSender sender, String[] args);
}