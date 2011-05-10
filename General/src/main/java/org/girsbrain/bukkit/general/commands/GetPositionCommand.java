package org.girsbrain.bukkit.general.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.general.CommandHandler;
import org.girsbrain.bukkit.general.GeneralPlugin;

/**
 * @author jlogsdon
 */
public class GetPositionCommand extends CommandHandler {
    public GetPositionCommand(GeneralPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        Location loc = ((Player)sender).getLocation();
        String pos = String.format("%d, %d, %d", Math.round(loc.getX()), Math.round(loc.getY()), Math.round(loc.getZ()));
        sender.sendMessage(pos);
        return true;
    }
}