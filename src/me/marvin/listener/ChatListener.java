package me.marvin.listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.Random;
import org.bukkit.event.Listener;

public class ChatListener implements Listener
{
    private static final Random RANDOM;

    static {
        RANDOM = new Random();
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        String teamName = player.getScoreboard().getPlayerTeam(player).getDisplayName();
        //TODO Falls Spieler kein Team besitzt -> Error!
        String prefix = String.format("%s", teamName);

        File playersFile = new File("plugins/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
        String suffix = config.getString("Suffix" + " ");
        if (suffix == null) {
            suffix = "";
        }

        String displayName = config.getString("Nick");
        player.setDisplayName(displayName);

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
            if (distance > 30.0) {
                continue;
            }
            ++i;
            if (distance > 15.0) {
                target.sendMessage("§f" + prefix + " " + player.getDisplayName() + " " + suffix + "§8➝ §o§7§k#§r§7§o" + randomizeMessage(event.getMessage()) + "§7§k#");
            }
            else {
                target.sendMessage("§f" + prefix + " " + player.getDisplayName() + " " + suffix + "§8➝ §7" + event.getMessage());
            }
        }
        if (i == 0) {
            player.sendMessage("§cDich hat leider niemand geh\u00f6rt.");
        }
        else {
            player.sendMessage("§f" + prefix + " " + player.getDisplayName() + " " + suffix + "§8➝ §7" + event.getMessage());
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