package org.girsbrain.utils.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jlogsdon
 */
public class JDCConnectionDriver implements Driver {
    public static final String URL_PREFIX = "jdbc:jdc:";
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 0;
    private ConnectionService pool;

    public JDCConnectionDriver(String driver, String url, String user, String password)
          throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        DriverManager.registerDriver(this);
        Class.forName(driver).newInstance();
        pool = new ConnectionService(url, user, password);
    }

    public Connection connect(String url, Properties props) throws SQLException {
        if (!url.startsWith(JDCConnectionDriver.URL_PREFIX)) {
            return null;
        }
        return pool.getConnection();
    }

    public boolean acceptsURL(String url) {
        return url.startsWith(JDCConnectionDriver.URL_PREFIX);
    }

    public int getMajorVersion() {
        return JDCConnectionDriver.MAJOR_VERSION;
    }

    public int getMinorVersion() {
        return JDCConnectionDriver.MINOR_VERSION;
    }

    public DriverPropertyInfo[] getPropertyInfo(String str, Properties props) {
        return new DriverPropertyInfo[0];
    }

    public boolean jdbcCompliant() {
        return false;
    }
}