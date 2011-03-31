package org.girsbrain.bukkit.warp.datasource;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

/**
 * @author jlogsdon
 */
public class Warp {
    private int index;
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

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getInvited() {
        return invited;
    }

    public Location getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

    public String getWorld() {
        return world;
    }

    public boolean hasAccess(Player player) {
        // No cross-world pollution
        if (!world.equals(player.getWorld().getName())) {
            return false;
        }

        // Owners always have access
        if (isOwner(player)) {
            return true;
        }

        // Public warps are available to all
        if (!type.equals(Type.PRIVATE)) {
            return true;
        }

        // And finally check if the player is invited
        if (isInvited(player)) {
            return true;
        }

        return false;
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getName());
    }

    public boolean isOwner(String player) {
        return owner.equals(player);
    }

    public boolean isInvited(Player player) {
        return isInvited(player.getName());
    }

    public boolean isInvited(String player) {
        return invited.contains(player);
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