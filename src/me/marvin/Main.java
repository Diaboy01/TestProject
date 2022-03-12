package me.marvin;

import me.marvin.command.*;
import me.marvin.listener.*;
import org.bukkit.*;
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

        getCommand("add").setExecutor(new AddLeader());
        getCommand("invite").setExecutor(new Invite());
        getCommand("create").setExecutor(new CreateTeam());
        getCommand("role").setExecutor(new Role());
        getCommand("nick").setExecutor(new Nick());
        getCommand("farmwelt").setExecutor(new Farmwelt());
        getCommand("wtp").setExecutor(new WorldTeleport());


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
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}