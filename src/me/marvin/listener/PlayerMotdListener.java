package me.marvin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerMotdListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMaxPlayers(100);
        event.setMotd("§fMinecraft §bHEXXIT §fII \n§6by Novorex.net / §6§oSponsor: Nitrado.net");
    }
}
