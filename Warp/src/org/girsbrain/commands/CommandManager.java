package org.girsbrain.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.girsbrain.warp.Log;
import org.girsbrain.warp.WarpPlugin;

/**
 * @author jlogsdon
 */
public class CommandManager {
    private final HashMap<String, ICommand> commands = new HashMap<String, ICommand>();
    private final HashMap<String, String> aliasCache = new HashMap<String, String>();
    private final WarpPlugin plugin;

    public CommandManager(WarpPlugin instance) {
        plugin = instance;
    }

    public void registerCommand(Class<?> clazz) {
        try {
            ICommand command = (ICommand) clazz.newInstance();
            String cmd = command.getName().toLowerCase();
            commands.put(cmd, command);
            
            // Build the alias cache
            for (String alias : command.getAliases()) {
                aliasCache.put(alias.toLowerCase(), cmd);
            }

            Log.info("Loaded command: " + command.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempt to handle a command from either the console or in-game chat.
     *
     * @param sender
     * @param command
     * @param commandLabel
     * @param args
     * @return false if the command is not handled.
     */
    public boolean handler(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length == 0) {
            showHelp(sender);
            return true;
        }

        // Save the sub-command then shift if off the args array
        String subCommand = args[0];
        String[] subArgs = new String[] {};
        if (args.length > 1) {
            subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        }

        sender.sendMessage("Sent: " + subCommand);
        if (subCommand.equalsIgnoreCase("help")) {
            showHelp(sender);
            return true;
        }

        ICommand cmd = findCommand(subCommand);
        if (cmd == null) {
            cmd = findCommand("goto");
            if (cmd == null) {
                return false;
            }
            if (subArgs.length > 0) {
                subArgs = new String[] {subCommand, subArgs[0]};
            } else {
                subArgs = new String[] {subCommand};
            }
        }

        // Validate permissions
        if (!cmd.validate(plugin, sender, subArgs)) {
            return false;
        }

        if (!cmd.execute(plugin, sender, subArgs)) {
            sender.sendMessage(cmd.getHelp());
        }
        return true;
    }

    /**
     * Locate a command by name. Checks aliases and uses a cache for quicker
     * future lookups.
     *
     * @param cmd
     * @return
     */
    private ICommand findCommand(String cmd) {
        // We use all lowercase to ensure integrity
        cmd = cmd.toLowerCase();

        // Check the alias cache and update the command as needed
        if (aliasCache.containsKey(cmd)) {
            cmd = aliasCache.get(cmd);
        }

        // Try to find by full-name first
        if (commands.containsKey(cmd)) {
            return commands.get(cmd);
        }

        // At this point we just cannot find the command
        return null;
    }

    private void showHelp(CommandSender sender) {
        for (ICommand cmd : commands.values()) {
            sender.sendMessage(ChatColor.RED + "/warp " + cmd.getName() + " " + cmd.getHelp());
        }
    }
}
