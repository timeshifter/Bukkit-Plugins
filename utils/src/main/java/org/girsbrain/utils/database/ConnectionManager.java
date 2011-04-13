package org.girsbrain.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.girsbrain.utils.BasePlugin;

/**
 * @author jlogsdon
 */
public class ConnectionManager {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:jdc:jdcpool");
        conn.setAutoCommit(false);
        return conn;
    }

    public static boolean createConnection(BasePlugin plugin, ConnectionSettings settings) {
        try {
            new JDCConnectionDriver("com.mysql.jdbc.Driver", settings.getDsn(), settings.getUser(), settings.getPass());
            return true;
        } catch (ClassNotFoundException e) {
            plugin.getLogger().severe("Could not find mysql library! Please make sure it is present and readable.");
        } catch (InstantiationException e) {
            plugin.getLogger().severe("Instantiation exception: " + e.getMessage());
        } catch (IllegalAccessException e) {
            plugin.getLogger().severe("Illegal access exception: " + e.getMessage());
        } catch (SQLException e) {
            plugin.getLogger().severe("Error during connection: " + e.getMessage());
        }

        return false;
    }
}
