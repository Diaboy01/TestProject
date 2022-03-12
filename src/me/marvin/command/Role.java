package me.marvin.command;

import me.marvin.api.YAMLPlayers;
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

        if (player.hasPermission("empire.leader")) {
            File playersFile2 = new File("plugins/Players/", playerName + ".yml");
            YamlConfiguration config2 = YamlConfiguration.loadConfiguration(playersFile2);
            String team2 = config2.getString("Team");

            File playersFile1 = new File("plugins/Players/", args[0] + ".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(playersFile1);
            String team1 = config1.getString("Team");

            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
                return false;
            }
            if (!team1.equals(team2)) {
                commandSender.sendMessage("Error! Du musst im gleichen Team sein!");
                return false;
            }

            commandSender.sendMessage("Spieler: " + args[0] + " erhält Rolle: " + args[1]);
            YAMLPlayers.printYml(args[0],"Role", args[1]);
        }
        return true;
    }
}

