package org.girsbrain.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jlogsdon
 */
public class Log {
    private Logger log;
    private String prefix;

    public Log(String prefix) {
        log = Logger.getLogger("Minecraft");
        this.prefix = prefix;
    }

    public void finest(String msg) { log(Level.FINEST, msg); }
    public void finer(String msg) { log(Level.FINER, msg); }
    public void fine(String msg) { log(Level.FINE, msg); }
    public void info(String msg) { log(Level.INFO, msg); }
    public void warning(String msg) { log(Level.WARNING, msg); }
    public void severe(String msg) { log(Level.SEVERE, msg); }

    public void log(Level level, String msg) {
        log.log(level, String.format("[%s] %s", prefix, msg));
    }
}