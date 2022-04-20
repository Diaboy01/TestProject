package me.marvin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.player.PlayerTeleportEvent;
        import org.bukkit.event.EventHandler;
        import org.bukkit.entity.Player;
        import org.bukkit.World;
        import org.bukkit.event.player.PlayerPortalEvent;
        import org.bukkit.event.Listener;

public class PortalListener implements Listener {

    @EventHandler
    public void PlayerPortal(final PlayerPortalEvent event) {
        final Player player = event.getPlayer();
        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (!player.hasPermission("Portal.Nether")) {
                event.setCancelled(true);
                player.sendMessage("You don't have permissions to go to the Nether!");
            }
        }
        else if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END && !player.hasPermission("Portal.End")) {
            event.setCancelled(true);
            player.sendMessage("&4You don't have permissions to go to the End!");
        }
    }


    @EventHandler
    public void PlayerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String w = String.valueOf(event.getTo().getWorld());
        if (w == "world") {
            player.sendMessage("Achtung! PvP ist in dieser Welt verboten! " + w);
        } else {
            player.sendMessage("Achtung! PvP ist in dieser Welt erlaubt! " + w);
        }
        World world = Bukkit.getWorld(w);
        world.setDifficulty(Difficulty.HARD);
        Bukkit.dispatchCommand(console, "gamerule naturalRegeneration false");
        World Bauwelt = Bukkit.getWorld("world");
        Bauwelt.setDifficulty(Difficulty.PEACEFUL);

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (!player.hasPermission("world.NETHER")) {
                event.setCancelled(true);
                player.sendMessage("You don't have permissions to go to the Nether!");
            }
        } else if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            if (!player.hasPermission("world.THE_END")) {
                event.setCancelled(true);
                player.sendMessage("You don't have permissions to go to the End!");
            }
        } else if (!(event.getTo().getWorld().getEnvironment() == World.Environment.NORMAL)){
            if (!player.hasPermission("world.UNNORMAL")) {
                event.setCancelled(true);
                player.sendMessage("You don't have permissions to go to this world/dimension!");
            }
        }


    }
}
