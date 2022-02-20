package me.marvin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerMotdListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMaxPlayers(50);
        event.setMotd("§cCooler Lokaler Server xD\n§fNice nice nice");
    }
}
