package org.girsbrain.warp.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.girsbrain.warp.Warp;

/**
 * @author jlogsdon
 */
public class Paginator {
    private static final int WARPS_PER_PAGE = 8;

    private List<Warp> warps = new ArrayList<Warp>();
    private int pages;

    public Paginator(List<Warp> warps, Player player, String forPlayer) {
        for (Warp warp : warps) {
            if (null != forPlayer && !warp.isOwner(forPlayer)) {
                continue;
            }

            if (warp.hasAccess(player)) {
                this.warps.add(warp);
            }
        }

        this.pages = (int) Math.ceil(this.warps.size() / (double) WARPS_PER_PAGE);
    }

    public Paginator(List<Warp> warps, Player player) {
        for (Warp warp : warps) {
            if (warp.hasAccess(player)) {
                this.warps.add(warp);
            }
        }

        this.pages = (int) Math.ceil(this.warps.size() / (double) WARPS_PER_PAGE);
    }

    public void display(Player player, int page) {
        int start = (page - 1) * WARPS_PER_PAGE;
        int end = Math.min(start + WARPS_PER_PAGE, warps.size());

        if (pages == 0) {
            player.sendMessage(ChatColor.RED + "No warps were found for listing!");
            return;
        }
        if (page < 1 || page > pages) {
            player.sendMessage(ChatColor.RED + "Invalid page number! " + pages + " total pages.");
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "------------- Page " + page + " / " + pages + " -------------");

        for (int i = start; i < end; i++) {
            Warp warp = warps.get(i);
            Location loc = warp.getLocation();

            String indicator = "-";
            if (warp.isPublic()) {
                indicator = "+";
            } else if (warp.isGlobal()) {
                indicator = "%";
            }

            String location = String.format(" %s@(%.0f, %.0f, %.0f)", ChatColor.AQUA, loc.getX(), loc.getY(), loc.getZ());
            String owner = String.format("%s%s by %s%s", ChatColor.WHITE, indicator, ChatColor.YELLOW, warp.getOwner());
            ChatColor color = ChatColor.RED;

            if (warp.isOwner((Player) player)) {
                color = ChatColor.AQUA;
            } else if (warp.isPublic()) {
                color = ChatColor.GREEN;
            } else if (warp.isGlobal()) {
                color = ChatColor.YELLOW;
            }

            player.sendMessage(color + warp.getName() + owner + location);
        }
    }
}