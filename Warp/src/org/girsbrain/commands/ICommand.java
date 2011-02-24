package org.girsbrain.commands;

import org.bukkit.command.CommandSender;
import org.girsbrain.warp.WarpPlugin;

/**
 * @author James
 */
public interface ICommand {
    public String[] getAliases();

    public String getName();

    public String getHelp();

    public boolean execute(WarpPlugin instance, CommandSender sender, String[] args);
    public boolean validate(WarpPlugin instance, CommandSender sender, String[] args);
}