package me.marvin.command;

import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Role implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(args.length == 2)) {
            commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
            return false;
        }
        //TODO Perm Command only for Leader
        commandSender.sendMessage("Spieler: " + args[0] + " erhält Rolle: " + args[1]);
        //TODO Nur Rolle änderbar vom Leader des Teams des Spielers
        try {
            printLog(args[0], args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void printLog(String playerName, String suffix) throws IOException {
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
        config.save(playersFile);
    }
}

