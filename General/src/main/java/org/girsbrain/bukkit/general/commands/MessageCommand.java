package org.girsbrain.bukkit.general.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.general.CommandHandler;
import org.girsbrain.bukkit.general.GeneralPlugin;

import org.girsbrain.utils.StringFormatter;

/**
 * @author jlogsdon
 */
public class MessageCommand extends CommandHandler {
    private Map<Player, CommandSender> lastMessages = new HashMap<Player, CommandSender>();

    public MessageCommand(GeneralPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        Player target = getPlayer(sender, args, 0);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.GREEN + args[0] + ChatColor.RED + "' does not exist!");
            return true;
        }

        String name = "Anonymous";
        String[] message = new String[args.length - 1];
        System.arraycopy(args, 1, message, 0, args.length - 1);

        if (sender instanceof Player) {
            name = ((Player)sender).getDisplayName();
        }

        target.sendMessage(String.format("[%s]-> %s", name, StringFormatter.join(message)));
        sender.sendMessage(String.format("[%s]<- %s", target.getDisplayName(), StringFormatter.join(message)));
        lastMessages.put(target, sender);

        return true;
    }

    public CommandSender getLastSender(Player player) {
        return lastMessages.get(player);
    }
}