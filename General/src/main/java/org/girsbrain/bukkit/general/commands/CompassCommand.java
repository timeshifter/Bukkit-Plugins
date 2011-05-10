package org.girsbrain.bukkit.general.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.girsbrain.bukkit.general.CommandHandler;
import org.girsbrain.bukkit.general.GeneralPlugin;

/**
 * @author jlogsdon
 */
public class CompassCommand extends CommandHandler {
    public CompassCommand(GeneralPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run from the console!");
            return true;
        }

        sender.sendMessage("Direction: " + getDirection(getRotation((Player) sender)));
        return true;
    }

    private double getRotation(Player sender) {
        double degreeRotation = ((sender.getLocation().getYaw() - 90) % 360);
        if(degreeRotation < 0) {
            degreeRotation += 360.0;
        }
        return degreeRotation;
    }

    private String getDirection(double degrees) {
        if(0 <= degrees && degrees < 22.5) return "N";
        else if(22.5 <= degrees && degrees < 67.5) return "NE";
        else if(67.5 <= degrees && degrees < 112.5) return "E";
        else if(112.5 <= degrees && degrees < 157.5) return "SE";
        else if(157.5 <= degrees && degrees < 202.5) return "S";
        else if(202.5 <= degrees && degrees < 247.5) return "SW";
        else if(247.5 <= degrees && degrees < 292.5) return "W";
        else if(292.5 <= degrees && degrees < 337.5) return "NW";
        else if(337.5 <= degrees && degrees < 360.0) return "N";
        else return "ERR";
    }
}