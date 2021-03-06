package me.marvin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;

public class PortalListener implements Listener {


    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        World worldGetTo = event.getPlayer().getWorld();
        String worldGetToName = worldGetTo.getName();
        worldGetTo.setDifficulty(Difficulty.HARD); //TODO Ausnahme: PvP World
        worldGetTo.setGameRuleValue("naturalRegeneration", "false");
        World Bauwelt = Bukkit.getWorld("world");
        Bauwelt.setDifficulty(Difficulty.PEACEFUL);

        //TODO PvP Welt get aus config
        String pvpworldName = "pvpworld1";

        File generalFile = new File("plugins/Novorex/General/", "Farmwelt.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

        String farmworld = config.getString("Farmwelt");

        if(!event.getPlayer().getWorld().equals(event.getFrom())) {
            if (!(player.hasPermission("world.all")) && !(worldGetToName.equals("world") || worldGetToName.equals(farmworld) || worldGetToName.equals("DIM-1") || worldGetToName.equals(pvpworldName))) {
                player.sendMessage("Welt/Dimension ist noch gesperrt!");
            } else if (worldGetToName.equals("world")) {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt verboten!");
            } else {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt erlaubt!");
            }
        }
    }

    @EventHandler
    public void playerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        World worldGetTo = event.getTo().getWorld();
        String worldGetToName = worldGetTo.getName();
        worldGetTo.setDifficulty(Difficulty.HARD);
        worldGetTo.setGameRuleValue("naturalRegeneration", "false");
        World Bauwelt = Bukkit.getWorld("world");
        Bauwelt.setDifficulty(Difficulty.PEACEFUL);

        //TODO PvP Welt get aus config
        String pvpworldName = "pvpworld1";

        File generalFile = new File("plugins/Novorex/General/", "Farmwelt.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

        String farmworld = config.getString("Farmwelt");

        if(!event.getTo().getWorld().equals(event.getFrom().getWorld())) {
            if (!(player.hasPermission("world.all")) && !(worldGetToName.equals("world") || worldGetToName.equals(farmworld) || worldGetToName.equals("DIM-1") || worldGetToName.equals(pvpworldName))) {
                event.setCancelled(true);
                player.sendMessage("Welt/Dimension ist noch gesperrt!");
            } else if (worldGetToName.equals("world")) {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt verboten!");
            } else {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt erlaubt!");
            }
        }
    }
}
