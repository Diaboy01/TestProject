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

            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
                return false;
            }

            File playersFile2 = new File("plugins/Novorex/Players/", playerName + ".yml");
            YamlConfiguration config2 = YamlConfiguration.loadConfiguration(playersFile2);
            String team2 = config2.getString("Team");

            //Todo Nutze:             Player target = Bukkit.getPlayer(args[0]);
            //            target.hasPermission("test");

            File playersFile1 = new File("plugins/Novorex/Players/", args[0] + ".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(playersFile1);
            String team1 = config1.getString("Team");

            //TODO Leader Permission abfragen!
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

