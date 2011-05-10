package org.girsbrain.bukkit.general.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.general.CommandHandler;
import org.girsbrain.bukkit.general.GeneralPlugin;

import org.girsbrain.utils.StringFormatter;

/**
 * @author jlogsdon
 */
public class ReplyCommand extends CommandHandler {
    public ReplyCommand(GeneralPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (anonymousCheck(sender) || args.length < 1) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        Player player = (Player)sender;
        CommandSender target = getTarget(player);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "There is nobody to reply to!");
            return true;
        }

        String name = "Anonymous";
        String[] message = new String[args.length - 1];
        System.arraycopy(args, 1, message, 0, args.length - 1);

        if (sender instanceof Player) {
            name = ((Player)sender).getDisplayName();
        }

        target.sendMessage(String.format("[%s]-> %s", player.getDisplayName(), StringFormatter.join(message)));
        sender.sendMessage(String.format("[%s]<- %s", name, StringFormatter.join(message)));

        return true;
    }

    private CommandSender getTarget(Player player) {
        PluginCommand command = plugin.getCommand("msg");

        if (command != null && command.getExecutor() instanceof MessageCommand) {
            return ((MessageCommand)command.getExecutor()).getLastSender(player);
        } else {
            return null;
        }
    }
}