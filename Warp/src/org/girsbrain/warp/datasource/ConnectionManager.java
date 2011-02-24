package org.girsbrain.warp.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.girsbrain.warp.Log;

/**
 * @author jlogsdon
 */
public class ConnectionManager {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection createConnection(String database) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(database);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
