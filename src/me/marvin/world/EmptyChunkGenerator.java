package me.marvin.world;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                biome.setBiome(x, z, Biome.PLAINS);
            }
        }

        return chunkData;
    }
}
