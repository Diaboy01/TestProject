package me.marvin.listener;

import me.marvin.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerDeathListener implements Listener {
    private FileWriter fileWriter;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");
    String date = Utils.getDate();

    public PlayerDeathListener() {
        File directory = new File("plugins/Novorex/Logs/" + date + "/");
        directory.mkdirs();

        File deathLogFile = new File("plugins/Novorex/Logs/" + date + "/", "death.log");
        if(!deathLogFile.exists()) {
            try {
                deathLogFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            fileWriter = new FileWriter(deathLogFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Schreibt die Todesnachricht in die death.log-Datei.
     *
     * @param player Spieler der gestorben ist
     * @param deathLocation die Position an der der Spieler gestorben ist
     * @param message Nachricht vom Server
     */
    private void printDeath(Player player, Location deathLocation, String message) {
        try {
            fileWriter.write(simpleDateFormat.format(new Date()) + " " + player.getName() + " wurde getötet [world=" + deathLocation.getWorld().getName() + ",x=" + deathLocation.getBlockX() + ",y=" + deathLocation.getBlockY() + ",z=" + deathLocation.getBlockZ() + "] (" + message + ")\n");
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        printDeath(event.getEntity(), event.getEntity().getLocation(), event.getDeathMessage());
        Player p = event.getEntity();
        Bukkit.dispatchCommand(p,"/near"); //Klappt das?

    }
}
