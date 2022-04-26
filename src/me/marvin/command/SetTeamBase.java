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
        if (player.hasPermission("*") || player.isOp()) {
            commandSender.sendMessage("Achtung! Du bist * OP");
        }

        if (player.hasPermission("empire.leader")) {
            if (!(args.length == 0)) {
            commandSender.sendMessage("Error! Nutze /teambase");
            return false;
            }
            player.sendMessage("Team Warp wird gesetzt!");

            File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

            String team = config.getString("Team");

            File generalFile = new File("plugins/Novorex/Logs/" + date + "/TeamBase/" + team + ".yml");
            YamlConfiguration config2 = YamlConfiguration.loadConfiguration(generalFile);

            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission settemp essentials.setwarp true 5s");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.chat("/setwarp " + team);
            player.sendMessage("Momentan muss ein Teamgebiet noch mit Schildern und Zäunen deutlich gekennzeichnet werden!");

            //TODO Team Gebiet mit Radius

        }
        return true;
    }
}