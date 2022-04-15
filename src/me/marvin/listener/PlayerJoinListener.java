package me.marvin.listener;

import com.mojang.authlib.GameProfile;
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
import java.lang.reflect.Method;

import static me.marvin.api.YAMLPlayers.printYml;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        World world = Bukkit.getWorld("world");

        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        String teamName = config.getString("Team");

        if(player.hasPlayedBefore()) {
            world.setDifficulty(Difficulty.PEACEFUL);
            Bukkit.dispatchCommand(console, "gamerule naturalRegeneration false");
            player.sendMessage("§a Normal Join");
            player.sendMessage("Dein Team: " + teamName);
            Bukkit.dispatchCommand(console, "scoreboard teams add " + teamName);
            Bukkit.dispatchCommand(console, "scoreboard teams join " + teamName + " " + playerName);
            //        if (w == "world") {
            //            player.sendMessage("Achtung! PvP ist in dieser Welt verboten! " + w);
            //        } else {
            //            player.sendMessage("Achtung! PvP ist in dieser Welt erlaubt! " + w);
            //        }
            //TELLRAW:
            //Bukkit.dispatchCommand(console, "tellraw @a ["",{"text":"Server Sponsor: ","bold":true,"italic":true,"color":"gold"},{"text":"Nitrado.net","bold":true,"italic":true,"underlined":true,"color":"gold","clickEvent":{"action":"open_url","value":"https://nitra.do/KevinFilmt"}}] ");
        } else {
            player.sendMessage("§a First Join");
            player.getInventory().clear();
            //for(int i = 0; i < 35; i++){
            //player.getInventory().setItem(i, null);
            //}
            player.teleport(new Location(world, 0, 100, 0));
            Bukkit.dispatchCommand(console, "scoreboard teams add - ");
            Bukkit.dispatchCommand(console, "scoreboard teams join - " + playerName);
            printYml(playerName, "Team", "-");
        }



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
                setTag(player, "Test");
            } else {
                setTag(player,"Test");
                player.setPlayerListName("§r" + prefix + " " + player.getDisplayName() + " " + suffix + "");
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
    //TODO setTag() geht net :(
    private void setTag2(Player player, String name) {
        try {

            Method getHandle = player.getClass().getMethod("getHandle");
            Object entityPlayer1 = getHandle.invoke(player);

            GameProfile entityPlayer = ((CraftPlayer)player).getHandle().getProfile();

            Field f = entityPlayer .getClass().getDeclaredField("name");
            f.setAccessible(true);
            f.set(entityPlayer, name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTag(Player player, String tag) {

        try {

            Object nmsPlayer = player;
            GameProfile profile = ((GameProfile) nmsPlayer.getClass().getMethod("getProfile").invoke(nmsPlayer));
            Field name = profile.getClass().getDeclaredField("name");
            name.setAccessible(true);
            name.set(profile, tag);

            for(Player pls : Bukkit.getOnlinePlayers()){

                pls.hidePlayer(player);
                pls.showPlayer(pls);

            }

        } catch (Exception e) {

            e.printStackTrace();

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