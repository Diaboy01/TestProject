package me.marvin.command;

import me.marvin.api.YAMLGeneral;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class Farmwelt implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String playerName = player.getName();
            if (player.hasPermission("empire.admin")) {
                if (args.length == 1) {
                    YAMLGeneral.printYml("Welten","Farmwelt",args[0]);
                    commandSender.sendMessage("Die Farmwelt heißt nun: " + args[0]);
                }
            }

            if (args.length == 0) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "rtp " + playerName + "");
                    commandSender.sendMessage("Du wurdest in die Farmwelt teleportiert!");
                    //TODO Zum Farmwelt Home Spieler teleportieren (Abfrage/autmomtisch)
            }
        } else {
            commandSender.sendMessage("Befehl ist nur für Spieler Ingame!");
        }
        return false;
    }
}