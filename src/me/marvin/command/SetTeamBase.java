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

            int r = Integer.parseInt(args[0]);

            int X = (int) player.getLocation().getX();
            int Y = (int) player.getLocation().getY();
            int Z = (int) player.getLocation().getZ();

            int x1 = X + r;
            int x2 = X - r;
            int y1 = Y + r;
            int y2 = Y - r;
            int z1 = Z + r;
            int z2 = Z - r;

            //pos1 0,100,0
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission set worldedit.selection.pos");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.dispatchCommand(player,"/pos1 " + x1 + "," + y1 + "," + z1);
            Bukkit.dispatchCommand(player,"/pos2 " + x2 + "," + y2 + "," + z2);
            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission set worldguard.region.define");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.dispatchCommand(player,"rg define " + team);
            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission unset worldedit.selection.pos");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bukkit.dispatchCommand(console, "lp user " + playerName + " permission unset worldguard.region.define");
            Bukkit.dispatchCommand(console, "rg flag " + team + " -w world console-command-on-entry /say " + team); //TODO Bauwelt = world?
            player.sendMessage("Fertig!");
        }
        return true;
    }
}