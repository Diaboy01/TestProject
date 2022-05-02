package me.marvin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Teilnehmen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //TODO Kit/Teilnehmen nur 1 mal | PvP Welt betretbar machen | PvP Welt kein Farmwelt Zeit Limit
        /*
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;
            String playerName = player.getName();

            //TODO PvP Welt get aus config

            String pvpworldName = "pvpworld1";

            World pvpworld = Bukkit.getWorld(pvpworldName);

            //TODO bei WTP wird Welt geladen. Ist die Welt nicht geladen wird setDifficulty etc. einen fehler werfen

            pvpworld.setDifficulty(Difficulty.PEACEFUL);
            pvpworld.setGameRuleValue("naturalRegeneration", "false");

            World world = player.getWorld();
            String worldName = world.getName();
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
            Bukkit.dispatchCommand(console, "say " + playerName + " ist nun PvP-Event Teilnehmer!");

            if (worldName.equals(pvpworldName)) {
                commandSender.sendMessage("Du bist bereits in der PvP Welt!");
                return false;
            }
            if (args.length == 0) {
                //TODO Alles nochmal überprüfen
                commandSender.sendMessage("§aZufallsteleport in die PvP Welt...!");


                Random random = new Random();

                int randomX = random.nextInt(500 + 20) + 20;
                int randomZ = random.nextInt(500 + 20) + 20;

                int randomChunkX = randomX / 16;
                int randomChunkZ = randomZ / 16;

                ((CraftWorld) pvpworld).getHandle().getChunkProviderServer().getOrLoadChunkAt(randomChunkX, randomChunkZ);
                World finalPvp = pvpworld;
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(finalPvp.getHighestBlockAt(randomX, randomZ).getLocation().add(0, 1, 0)), 20L);
                player.setInvulnerable(true);
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setInvulnerable(false), 20L * 15);
                Bukkit.dispatchCommand(console, "rg flag __global__ invincible allow");
                Bukkit.dispatchCommand(console, "effect " + playerName + " minecraft:blindness 255 10");
                player.sendTitle("PvP-Event!", "Mach dich bereit!", 20, 120, 20);
                Bukkit.dispatchCommand(console, "kit give pvp1 " + playerName);

            }
            return true;
        }

         */
        return true;
    }

}