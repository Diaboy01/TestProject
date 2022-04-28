package me.marvin.listener;


import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {
    @EventHandler
    public void onExplosionPrimeEvent(final ExplosionPrimeEvent e) {
        String world = String.valueOf(e.getEntity().getWorld().getName());
        if (world.equals("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplodeEvent(final EntityExplodeEvent e) {
        String world = String.valueOf(e.getEntity().getWorld().getName());
        if (world.equals("world")) {
            e.setCancelled(true);
        }
    }
}
