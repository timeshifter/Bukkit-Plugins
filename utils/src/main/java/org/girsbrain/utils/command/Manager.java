package org.girsbrain.utils.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.girsbrain.utils.BasePlugin;
import org.girsbrain.utils.StringFormatter;

/**
 * @author jlogsdon
 */
public class Manager implements CommandExecutor {
    private List<Command> commands = new ArrayList<Command>();
    private BasePlugin plugin;
    private String mainCommand;

    public Manager(BasePlugin instance, String mainCommand) {
        plugin = instance;
        this.mainCommand = mainCommand;
    }

    public void registerCommand(Command command) {
        plugin.getLogger().info("Registering command: " + command.getName());
        commands.add(command);
    }

    public Command findCommand(String command) {
        for (Command cmd : commands) {
            if (cmd.matches(command)) {
                return cmd;
            }
        }

        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        String priCommand = command.getName().toLowerCase();
        if (!priCommand.equalsIgnoreCase(mainCommand)) {
            return false;
        }

        String subCommand = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);

        Command localCommand = findCommand(subCommand);
        if (localCommand == null) {
            return handleHelp(sender, subCommand.equalsIgnoreCase("help") ? subArgs : args);
        }
        if (!localCommand.hasPermission(sender)) {
            return false;
        }

        return localCommand.execute(sender, subArgs);
    }

    private boolean handleHelp(CommandSender sender, String[] args) {
        System.out.println(StringFormatter.join(args));
        // Display master help
        if (args.length == 0) {
            boolean hasCommand = false;
            for (Command command : commands) {
                if (!command.hasPermission(sender)) {
                    continue;
                }
                hasCommand = true;
                command.sendUsage(sender, mainCommand);
            }
            return hasCommand;
        }

        String subCommand = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);

        Command localCommand = findCommand(subCommand);
        if (localCommand != null) {
            localCommand.sendHelp(sender, mainCommand);
            return true;
        }

        return false;
    }
}