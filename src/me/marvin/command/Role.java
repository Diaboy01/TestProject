package me.marvin.command;

import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Role implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = (Player) commandSender;
        //TODO Nur Rolle änderbar vom Leader des Teams des Spielers
        if (player.hasPermission("empire.leader")) {
            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
                return false;
            }
            commandSender.sendMessage("Spieler: " + args[0] + " erhält Rolle: " + args[1]);
            try {
                printSuffix(args[0], args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public void printSuffix(String playerName, String suffix) throws IOException {
        //TODO Falls /plugins/Players/... noch nicht vorhanden -> Error!
        File playersFile = new File("plugins/Players/", playerName + ".yml");
        File directory = new File("plugins/Players/");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
            directory.mkdirs();
            if (!playersFile.exists()) {
                try {
                    playersFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        config.set("Suffix", suffix);
        try {
            config.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

