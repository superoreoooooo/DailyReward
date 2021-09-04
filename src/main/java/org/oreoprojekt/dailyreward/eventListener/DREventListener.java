package org.oreoprojekt.dailyreward.eventListener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.oreoprojekt.dailyreward.DailyRewardMain;

import java.io.File;

public class DREventListener implements Listener {
    private DailyRewardMain plugin;

    public DREventListener(DailyRewardMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        CheckPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    public void CheckPlayer(Player player) {
        int count = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count")) {
            count = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count");
        }
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count", (count + 1));
        plugin.data.saveConfig();
        plugin.check(player);
    }
}
