package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("empire.leader")) {
                if (args.length == 0) {
                    commandSender.sendMessage("Error: Teamname angeben!");
                }
                if (args.length == 1) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                    commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!");
                    String playerName = player.getName();
                    Bukkit.dispatchCommand(console, "scoreboard teams join " + args[0] + " " + playerName);
                }
            }
        } else {
            commandSender.sendMessage("Error!");
        }
        return false;
    }
}