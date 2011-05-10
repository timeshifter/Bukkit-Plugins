package org.girsbrain.bukkit.general;

import org.girsbrain.bukkit.general.commands.*;
import org.girsbrain.utils.BasePlugin;

/**
 * @author jlogsdon
 */
public class GeneralPlugin extends BasePlugin {
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    protected final Class clazz = getClass();

    @Override
    protected void registerCommands() {
        getCommand("compass").setExecutor(new CompassCommand(this));
        getCommand("getpos").setExecutor(new GetPositionCommand(this));
        getCommand("msg").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));
        getCommand("who").setExecutor(new PlayerListCommand(this));
    }
}