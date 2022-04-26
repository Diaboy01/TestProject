package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import static me.marvin.api.YAMLPlayers.printYml;


public class Accept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String playerName = player.getName();
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
            if (player.hasPermission("empire.leader")) {
                commandSender.sendMessage("Error! Du bist ein Leader");
                return true;

            } else {
                if (args.length != 1) {
                commandSender.sendMessage("Error! Nutze: /accept TEAMNAME");
                return true;
                                      }
                if (player.hasPermission("accept." + args[0])) {
                    Bukkit.dispatchCommand(console, "scoreboard teams join " + args[0] + " " + playerName);
                    Bukkit.dispatchCommand(console, "lp user " + playerName + " parent set s" + args[0]);
                    printYml(playerName, "Team", args[0]);
                    Bukkit.dispatchCommand(console, "kick " + playerName + " Team " + args[0] + " hinzugefügt! Please rejoin!");
                    commandSender.sendMessage("Spieler: " + playerName + " wurde zum Team: " + args[0] + " hinzugefügt!");
                } else {
                    commandSender.sendMessage("Error! Keine aktuelle Einladung vorhanden!");
                    return true;
                }
            }
        } else {
            commandSender.sendMessage("Error!");
            return true;
        }
        return false;
    }
}
