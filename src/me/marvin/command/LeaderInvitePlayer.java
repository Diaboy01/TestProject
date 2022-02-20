package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LeaderInvitePlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("empire.leader")) {
                if (args.length == 0) {
                    commandSender.sendMessage("Error: Spielernamen angeben!");
                    return true;
                }
                if (args.length == 1) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "test " + args[0] + " ");
                    commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Team eingeladen!");
                }
            }
        } else {
                commandSender.sendMessage("Error!");
                return true;
            }
        return false;
    }
}
