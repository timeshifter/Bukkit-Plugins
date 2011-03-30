package org.girsbrain.utils.database;

/**
 * @author jlogsdon
 */
public class ConnectionSettings {
    private String host = "localhost";
    private String user = "root";
    private String pass;
    private String name = "minecraft";
    private int port = 3306;

    public String getDsn() {
        return "jdbc:mysql://" + host + ":" + port + "/" + name;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
}