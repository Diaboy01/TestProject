package me.marvin.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Invite implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String teamName = "";
            String playerName = player.getName();
            if (player.hasPermission("empire.leader")) {
                teamName = player.getScoreboard().getPlayerTeam(player).getName();
                if (teamName.matches("-")) {
                    commandSender.sendMessage("Error! Du benötigst ein eigenes Team!");
                    return true;
                }
                if (args.length != 1) {
                    commandSender.sendMessage("Error! Nutze: /invite SPIELERNAME");
                    return true;
                }
                if (args[0].matches(playerName)) {
                    commandSender.sendMessage("Error! Du kannst dich nicht selbst einladen!");
                    return true;
                }
                //TODO Leader soll keine anderen Leader einladen können!

                Bukkit.dispatchCommand(console,  "tellraw " + args[0] + " [\"\",{\"text\":\"Einladung für Team: " + teamName + "\"},{\"text\":\" \",\"color\":\"dark_green\"},{\"text\":\"-> \",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/invite accept " + teamName + "\"}},{\"text\":\"Beitreten\",\"underlined\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/invite accept " + teamName + "\"}},{\"text\":\" \",\"color\":\"dark_green\"},{\"text\":\"-> \",\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/invite decline " + teamName + "\"}},{\"text\":\"Ablehen\",\"underlined\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/invite decline " + teamName + "\"}}]");
                commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Team: " + teamName + " eingeladen!");
                Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission settemp invite." + teamName + " true 5m");
            }

            if (!player.hasPermission("empire.leader")) {
                if (args.length != 2) {
                    commandSender.sendMessage("Error: Schreibe /invite accept TEAMNAME oder /invite decline TEAMNAME");
                    return true;
                }
                if (player.hasPermission("invite." + args[1])) {
                    if (args[0].equalsIgnoreCase("accept")) {
                        //TODO Spieler in Team Spieler Perm Group stecken
                        commandSender.sendMessage("Spieler: " + playerName + " wurde zum Team: " + args[1] + " hinzugefügt!");
                        Bukkit.dispatchCommand(console, "scoreboard teams join " + args[1] + " " + playerName);
                    }
                    if (args[0].equalsIgnoreCase("decline")) {
                        commandSender.sendMessage("Spieler: " + playerName + " wurde zum Team: " + args[1] + " nicht hinzugefügt!");
                    }
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
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("empire.leader")) {
            if (args.length == 1) {
                return Lists.newArrayList("accept", "decline");
            }
        }
        return Collections.emptyList();
    }
}
