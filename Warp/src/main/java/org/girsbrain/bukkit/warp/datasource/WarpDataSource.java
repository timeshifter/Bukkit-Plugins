package org.girsbrain.bukkit.warp.datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.girsbrain.bukkit.warp.WarpPlugin;
import org.girsbrain.utils.StringFormatter;

import org.girsbrain.utils.database.ConnectionManager;

/**
 * @author jlogsdon
 */
public class WarpDataSource {
    private final static String WARP_TABLE = "CREATE TABLE `warps` ("
            + "`id` INTEGER AUTO_INCREMENT, "
            + "`owner` VARCHAR(32) NOT NULL, "
            + "`name` VARCHAR(32) NOT NULL, "
            + "`world` VARCHAR(32) NOT NULL, "
            + "`x` DOUBLE NOT NULL, "
            + "`y` DOUBLE NOT NULL, "
            + "`z` DOUBLE NOT NULL, "
            + "`pitch` FLOAT NOT NULL, "
            + "`yaw` FLOAT NOT NULL, "
            + "`visibility` VARCHAR(20) NOT NULL, "
            + "`permissions` TEXT, "
            + "PRIMARY KEY(`id`))";

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement delete;

    public static boolean initialize(WarpPlugin plugin) {
        try {
            if (!tableExists()) {
                plugin.getLogger().warning("Warps table does not exist, attempting to create...");
                createTable();
            }

            plugin.getLogger().info("Preparing database...");
            setupStatements();
            return true;
        } catch (SQLException ex) {
            plugin.getLogger().severe("Error preparing database: " + ex.getMessage());
        }

        return false;
    }

    public static List<Warp> getWarps(WarpPlugin plugin) {
        List<Warp> warps = new ArrayList<Warp>();
        Statement stmt = null;
        ResultSet set = null;

        try {
            Connection conn = ConnectionManager.getConnection();
            stmt = conn.createStatement();
            set = stmt.executeQuery("SELECT * FROM warps ORDER BY name ASC");

            while (set.next()) {
                try {
                    warps.add(new Warp(plugin.getServer(), set));
                } catch (Exception ex) {
                    plugin.getLogger().severe("Failed to add warp: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
            }
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
            }
        }

        return warps;
    }

    public static int getAutoIncrement() {
        try {
            Connection conn = ConnectionManager.getConnection();

            ResultSet rs = conn.createStatement().executeQuery("SELECT last_insert_id()");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public static boolean saveWarp(Warp warp) {
        try {
            if (warp.getIndex() == 0) {
                return insertWarp(warp);
            } else {
                return updateWarp(warp);
            }
        } catch (SQLException e) {
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
            return false;
        }
    }

    private static void prepare(PreparedStatement stmt, Warp warp) throws SQLException {
        stmt.setString(1, warp.getOwner());
        stmt.setString(2, warp.getName());
        stmt.setString(3, warp.getWorld());
        stmt.setDouble(4, warp.getLocation().getX());
        stmt.setDouble(5, warp.getLocation().getY());
        stmt.setDouble(6, warp.getLocation().getZ());
        stmt.setFloat(7, warp.getLocation().getPitch());
        stmt.setFloat(8, warp.getLocation().getYaw());
        stmt.setString(9, warp.getType().toString());
        stmt.setString(10, StringFormatter.join(",", warp.getInvited().toArray()));
    }

    private static boolean tableExists() throws SQLException {
        ResultSet rs = null;

        try {
            Connection conn = ConnectionManager.getConnection();
            DatabaseMetaData meta = conn.getMetaData();

            rs = conn.getMetaData().getTables(null, null, "warps", null);
            return rs.next();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    private static void createTable() throws SQLException {
        Statement st = null;

        try {
            Connection conn = ConnectionManager.getConnection();
            st = conn.createStatement();
            st.executeUpdate(WARP_TABLE);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
            }
        }
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
}
