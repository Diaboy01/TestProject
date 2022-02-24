package me.marvin.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CreateTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("empire.admin")) {
                if (args.length == 0) {
                    commandSender.sendMessage("Error! Nutze: /create TEAMNAME");
                }
                if (args.length == 1) {
                    //TODO lp Create Team Spieler Perm Group + Team Leader Perm Group
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                    commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!");
                }
            }
        } else {
            if (args.length == 0) {
                commandSender.sendMessage("Error! Nutze: /create TEAMNAME");
            }
            if (args.length == 1) {
                //TODO lp Create Team Spieler Perm Group + Team Leader Perm Group
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!");
            }
        }
        return false;
    }
}