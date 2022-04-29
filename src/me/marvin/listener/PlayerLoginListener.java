package me.marvin.listener;

import me.marvin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginListener implements Listener {
    //TODO Listener umbennen

    @EventHandler
    public void playerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World bauwelt = Bukkit.getWorld("world");


        World world = player.getWorld();

        String worldName = world.getName();

        if (!worldName.equals("world")) {
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(new Location(bauwelt, -4, 70, -6)), 20L);
            player.sendMessage("§4§lAchtung du hast dich falsch ausgeloggt! Bitte verlasse den Server nur wenn du in der Bauwelt bist!");

        }
    }

}
