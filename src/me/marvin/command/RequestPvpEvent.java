package me.marvin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RequestPvpEvent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (player.hasPermission("empire.admin")) {
                Bukkit.dispatchCommand(console,  "tellraw @a [\"\",{\"text\":\"Ist dein Inventar komplett leer? Möchtest du heute bei dem PvP Event teilnehmen?\"},{\"text\":\" -> /\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/teilnehmen\"}},{\"text\":\"TEILNEHMEN\",\"underlined\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/teilnehmen\"}}]\n");
                commandSender.sendMessage("Einladung versendet!");
            }
        } else {
            commandSender.sendMessage("Error!");
            return true;
        }
        return false;
    }
}
