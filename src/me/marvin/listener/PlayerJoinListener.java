package me.marvin.listener;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.File;
import java.lang.reflect.Field;

import static me.marvin.api.YAMLconfig.printYml;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        if(player.hasPlayedBefore()) {
            player.sendMessage("§aWillkommen zurück!");
        } else {
            player.sendMessage("§aHerzlich Willkommen!");
            Bukkit.dispatchCommand(console, "scoreboard teams join - " + playerName);
            File playersFile = new File("plugins/Players/", playerName + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
            printYml(playerName, "Team", "-");
        }

        if(player.hasPermission("test.info")) {
            player.sendMessage("§cSchon ein paar Spieler getötet?");
        }

        File playersFile = new File("plugins/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        String teamName = config.getString("Team");
        String p = String.format("%s", teamName);
        if (p == null) {
            p = "";
        }
        String prefix = p + " ";

        String s = config.getString("Role");
        if (s == null) {
            s = "";
        }
        String suffix = s + " ";

        String displayName = config.getString("Nick");
        player.setDisplayName(displayName);

        player.sendMessage("DisplayName: " + player.getDisplayName() + " Name: " + player.getName());
        player.sendMessage("Teamname: " + teamName + " Teamname: " + teamName);
        player.sendMessage("Prefix: " + prefix + " Suffix: " + suffix);

        event.setJoinMessage("§f" + player.getName() + " §7hat den Server betreten!");

        //player.teleport(Bukkit.getWorld(Main.GAME_WORLD_NAME).getSpawnLocation());

        setTablistHeaderAndFooter(player, "Novorex Network", "Sponsor: Nitrado.net");


        if (teamName.matches("-")) {
            player.setPlayerListName("§r\uD83D\uDC80✖ §o" + player.getDisplayName());
            return;
        }

            if(player.isOp()) {
                player.setPlayerListName("§r§l" + prefix + " " + player.getDisplayName() + " " + suffix + "");
            } else {
                player.setPlayerListName("§r" + prefix + " " + player.getDisplayName() + " " + suffix + "");
            }

    }

    public void setTablistHeaderAndFooter(Player player, String h, String f) {
        PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + h + "\"}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + f + "\"}");

        modifyPrivateField(packetPlayOutPlayerListHeaderFooter, "a", header);
        modifyPrivateField(packetPlayOutPlayerListHeaderFooter, "b", footer);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerListHeaderFooter);
    }

    private void modifyPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}