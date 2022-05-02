package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class PvpStart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (args.length != 0) {
                if (player.hasPermission("empire.admin")) {
                    //TODO Tellraw geht net
                    Bukkit.dispatchCommand(console, "tellraw @a [\"\",{\"text\":\"Ist dein Inventar komplett leer? Möchtest du jetzt bei dem PvP Event teilnehmen?\"},{\"text\":\" -> /\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/teilnehmen\"}},{\"text\":\"TEILNEHMEN\",\"underlined\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/teilnehmen\"}}]\n");
                    commandSender.sendMessage("Einladungen versendet!");
                }
            }
            // /minecraft:effect @p clear
            // /rg flag __global__ invincible deny
        } else {
            commandSender.sendMessage("Error!");
            return true;
        }
        return false;
    }
}
