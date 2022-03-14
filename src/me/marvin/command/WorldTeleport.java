package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldTeleport implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("empire.admin")) {
                if (!(args.length == 1)) {
                    commandSender.sendMessage("Error! Nutze: /wtp WELTNAME");
                }
                if (args.length == 1) {
                    World world = Bukkit.getWorld(args[0]);
                    if (world == null) {
                        world = WorldCreator.name(args[0]).createWorld();
                        Bukkit.createWorld(new WorldCreator(args[0]));
                    };
                    player.teleport(new Location(world, 0, 100, 0));
                    commandSender.sendMessage("Welt: " + args[0] + "");
                }
            }
        } else {
            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze: /wtp SPIELERNAME WELTNAME");
            }
            if (args.length == 2) {
                //TODO /wtp über Console
                commandSender.sendMessage("Coming soon!");
            }
        }
        return false;
    }
}