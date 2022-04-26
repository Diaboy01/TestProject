package me.marvin.command;

import me.marvin.api.YAMLPlayers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Role implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = (Player) commandSender;
        String playerName = player.getName();
        if (player.hasPermission("*") || player.isOp()) {
            commandSender.sendMessage("Achtung! Du bist * OP");
        }
        if (player.hasPermission("empire.leader")) {

            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (!(target.hasPermission("essentials.warps." + args[0])) && (player.hasPermission("essentials.warps." + args[0])))
            {
                commandSender.sendMessage("Error! Du musst im gleichen Team sein!");
                return false;
            }

            commandSender.sendMessage("Spieler: " + args[0] + " erhält Rolle: " + args[1]);
            YAMLPlayers.printYml(args[0],"Role", args[1]);
        }
        return true;
    }
}

