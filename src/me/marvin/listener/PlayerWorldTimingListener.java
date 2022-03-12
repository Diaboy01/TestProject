package me.marvin.listener;

import me.marvin.Main;
import me.marvin.api.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;


public class PlayerWorldTimingListener implements Listener {

    //TODO Datei Welten.yml vorher erstellen in plugins/Novorex/General/ ?

    static File generalFile1 = new File("plugins/Novorex/General/","Welten.yml");
    static YamlConfiguration config1 = YamlConfiguration.loadConfiguration(generalFile1);
    //TODO FARMWORLD sollte auch für andere Dimesnionen gelten! Also !Bauwelt
    private static final String FALLBACK_WORLD = "world", FARMWORLD = config1.getString("Farmwelt");

    static {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {

            String date = Utils.getDate();

            @Override
            public void run() {
                String newDate = Utils.getDate();
                if(!date.equals(newDate)) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
                        playerWorldTimings.stopCounting();
                        playerWorldTimings.appendData(0L);
                        playerWorldTimings.startCounting();
                    }

                    date = newDate;
                }

                for(Player player : Bukkit.getOnlinePlayers()) {
                    PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());

                    if(playerWorldTimings.isCounting()) {
                        long time = PlayerWorldTimings.FARMWORLD_TIME_LIMIT - playerWorldTimings.getTimeSpend();

                        if (time < 60000) {
                            player.sendMessage("Achtung! Du hast noch Spielzeit 1 Minute in der Farmwelt, setze ein Home um deine Postion in der Farmwelt zu speichern! Mit: /sethome");
                        }

                        int s = 0, m = 0;

                        while(time >= 1000) {
                            time -= 1000;
                            s++;
                        }

                        while(s >= 60) {
                            s -= 60;
                            m++;
                        }

                        player.sendActionBar("Verbleibende Spielzeit: " + m + ":" + s );

                        if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.FARMWORLD_TIME_LIMIT)) {
                            player.teleport(Bukkit.getWorld(FALLBACK_WORLD).getSpawnLocation());
                            //TODO zum Team Warp Teleportieren
                            playerWorldTimings.stopCounting();
                        }

                    }
                }
            }
        }, 20, 20);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());

        String date = Utils.getDate();
        File generalFile = new File("plugins/General/TimePlayed/", date + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);
        Player joinPlayer = event.getPlayer();
        String playerName = joinPlayer.getName();
        String timePlayed = config.getString(playerName);

        playerWorldTimings.appendData(Long.parseLong(timePlayed));

        World world = player.getWorld();
        if(world.getName().equalsIgnoreCase(FARMWORLD)) {
            if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.FARMWORLD_TIME_LIMIT)) {
                player.teleport(Bukkit.getWorld(FALLBACK_WORLD).getSpawnLocation());
                //TODO zum Team Warp Teleportieren
            } else {
                playerWorldTimings.startCounting();
                //TODO Zum Farmwelt Home Spieler teleportieren (Abfrage/autmomtisch)
            }
        }
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World worldTo = event.getPlayer().getWorld();
        World worldFrom = event.getFrom();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(!worldTo.getName().equalsIgnoreCase(FARMWORLD)) {
            if(playerWorldTimings.isCounting()) {
                playerWorldTimings.stopCounting();
            }
            return;
        }

        if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.FARMWORLD_TIME_LIMIT)) {
            player.teleport(worldFrom.getSpawnLocation());
        } else {
            playerWorldTimings.startCounting();
            //TODO Zum Farmwelt Home Spieler teleportieren (Abfrage/autmomtisch)
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());

        String timePlayed = String.valueOf(playerWorldTimings.getTimeSpend());
        String date = Utils.getDate();

        YAMLTimePlayed.printYml(date, playerName, timePlayed);

        PlayerWorldTimings.dispose(player.getUniqueId());
    }
}