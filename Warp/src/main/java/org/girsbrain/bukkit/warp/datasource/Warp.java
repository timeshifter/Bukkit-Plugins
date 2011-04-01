package org.girsbrain.bukkit.warp.datasource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import org.girsbrain.utils.permissions.PermissionsHandler;

/**
 * @author jlogsdon
 */
public class Warp {
    private int index = 0;
    private String owner;
    private String name;
    private String world;
    private Location location;
    private Type type = Type.PRIVATE;
    private final List<String> invited = new ArrayList<String>();

    public Warp(Player player, String name) {
        owner = player.getName();
        this.name = name;
        world = player.getWorld().getName();
        location = new Location(player.getLocation());
    }

    public Warp(Server server, ResultSet set) throws SQLException, Exception {
        index = set.getInt("id");
        name = set.getString("name");
        owner = set.getString("owner");
        type = Type.fromString(set.getString("visibility"));
        world = set.getString("world");

        setLocation(server, set);
        invited.addAll(Arrays.asList(set.getString("permissions").split(",")));
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (this.index == 0) {
            this.index = index;
        }
    }

    public List<String> getInvited() {
        return invited;
    }

    public void addInvite(Player player) {
        addInvite(player.getName());
    }

    public void addInvite(String player) {
        if (!isInvited(player)) {
            invited.add(player.toLowerCase());
        }
    }

    public void removeInvite(Player player) {
        removeInvite(player.getName());
    }

    public void removeInvite(String player) {
        if (isInvited(player)) {
            invited.remove(player.toLowerCase());
        }
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation(Server server, ResultSet set) throws SQLException, Exception {
        if (server.getWorld(set.getString("world")) == null) {
            throw new Exception("Invalid world specified: " + set.getString("world"));
        }

        double x = set.getDouble("x");
        double y = set.getDouble("y");
        double z = set.getDouble("z");
        float pitch = set.getFloat("pitch");
        float yaw = set.getFloat("yaw");

        location = new Location(x, y, z, pitch, yaw);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getWorld() {
        return world;
    }

    public boolean hasAccess(Player player) {
        // No cross-world pollution
        if (!world.equals(player.getWorld().getName())
                && !PermissionsHandler.hasPermission(player, "warp.cross-world")) {
            return false;
        }

        // Owners always have access
        if (isOwner(player)
                || PermissionsHandler.hasPermission(player, "warp.admin")) {
            return true;
        }

        // Public warps are available to all
        if (type.equals(Type.PUBLIC)
                && PermissionsHandler.hasPermission(player, "warp.use.public")) {
            return true;
        }
        if (type.equals(Type.GLOBAL)
                && PermissionsHandler.hasPermission(player, "warp.use.global")) {
            return true;
        }

        // And finally check if the player is invited
        if (isInvited(player)
                && PermissionsHandler.hasPermission(player, "warp.use.invited")) {
            return true;
        }

        return false;
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getName());
    }

    public boolean isOwner(String player) {
        return owner.equalsIgnoreCase(player);
    }

    public boolean isInvited(Player player) {
        return isInvited(player.getName());
    }

    public boolean isInvited(String player) {
        return invited.contains(player.toLowerCase());
    }

    public boolean isPrivate() {
        return type.equals(Type.PRIVATE);
    }

    public boolean isPublic() {
        return type.equals(Type.PUBLIC);
    }

    public boolean isGlobal() {
        return type.equals(Type.GLOBAL);
    }

    public enum Type {
        PRIVATE,
        PUBLIC,
        GLOBAL;

        public static Type fromString(String string) {
            for (Type v : Type.values()) {
                if (v.toString().startsWith(string.toUpperCase())) {
                    return v;
                }
            }

            return PRIVATE;
        }

        public static Type fromStringStrict(String string) {
            for (Type v : Type.values()) {
                if (v.toString().equalsIgnoreCase(string)) {
                    return v;
                }
            }

            return null;
        }
    }
    public class Location {
        private double x;
        private double y;
        private double z;
        private float pitch;
        private float yaw;

        private Location(org.bukkit.Location location) {
            x = location.getX();
            y = location.getY();
            z = location.getZ();
            pitch = location.getPitch();
            yaw = location.getYaw();
        }

        private Location(double x, double y, double z, float pitch, float yaw) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.pitch = pitch;
            this.yaw = yaw;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getPitch() {
            return pitch;
        }

        public float getYaw() {
            return yaw;
        }
    }
}