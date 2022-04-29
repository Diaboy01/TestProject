package me.marvin.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.Random;

public class ChatListener implements Listener
{
    private static final Random RANDOM;

    static {
        RANDOM = new Random();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);


        String role = config.getString("Role");
        if (role == null) {
            role = "";
        } else role = role + " ";
        int i = 0;
        event.setCancelled(true);
        for (final Player target : Bukkit.getOnlinePlayers()) {
            if (player == target) {
                continue;
            }
            if (!target.getWorld().equals(player.getWorld())) {
                continue;
            }
            final double distance = target.getLocation().distance(player.getLocation());
            if (distance > 60.0) {
                continue;
            }
            ++i;
            if (distance > 30.0) {
                target.sendMessage("§f" + role + "" + player.getDisplayName() + " " + "§8➝ §o§7§k#§r§7§o" + randomizeMessage(event.getMessage()) + "§7§k#");
            }
            else {
                target.sendMessage("§f" + role + "" + player.getDisplayName() + " " + "§8➝ §7" + event.getMessage());
            }
        }
        if (i == 0) {
            player.sendMessage("§cDich hat leider niemand geh\u00f6rt.");
        }
        else {
            player.sendMessage("§f" + role + "" + player.getDisplayName() + " " + "§8➝ §7" + event.getMessage());
        }
    }

    private static String randomizeMessage(final String message) {
        if (message == null) {
            return "";
        }
        final char[] characters = message.toCharArray();
        for (int i = 0; i < characters.length; ++i) {
            char c = characters[i];
            c += (char)ChatListener.RANDOM.nextInt(16);
            characters[i] = c;
        }
        return new String(characters);
    }
}