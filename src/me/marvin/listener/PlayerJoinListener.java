package me.marvin.listener;

import me.marvin.Main;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.lang.reflect.Field;

import static me.marvin.api.YAMLPlayers.printYml;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        Player player = event.getPlayer();
        event.setJoinMessage("§fServer §8➝ §7 [+] " + player.getName());
        String playerName = player.getName();

        Bukkit.dispatchCommand(console,"tellraw " + playerName + " [\"\",{\"text\":\"Sponsor: \",\"italic\":true,\"color\":\"gold\"},{\"text\":\"Nitrado.net\",\"italic\":true,\"underlined\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://nitra.do/KevinFilmt\"}}]");

        World playerWorld = player.getWorld();
        playerWorld.setDifficulty(Difficulty.HARD);
        World world = Bukkit.getWorld("world");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setDifficulty(Difficulty.PEACEFUL);

        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        String teamName = config.getString("Team");

        if(player.hasPlayedBefore()) {
            //NORMAL JOIN
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRuleValue("naturalRegeneration", "false");
            Bukkit.dispatchCommand(console, "scoreboard teams add " + teamName);
            Bukkit.dispatchCommand(console, "scoreboard teams join " + teamName + " " + playerName);
            String playerWorldName = playerWorld.getName();
            if (playerWorldName.equals("world")) {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt verboten!");
            } else {
                player.sendMessage("§cAchtung! PVP ist in dieser Welt erlaubt!");
            }
        } else {
            //FIRST JOIN
            //for(int i = 0; i < 35; i++){
            //player.getInventory().setItem(i, null);
            //}
            player.teleport(new Location(world, -4, 70, -6));
            Bukkit.dispatchCommand(console, "scoreboard teams add - ");
            Bukkit.dispatchCommand(console, "scoreboard teams join - " + playerName);
            printYml(playerName, "Team", "-");
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "clear " + playerName), 20L * 5);


        }

        

        //player.teleport(Bukkit.getWorld(Main.GAME_WORLD_NAME).getSpawnLocation());

        setTablistHeaderAndFooter(player, "§fMinecraft §bHEXXIT §fII", "§6by Novorex.net / §6§oSponsor: Nitrado.net");


        //TODO bei FirstJoin ist Prefix trotzdem #null

        String prefix = String.format("%s", teamName);
        if (prefix == null) {
            prefix = "";
        } else prefix = prefix + " ";

        if(player.isOp()) {
                player.setPlayerListName("§r§l#" + prefix + "" + player.getDisplayName());
            } else {
                player.setPlayerListName("§r#" + prefix + "" + player.getDisplayName());
            }

        if (teamName.matches("-")) {
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setPlayerListName("" + player.getDisplayName()), 20L * 3);
        }
            /*
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("3s");
                if(player.isOp()) {
                    player.setPlayerListName("§r§l" + prefix + " " + player.getDisplayName() + " " + suffix + "");
                } else {
                    player.setPlayerListName("§r" + prefix + " " + player.getDisplayName() + " " + suffix + "");
                }
            }
        }, 60L); //60 Tick = 3s
        */
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