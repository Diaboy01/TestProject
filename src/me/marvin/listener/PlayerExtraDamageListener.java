package me.marvin.listener;

import me.marvin.command.PlayerDamageCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

public class PlayerExtraDamageListener implements Listener
{
    @EventHandler
    public void onPlayerDamage(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            final double health = player.getHealth();
            final double value = PlayerDamageCommand.getValue();
            if (value == -1.0) {
                return;
            }
            final double percent = health * value;
            event.setDamage(event.getDamage() * (1.0 + percent));
        }
    }
}
