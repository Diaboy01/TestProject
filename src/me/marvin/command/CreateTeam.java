package me.marvin.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateTeam implements CommandExecutor {

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
                    Bukkit.dispatchCommand(console, "lp creategroup s" + args[0]);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.dispatchCommand(console, "lp creategroup l" + args[0]);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.dispatchCommand(console, "lp group s" + args[0] + " parent add spieler");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.dispatchCommand(console, "lp group s" + args[0] + " permission set essentials.warps." + args[0]);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.dispatchCommand(console, "lp group l" + args[0] + " parent add leader");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.dispatchCommand(console, "lp group l" + args[0] + " permission set essentials.warps." + args[0]);
                    Bukkit.dispatchCommand(console, "scoreboard teams option " + args[0] + " nametagVisibility hideForOtherTeams");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!");
                }
            }
        } else {
            if (args.length == 0) {
                commandSender.sendMessage("Error! Nutze: /create TEAMNAME");
            }
            if (args.length == 1) {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "scoreboard teams add " + args[0]);
                Bukkit.dispatchCommand(console, "lp creategroup s" + args[0]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "lp creategroup l" + args[0]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "lp group s" + args[0] + " parent add spieler");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "lp group s" + args[0] + " permission set essentials.warps." + args[0]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "lp group l" + args[0] + " parent add leader");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.dispatchCommand(console, "lp group l" + args[0] + " permission set essentials.warps." + args[0]);
                Bukkit.dispatchCommand(console, "scoreboard teams option " + args[0] + " nametagVisibility hideForOtherTeams");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!");
            }
        }
        return false;
    }
}