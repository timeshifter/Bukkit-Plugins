package org.girsbrain.warp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author jlogsdon
 */
public class Warp {
    private int index;
    private String name;
    private String owner;
    private Location location;
    private Visibility visibility = Visibility.PRIVATE;
    private ArrayList<String> invited = new ArrayList<String>();

    public Warp(Server server, ResultSet set) throws SQLException, Exception {
        index = set.getInt("id");
        name = set.getString("name");
        owner = set.getString("owner");
        visibility = Visibility.fromString(set.getString("visibility"));

        setLocation(server, set);
        invited.addAll(Arrays.asList(set.getString("permissions").split(",")));
    }

    public Warp(Player player, String name) {
        index = 0;
        this.name = name;
        owner = player.getName();
        location = player.getLocation();
    }

    public enum Visibility {
        PRIVATE,
        PUBLIC,
        GLOBAL;

        public static Visibility fromString(String string) {
            for (Visibility v : Visibility.values()) {
                if (v.toString().startsWith(string.toUpperCase())) {
                    return v;
                }
            }

            return PRIVATE;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        this.owner = player.getName();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private void setLocation(Server server, ResultSet set) throws SQLException, Exception {
        World world = server.getWorld(set.getString("world"));
        if (world == null) {
            throw new Exception("Invalid world specified: " + set.getString("world"));
        }

        double x = set.getDouble("x");
        double y = set.getDouble("y");
        double z = set.getDouble("z");
        int pitch = set.getInt("pitch");
        int yaw = set.getInt("yaw");

        location = new Location(world, x, y, z, pitch, yaw);
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public ArrayList<String> getInvites() {
        return invited;
    }

    public void addInvite(Player player) {
        addInvite(player.getName());
    }

    public void addInvite(String player) {
        if (!invited.contains(player)) {
            invited.add(player);
        }
    }

    public void removeInvite(Player player) {
        removeInvite(player.getName());
    }

    public void removeInvite(String player) {
        if (invited.contains(player)) {
            invited.remove(player);
        }
    }

    public boolean isInvited(Player player) {
        return invited.contains(player.getName());
    }

    public boolean isInvited(String player) {
        return invited.contains(player);
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getName());
    }

    public boolean isOwner(String player) {
        return player.equals(owner);
    }

    public boolean isPublic() {
        return visibility == Visibility.PUBLIC;
    }

    public boolean isGlobal() {
        return visibility == Visibility.GLOBAL;
    }

    public boolean hasAccess(Player player) {
        // Owners always have access
        if (player.getName().equals(owner)) {
            return true;
        }

        // Public/global warp means everyone has access
        if (!visibility.equals(Visibility.PRIVATE)) {
            return true;
        }

        // Invited players have access, of course
        if (invited.contains(player.getName())) {
            return true;
        }

        return false;
    }

    public boolean matches(Player player, String name) {
        if (!isOwner(player)) {
            return false;
        }

        return matches(name);
    }

    public boolean matches(String player, String name) {
        if (!isOwner(player)) {
            return false;
        }

        return matches(name);
    }

    public boolean matches(String name) {
        return this.name.toLowerCase().startsWith(name.toLowerCase());
    }
}
