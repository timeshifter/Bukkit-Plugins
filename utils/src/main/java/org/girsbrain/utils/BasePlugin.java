package org.girsbrain.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.girsbrain.utils.permissions.PermissionsHandler;

/**
 * @author jlogsdon
 */
abstract public class BasePlugin extends JavaPlugin {
    protected Log logger;
    protected BetterConfig config;
    protected final Class clazz = getClass();

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        if (!handleUpdates()) {
            return;
        }

        PermissionsHandler.initialize(getServer());
        registerCommands();
        registerEvents();
    }

    protected void registerCommands() {
    }

    protected void registerEvents() {
    }

    protected void registerEvent(Listener listener, Event.Type event) {
        registerEvent(listener, event, Event.Priority.Normal);
    }

    protected void registerEvent(Listener listener, Event.Type type, Event.Priority priority) {
        getServer().getPluginManager().registerEvent(type, listener, priority, this);
    }

    public final Log getLogger() {
        if (logger == null) {
            logger = new Log(getDescription().getName());
        }

        return logger;
    }

    public final boolean handleUpdates() {
        Updatr updater = new Updatr(this);

        if (updater.hasPluginUpdate()) {
            getLogger().warning("Version " + updater.getLatestVersion().getVersion() + " is available!");
        }

        // Disable plugin if libraries aren't downloaded
        if (!updater.updateLibraries()) {
            getLogger().severe("Unable to download all required libraries! Disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    protected boolean loadConfiguration() {
        File configFile = copyResourceToFile("config.yml");

        if (null == configFile) {
            getLogger().severe("Configuration file could not be created!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        config = new BetterConfig(configFile);
        config.load();
        return true;
    }

    protected File getFilePath(String fileName) {
        return new File(getDataFolder(), fileName);
    }

    protected InputStream getFileAsStream(String fileName) throws FileNotFoundException {
        File filePath = getFilePath(fileName);

        if (!filePath.exists() && null == copyResourceToFile(fileName)) {
            return null;
        }

        return new FileInputStream(filePath);
    }

    protected File copyResourceToFile(String fileName) {
        File filePath = getFilePath(fileName);

        if (filePath.exists()) {
            return filePath;
        }

        InputStream stream = clazz.getResourceAsStream("/" + fileName);
        FileWriter writer = null;

        if (stream == null) {
            getLogger().severe("Failed to load resource " + fileName + " from " + clazz.getCanonicalName());
            return null;
        }

        try {
            createDataFolder();
            writer = new FileWriter(filePath);
            for (int b; 0 <= (b = stream.read());) {
                writer.write(b);
            }
            return filePath;
        } catch (IOException e) {
            getLogger().severe("Failed to copy resource " + fileName + ": " + e.getMessage());
            return null;
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException ex) {
                }
            }
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private void createDataFolder() throws IOException {
        File folder = getDataFolder();
        if (folder.exists()) {
            return;
        }
        if (folder.mkdirs()) {
            return;
        }

        throw new IOException("Failed to create data folder " + folder);
    }
}