package me.marvin.command;

import org.bukkit.command.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Role implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(args.length == 2)) {
            commandSender.sendMessage("Error! Nutze /role SPIELERNAME ROLLE");
            return false;
        }

        commandSender.sendMessage("Spieler: " + args[0] + "erhält Rolle: " + args[1]);
        try {
            printLog(args[0], args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void printLog(String playerName, String suffix) throws IOException {
        File directory = new File("plugins/Players/");
        //TODO Falls "Players" in /plugins/ noch nicht vorhanden -> Error!
        File playersFile = new File("plugins/Players/", playerName + ".yml");
        FileWriter fileWriter = new FileWriter(playersFile);
            directory.mkdirs();
            if (!playersFile.exists()) {
                try {
                    playersFile.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            try {
                fileWriter.write("" + suffix);
                fileWriter.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
    }
}

