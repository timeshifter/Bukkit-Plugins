package org.girsbrain.bukkit.warp;

import org.girsbrain.bukkit.warp.commands.*;
import org.girsbrain.bukkit.warp.datasource.WarpDataSource;
import org.girsbrain.bukkit.warp.datasource.WarpManager;

import org.girsbrain.utils.BasePlugin;
import org.girsbrain.utils.command.Manager;
import org.girsbrain.utils.database.ConnectionManager;
import org.girsbrain.utils.database.ConnectionSettings;

/**
 * @author jlogsdon
 */
public class WarpPlugin extends BasePlugin {
    protected ConnectionSettings settings;
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    protected final Class clazz = getClass();

    @Override
    public void onEnable() {
        if (!loadConfiguration()) {
            return;
        }

        super.onEnable();
        settings = new ConnectionSettings();
        settings.setHost(config.getString("database.host", settings.getHost()));
        settings.setName(config.getString("database.name", settings.getName()));
        settings.setUser(config.getString("database.user", settings.getUser()));
        settings.setPass(config.getString("database.pass", settings.getPass()));
        settings.setPort(config.getInt("database.port", settings.getPort()));
        ConnectionManager.createConnection(this, settings);

        if (!WarpDataSource.initialize(this)) {
            getLogger().severe("Setup did not complete! Disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            WarpManager.setWarps(WarpDataSource.getWarps(this));
            getLogger().info(String.format("Loaded %d warps", WarpManager.count()));
        }
    }

    @Override
    protected void registerCommands() {
        Manager commands = new Manager(this, "warp");
        commands.registerCommand(new CreateCommand(this));
        commands.registerCommand(new DeleteCommand(this));
        commands.registerCommand(new ListCommand(this));
        commands.registerCommand(new ListGlobalCommand(this));
        commands.registerCommand(new SearchCommand(this));
        commands.registerCommand(new InviteCommand(this));
        commands.registerCommand(new UninviteCommand(this));
        commands.registerCommand(new MakeGlobalCommand(this));
        commands.registerCommand(new MakePrivateCommand(this));
        commands.registerCommand(new MakePublicCommand(this));
        getCommand("warp").setExecutor(commands);
    }
}
