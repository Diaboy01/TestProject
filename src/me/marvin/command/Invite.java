package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;


public class Invite implements CommandExecutor {

    //TODO /invite geht nicht?
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String playerName = player.getName();
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
            if (player.hasPermission("empire.leader")) {
                File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
                String teamName = config.getString("Team");
                if (teamName.matches("-")) {
                      commandSender.sendMessage("Error! Du benötigst ein eigenes Team!");
                      return true;
                }
                if (args.length != 1) {
                    commandSender.sendMessage("Error! Nutze: /invite SPIELERNAME");
                    return true;
                }
                if (args[0].matches(playerName)) {
                    commandSender.sendMessage("Error! Du kannst dich nicht selbst einladen!");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (target.hasPermission("Leader")) {
                    commandSender.sendMessage("Error! Du kannst keinen anderen Leader einladen!");
                    return true;
                }

                Bukkit.dispatchCommand(console,  "tellraw " + args[0] + " [\"\",{\"text\":\"Einladung für Team: " + teamName + "\"},{\"text\":\" \",\"color\":\"dark_green\"},{\"text\":\"-> \",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/accept " + teamName + "\"}},{\"text\":\"Beitreten\",\"underlined\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/accept " + teamName + "\"}},{\"text\":\" \",\"color\":\"dark_green\"},{\"text\":\"-> \",\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/decline " + teamName + "\"}},{\"text\":\"Ablehen\",\"underlined\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/decline " + teamName + "\"}}]");
                commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Team: " + teamName + " eingeladen!");
                Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission settemp accept." + teamName + " true 5m");
            }
        } else {
                commandSender.sendMessage("Error!");
                return true;
            }
        return false;
    }
}
