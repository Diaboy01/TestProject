package me.marvin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerTeleportEvent;
        import org.bukkit.event.EventHandler;
        import org.bukkit.entity.Player;
        import org.bukkit.World;
        import org.bukkit.event.player.PlayerPortalEvent;
        import org.bukkit.event.Listener;

import java.io.File;

public class PortalListener implements Listener {


    @EventHandler
    public void PlayerPortal(final PlayerPortalEvent event) {
        final Player player = event.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        World world = event.getTo().getWorld();
        String w = world.getName();

        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("naturalRegeneration", "false");
        World Bauwelt = Bukkit.getWorld("world");
        Bauwelt.setDifficulty(Difficulty.PEACEFUL);

        File generalFile = new File("plugins/Novorex/General/", "Farmwelt.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

        String farmworld = config.getString("Farmwelt");

        if (!(player.hasPermission("world.all")) && !(w.equals("world") || w.equals(farmworld))) {
            event.setCancelled(true);
            player.sendMessage("Welt/Dimension ist noch gesperrt!");
        } else if (w.equals("world")) {
            player.sendMessage("§cAchtung! PVP ist in dieser Welt verboten!");
        } else {
            player.sendMessage("§cAchtung! PVP ist in dieser Welt erlaubt!");
        }
    }


    @EventHandler
    public void PlayerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        World world = event.getTo().getWorld();
        String w = world.getName();
        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("naturalRegeneration", "false");
        World Bauwelt = Bukkit.getWorld("world");
        Bauwelt.setDifficulty(Difficulty.PEACEFUL);

        File generalFile = new File("plugins/Novorex/General/", "Farmwelt.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

        String farmworld = config.getString("Farmwelt");

        if (!(player.hasPermission("world.all")) && !(w.equals("world") || w.equals(farmworld))) {
            event.setCancelled(true);
            player.sendMessage("Welt/Dimension ist noch gesperrt!");
            //TODO Abfrage ob World event.getTo().getWorld(); und aktuelle Welt die gleichen sind/ob nicht
        } else if (w.equals("world")) {
            player.sendMessage("§cAchtung! PVP ist in dieser Welt verboten!");
        } else {
            player.sendMessage("§cAchtung! PVP ist in dieser Welt erlaubt!");
        }
    }
}
