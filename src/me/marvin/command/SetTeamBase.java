package me.marvin.command;

import me.marvin.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SetTeamBase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = (Player) commandSender;
        String playerName = player.getName();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String date = Utils.getDate();

        if (player.hasPermission("empire.leader")) {
            if (!(args.length == 1)) {
            commandSender.sendMessage("Error! Nutze /teambase RADIUS");
            return false;
            }
            player.sendMessage("Bitte warte kurz...");

            File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

            String team = config.getString("Team");

            File generalFile = new File("plugins/Novorex/Logs/" + date + "/TeamBase/" + team + ".yml");
            YamlConfiguration config2 = YamlConfiguration.loadConfiguration(generalFile);

            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission set essentials.setwarp");
            Bukkit.dispatchCommand(player,"setwarp " + team);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission unset essentials.setwarp");

            Bukkit.dispatchCommand(console, "say " + team + "");

            //TODO Team Gebiet mit Radius

        }
        return true;
    }
}