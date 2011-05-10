package org.girsbrain.bukkit.general.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.general.CommandHandler;
import org.girsbrain.bukkit.general.GeneralPlugin;

/**
 * @author jlogsdon
 */
public class PlayerListCommand extends CommandHandler {
    public PlayerListCommand(GeneralPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PerformList(sender);
        } else if (args.length == 1) {
            PerformWhois(sender, args);
        }
        return true;
    }

    private void PerformWhois(CommandSender sender, String[] args) {
        Player player = getPlayer(sender, args, 0);

        if (player != null) {
            Map<String, String> report = new HashMap<String, String>();
            report.put("Display Name", player.getDisplayName());
            report.put("World", player.getWorld().getName());

            if (!ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(player.getName())) {
                report.put("Username", player.getName());
            }

            sender.sendMessage("------ WHOIS report ------");
            Set<String> keys = report.keySet();

            for (String key : keys) {
                sender.sendMessage(key + ": " + report.get(key));
            }
        }
    }

    private void PerformList(CommandSender sender) {
        String result = "";
        Player[] players = plugin.getServer().getOnlinePlayers();
        int count = 0;

        for (Player player : players) {
            String name = player.getDisplayName();

            if (name.length() > 0) {
                if (result.length() > 0) result += ", ";
                result += name;
                count++;
            }
        }

        if (count == 0) {
            sender.sendMessage("There's currently nobody playing on this server!");
        } else if (count == 1) {
            sender.sendMessage("There's only one player online: " + result);
        } else {
            sender.sendMessage("Online players: " + result);
        }
    }
}