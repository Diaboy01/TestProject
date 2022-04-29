package me.marvin.listener;

import me.marvin.Main;
import me.marvin.api.PlayerWorldTimings;
import me.marvin.api.Utils;
import me.marvin.api.YAMLTimePlayed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;


public class PlayerWorldTimingListener implements Listener {


    //static File generalFile1 = new File("plugins/Novorex/General/","Bauwelt.yml");
    //static YamlConfiguration config1 = YamlConfiguration.loadConfiguration(generalFile1);
    private static final String Bauwelt = "world";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss");

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
                        long time = PlayerWorldTimings.TIME_LIMIT - playerWorldTimings.getTimeSpend();

                        if (time == 60000 || time == 60001) {
                            player.sendMessage("Achtung! Du hast noch Spielzeit 1 Minute in der Farmwelt, setze ein Home um deine Postion in der Farmwelt zu speichern! Mit: /sethome");
                        }

                        player.sendActionBar("Verbleibende Spielzeit: §a" + FORMAT.format(time));

                        if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                            player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());
                            playerWorldTimings.stopCounting();
                        }
                    }
                }
            }
        }, 20, 20);
    }

    /* Spieler ist bereits in Bauwelt
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());

        World world = player.getWorld();
        if(!(world.getName().equalsIgnoreCase(Bauwelt))) {
            if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());
            } else {
                if(!player.hasPermission("empire.admin")) {
                    playerWorldTimings.startCounting();
                }
            }
        }
    }*/

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        /* Legacy
        World worldTo = event.getPlayer().getWorld();
        World worldFrom = event.getFrom();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(worldTo.getName().equalsIgnoreCase(Bauwelt)) {
            if(playerWorldTimings.isCounting()) {
                playerWorldTimings.stopCounting();
            }
            return;
        }

        if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
            player.teleport(worldFrom.getSpawnLocation());
        } else {
            if(!player.hasPermission("empire.admin")) {
                playerWorldTimings.startCounting();
            }

        }
         */

        World worldFrom = event.getFrom(), worldTo = event.getPlayer().getWorld();
        if(worldFrom.equals(worldTo)) return;

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(!player.hasPermission("empire.admin")) {
            if (worldFrom.getName().equals(Bauwelt)) {
                if (!playerWorldTimings.isCounting()) {
                    playerWorldTimings.startCounting();
                }
            } else {
                if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                    player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());
                } else {
                    if (playerWorldTimings.isCounting()) {
                        playerWorldTimings.stopCounting();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String date = Utils.getDate();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(playerWorldTimings.isCounting()) {playerWorldTimings.stopCounting();}
        YAMLTimePlayed.printTime(date, player.getUniqueId().toString(), playerWorldTimings.getTimeSpend());

        PlayerWorldTimings.dispose(player.getUniqueId());

        World bauwelt = Bukkit.getWorld(Bauwelt);
        World world = player.getWorld();
        String worldName = world.getName();

        if (!worldName.equals(Bauwelt)) {
            player.teleport(new Location(bauwelt, -4, 70, -6));
        }
    }
}