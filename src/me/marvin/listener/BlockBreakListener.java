package me.marvin.listener;

import me.marvin.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(!block.getWorld().getName().equals(Main.GAME_WORLD_NAME)) {
            return;
        }

        Location location = block.getLocation();
        if(location.getBlockX() == 0 && location.getBlockY() == 64 && location.getBlockZ() == 0) {
            event.setCancelled(true);
            Material material = getRandomBlock();
            location.getWorld().dropItem(location.add(0, 1, 0), new ItemStack(material));
        }
    }

    private Material getRandomBlock() {
        Material material = Material.values()[Main.RANDOM.nextInt(Material.values().length)];

        if(!material.isBlock()) {
            return getRandomBlock();
        }

        return material;
    }
}