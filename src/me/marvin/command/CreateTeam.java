package me.marvin.command;


import me.marvin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;



public class CreateTeam implements CommandExecutor {

    //TODO geht korrekt?
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
            if (player.hasPermission("empire.admin")) {
                if (args.length == 0) {
                    commandSender.sendMessage("Error! Nutze: /create TEAMNAME");
                }
                if (args.length == 1) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                    Bukkit.dispatchCommand(console, "scoreboard teams option " + args[0] + " nametagVisibility hideForOtherTeams");

                    Bukkit.dispatchCommand(console, "lp creategroup s" + args[0]);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp creategroup l" + args[0]), 20L);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group s" + args[0] + " parent add spieler"), 20L * 3);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group s" + args[0] + " permission set essentials.warps." + args[0]), 20L * 4);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group l" + args[0] + " parent add leader"), 20L * 5);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group l" + args[0] + " permission set essentials.warps." + args[0]), 20L * 6);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!"), 20L * 2);

                    //TODO testen

                }
            }
        } else {
            if (args.length == 0) {
                commandSender.sendMessage("Error! Nutze: /create TEAMNAME");
            }
            if (args.length == 1) {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                Bukkit.dispatchCommand(console, "scoreboard teams option " + args[0] + " nametagVisibility hideForOtherTeams");

                Bukkit.dispatchCommand(console, "lp creategroup s" + args[0]);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp creategroup l" + args[0]), 20L);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp creategroup l" + args[0]), 20L * 2);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group s" + args[0] + " parent add spieler" + args[0]), 20L * 3);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group s" + args[0] + " permission set essentials.warps." + args[0]), 20L * 4);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group l" + args[0] + " parent add leader" + args[0]), 20L * 5);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp group l" + args[0] + " permission set essentials.warps." + args[0]), 20L * 6);

                Bukkit.getScheduler().runTaskLater(Main.instance, () -> commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!"), 20L * 7);


            }
        }
        return false;
    }
}