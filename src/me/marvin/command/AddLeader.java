package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.marvin.api.YAMLPlayers.printYml;


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
                    Bukkit.dispatchCommand(console, "scoreboard teams join " + args[1] + " " + args[0]);
                    printYml(args[0], "Team", args[1]);
                    printYml(args[0], "Leader", "true");
                    Bukkit.dispatchCommand(console, "kick " + args[0] + " Please rejoin!");
                    commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Leader vom Team: " + args[1] + " ernannt!");

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
                Bukkit.dispatchCommand(console, "scoreboard teams join " + args[1] + " " + args[0]);
                printYml(args[0], "Team", args[1]);
                printYml(args[0], "Leader", "true");
                Bukkit.dispatchCommand(console, "kick " + args[0] + " Please rejoin!");
                commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Leader vom Team: " + args[1] + " ernannt!");

            }
        }
        return false;
    }
}
