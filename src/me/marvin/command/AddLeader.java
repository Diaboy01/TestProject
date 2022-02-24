package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;


public class AddLeader implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("empire.admin")) {
                if (!(args.length == 2)) {
                    commandSender.sendMessage("Error! Nutze: /add SPIELERNAME TEAMNAME!");
                }
                if (args.length == 2) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    //TODO Leader in Perm Group Leader stecken
                    Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission set empire.leader");
                    commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Leader vom Team: " + args[1] + " ernannt!");
                    Bukkit.dispatchCommand(console, "scoreboard teams join " + args[1] + " " + args[0]);
                }
            }
        } else {
            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze: /add SPIELERNAME TEAMNAME!");
            }
            if (args.length == 2) {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                //TODO Leader in Perm Group Leader stecken
                Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission set empire.leader");
                commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Leader vom Team: " + args[1] + " ernannt!");
                Bukkit.dispatchCommand(console, "scoreboard teams join " + args[1] + " " + args[0]);
            }
        }
        return false;
    }
}
