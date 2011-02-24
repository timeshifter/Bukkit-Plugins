package org.girsbrain.warp.datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;

import org.girsbrain.warp.Log;
import org.girsbrain.utils.StringFormatter;

import org.girsbrain.warp.Warp;

/**
 * @author jlogsdon
 */
public class WarpDataSource {
    private final static String WARP_TABLE = "CREATE TABLE `warps` ("
            + "`id` INTEGER PRIMARY KEY, "
            + "`owner` VARCHAR(32) NOT NULL, "
            + "`name` VARCHAR(32) NOT NULL, "
            + "`world` VARCHAR(32) NOT NULL, "
            + "`x` DOUBLE NOT NULL, "
            + "`y` DOUBLE NOT NULL, "
            + "`z` DOUBLE NOT NULL, "
            + "`pitch` INTEGER NOT NULL, "
            + "`yaw` INTEGER NOT NULL, "
            + "`visibility` VARCHAR(20) NOT NULL, "
            + "`permissions` TEXT)";

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement delete;

    public static boolean initialize() {
        boolean success = tableExists() || createTable();

        if (success) {
            try {
                setupStatements();
            } catch (SQLException e) {
                Log.severe("Failed to setup prepared statements", e);
                success = false;
            }
        }

        return success;
    }

    private static void setupStatements() throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        insert = conn.prepareStatement("INSERT INTO warps "
                + "(owner, name, world, x, y, z, pitch, yaw, visibility, permissions) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        update = conn.prepareStatement("UPDATE warps SET "
                + "owner = ?, name= ?, world = ?, "
                + "x = ?, y = ?, z = ?, pitch = ?, yaw = ?, "
                + "visibility = ?, permissions = ? "
                + "WHERE id = ?");
        delete = conn.prepareStatement("DELETE FROM warps WHERE id = ?");
    }

    public static ArrayList<Warp> loadWarps(Server server) {
        ArrayList<Warp> warps = new ArrayList<Warp>();
        Statement stmt = null;
        ResultSet set = null;

        try {
            Connection conn = ConnectionManager.getConnection();

            stmt = conn.createStatement();
            set = stmt.executeQuery("SELECT * FROM warps");
            while (set.next()) {
                try {
                    Warp warp = new Warp(server, set);
                    warps.add(warp);
                } catch (Exception e) {
                    Log.info("Bad warp, unable to add", e);
                }
            }

            Log.info(warps.size() + " warps loaded");
        } catch (SQLException e) {
            Log.severe("Warp load exception", e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                Log.severe("Warp load exception", e);
            }
        }

        return warps;
    }

    public static int getAutoIncrement() {
        try {
            Connection conn = ConnectionManager.getConnection();

            ResultSet rs = conn.createStatement().executeQuery("SELECT last_insert_rowid()");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Log.severe("Failed to get last rowid", e);
        }

        return 0;
    }

    public static boolean saveWarp(Warp warp) {
        try {
            if (warp.getIndex() == 0) {
                return insertWarp(warp);
            } else {
                return updateWarp(warp);
            }
        } catch (SQLException e) {
            Log.severe("Failed to save warp", e);
            return false;
        }
    }

    private static boolean insertWarp(Warp warp) throws SQLException {
        prepare(insert, warp);
        insert.execute();
        if (insert.getUpdateCount() > 0) {
            warp.setIndex(getAutoIncrement());
            return true;
        } else {
            return false;
        }
    }

    private static boolean updateWarp(Warp warp) throws SQLException {
        prepare(update, warp);
        update.setInt(11, warp.getIndex());
        update.execute();
        return update.getUpdateCount() > 0;
    }

    public static boolean deleteWarp(Warp warp) {
        try {
            delete.setInt(1, warp.getIndex());
            delete.execute();
            return delete.getUpdateCount() > 0;
        } catch (SQLException e) {
            Log.severe("Failed to delete warp", e);
            return false;
        }
    }

    private static void prepare(PreparedStatement stmt, Warp warp) throws SQLException {
        stmt.setString(1, warp.getOwner());
        stmt.setString(2, warp.getName());
        stmt.setString(3, warp.getLocation().getWorld().getName());
        stmt.setDouble(4, warp.getLocation().getX());
        stmt.setDouble(5, warp.getLocation().getY());
        stmt.setDouble(6, warp.getLocation().getZ());
        stmt.setInt(7, Math.round(warp.getLocation().getPitch()) % 360);
        stmt.setInt(8, Math.round(warp.getLocation().getYaw()) % 360);
        stmt.setString(9, warp.getVisibility().toString());
        stmt.setString(10, StringFormatter.join(",", warp.getInvites()));
    }

    private static boolean tableExists() {
        ResultSet rs = null;
        try {
            Connection conn = ConnectionManager.getConnection();

            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "warps", null);
            return rs.next();
        } catch (SQLException e) {
            Log.severe("Error checking for table", e);
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                Log.severe("Error checking for table (on close)", e);
            }
        }
    }

    private static boolean createTable() {
        Statement st = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            st = conn.createStatement();
            st.executeUpdate(WARP_TABLE);
            return true;
        } catch (SQLException e) {
            Log.severe("Failed to create table", e);
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                Log.severe("Failed to create table (on close)", e);
            }
        }
    }
}
