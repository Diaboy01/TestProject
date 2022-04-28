package me.marvin.command;

import me.marvin.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;


public class Farmwelt implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("empire.farmworld")) {
            Player player = (Player) commandSender;
            String playerName = player.getName();
            File generalFile = new File("plugins/Novorex/General/", "Farmwelt.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

            String farmworld = config.getString("Farmwelt");

            World farm = Bukkit.getWorld(farmworld);

            World world = player.getLocation().getWorld();

            String w = world.getName();

            if (w.equals("farmworld")) {
                commandSender.sendMessage("Du bist bereits in der Farmwelt!");
                return false;
            }
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
            if (!(args.length == 0)) {
                commandSender.sendMessage("Schreibe /farmwelt");
            }
            if (args.length == 0) {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                commandSender.sendMessage("§aZufallsteleport in die Farmwelt...!");
                if (farm == null) {
                    farm = WorldCreator.name(farmworld).createWorld();
                    Bukkit.createWorld(new WorldCreator(farmworld));
                };

                Random random = new Random();

                int randomX = random.nextInt(500 + 20) + 20;
                int randomZ = random.nextInt(500 + 20) + 20;

                int randomChunkX = randomX / 16;
                int randomChunkZ = randomZ / 16;
                
                ((CraftWorld) farm).getHandle().getChunkProviderServer().getOrLoadChunkAt(randomChunkX, randomChunkZ);
                World finalFarm = farm;
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(finalFarm.getHighestBlockAt(randomX, randomZ).getLocation().add(0, 1, 0)), 20L);
                player.setInvulnerable(true);
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setInvulnerable(false), 20L * 15);

               // int randomX = random.nextInt(500 + 20) + 20;
               // int randomZ = random.nextInt(500 + 20) + 20;

               // int randomChunkX = randomX / 16;

               // int randomChunkZ = randomZ / 16;

                /*
                Chunk chunk = farm.getChunkAt(0,0);

                chunk.load();

                Bukkit.dispatchCommand(console, "god " + playerName + " enable");


                player.teleport(new Location(farm, 0, 255, 0));
                Bukkit.dispatchCommand(console, "spreadplayers 0 0 10 25 true " + playerName);

                try {
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bukkit.dispatchCommand(console, "god " + playerName + " disable");
                */
            }
        }
    } else {
            commandSender.sendMessage("Befehl ist nur für Spieler Ingame!");
            }
            return false;
    }
}