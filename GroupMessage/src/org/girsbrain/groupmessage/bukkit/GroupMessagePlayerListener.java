/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.girsbrain.groupmessage.bukkit;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 *
 * @author jlogsdon
 */
public class GroupMessagePlayerListener extends PlayerListener {
    private GroupMessagePlugin plugin;

    public GroupMessagePlayerListener(GroupMessagePlugin instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        if (plugin.hasToggle(event.getPlayer())) {
            event.setCancelled(true);
            plugin.sendToGroups(event.getPlayer(), event.getMessage());
        }
    }
}
