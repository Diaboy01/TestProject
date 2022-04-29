package me.marvin;

import me.marvin.command.*;
import me.marvin.listener.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin {

    public static Main instance;

    //public static String GAME_WORLD_NAME = "Spielwelt";
    public static final Random RANDOM = new Random();

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        // pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerMotdListener(), this);
        //pluginManager.registerEvents(new BlockBreakListener(), this);
        //pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerWorldTimingListener(), this);
        pluginManager.registerEvents(new PlayerExtraDamageListener(), this);
        pluginManager.registerEvents(new ExplosionListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);
        pluginManager.registerEvents(new PortalListener(), this);
        //pluginManager.registerEvents(new PlayerLoginListener(), this);

        getCommand("add").setExecutor(new AddLeader());
        getCommand("invite").setExecutor(new Invite());
        getCommand("accept").setExecutor(new Accept());
        getCommand("create").setExecutor(new CreateTeam());
        getCommand("role").setExecutor(new Role());
        getCommand("farmwelt").setExecutor(new Farmwelt());
        getCommand("wtp").setExecutor(new WorldTeleport());
        getCommand("modifydamage").setExecutor(new PlayerDamageCommand());
        getCommand("teambase").setExecutor(new SetTeamBase());

        //new PlayerDamageCommand();


        /*
        getCommand("test").setExecutor(new TestCommand());
        getCommand("test").setTabCompleter(new TestCommand());
         */


        /*
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                }
            }
        }, 20*5, 20);
        */
        /*
        WorldCreator worldCreator = new WorldCreator(GAME_WORLD_NAME);
        worldCreator.generateStructures(false);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generator(new EmptyChunkGenerator());
        World world = worldCreator.createWorld();
        new Location(world, 0, 64, 0).getBlock().setType(Material.GRASS);
        world.setSpawnLocation(0, 66, 0);
        */

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player, null);
            Bukkit.getPluginManager().callEvent(playerJoinEvent);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(player, null);
            Bukkit.getPluginManager().callEvent(playerQuitEvent);
            player.kickPlayer("Server Plugin Update! Please rejoin!");
        }

        super.onDisable();
    }
}