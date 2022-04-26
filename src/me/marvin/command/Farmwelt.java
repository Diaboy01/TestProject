package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.concurrent.TimeUnit;


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

            World world = player.getWorld();

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
                player.teleport(new Location(farm, 0, 150, 0));
                Bukkit.dispatchCommand(console, "god " + playerName + " enable");
                Bukkit.dispatchCommand(console, "spreadplayers 0 0 200 500 true " + playerName);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "god " + playerName + " disable");
            }
        }
    } else {
            commandSender.sendMessage("Befehl ist nur für Spieler Ingame!");
            }
            return false;
    }
}