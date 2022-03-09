package me.marvin.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAMLconfig {
    public static void printYml(String playerName, String name, String value) {
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
        config.set(name,value);
        try {
            config.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
