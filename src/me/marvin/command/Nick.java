package me.marvin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Nick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = (Player) commandSender;
        //TODO Nick nur änderbar vom Leader des Teams des Spielers
        if (player.hasPermission("empire.leader")) {
            if (!(args.length == 2)) {
                commandSender.sendMessage("Error! Nutze /nick SPIELERNAME NICKNAME");
                return false;
            }
            commandSender.sendMessage("Spieler: " + args[0] + " erhält Nick: " + args[1]);
            try {
                printNick(args[0], args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public void printNick(String playerName, String nick) throws IOException {
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

        config.set("Nick",nick);
            try {
                config.save(playersFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}