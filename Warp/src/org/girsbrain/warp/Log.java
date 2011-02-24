package org.girsbrain.warp;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    static private Logger log;

    static public void severe(String msg) { write(Level.SEVERE, msg); }
    static public void warning(String msg) { write(Level.WARNING, msg); }
    static public void info(String msg) { write(Level.INFO, msg); }
    static public void config(String msg) { write(Level.CONFIG, msg); }
    static public void fine(String msg) { write(Level.FINE, msg); }
    static public void finer(String msg) { write(Level.FINER, msg); }
    static public void finest(String msg) { write(Level.FINEST, msg); }

    static public void write(Level lvl, String msg) {
        if (null == log) {
            log = Logger.getLogger("Minecraft");
        }

        log.log(lvl, "[Warp] " + msg);
    }

    static public void severe(String msg, Throwable e) { write(Level.SEVERE, msg, e); }
    static public void warning(String msg, Throwable e) { write(Level.WARNING, msg, e); }
    static public void info(String msg, Throwable e) { write(Level.INFO, msg, e); }
    static public void config(String msg, Throwable e) { write(Level.CONFIG, msg, e); }
    static public void fine(String msg, Throwable e) { write(Level.FINE, msg, e); }
    static public void finer(String msg, Throwable e) { write(Level.FINER, msg, e); }
    static public void finest(String msg, Throwable e) { write(Level.FINEST, msg, e); }

    static public void write(Level lvl, String msg, Throwable e) {
        if (null == log) {
            log = Logger.getLogger("Minecraft");
        }

        log.log(lvl, "[Warp] " + msg, e);
    }
}