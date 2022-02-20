package me.marvin.listener;

import me.marvin.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("§f" + player.getName() + " §7hat den Server betreten!");

        if(player.hasPlayedBefore()) {
            player.sendMessage("§aWillkommen zurück!");
        } else {
            player.sendMessage("§aHerzlich Willkommen!");
        }

        if(player.hasPermission("test.info")) {
            player.sendMessage("§cSchon ein paar Spieler getötet?");
        }

        //player.teleport(Bukkit.getWorld(Main.GAME_WORLD_NAME).getSpawnLocation());

        setTablistHeaderAndFooter(player, "Novorex Network", "Sponsor: Nitrado.net");

        if(player.isOp()) {
            player.setPlayerListName("§8[§4Owner§8] §4" + player.getName());
        } else {
            player.setPlayerListName("§8[§7Sklave§8] §4" + player.getName());
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