/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.block.Block
 *  net.minecraft.init.Biomes
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldType
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.gen.IChunkGenerator
 *  net.minecraft.world.gen.feature.WorldGenerator
 *  net.minecraftforge.fml.common.IWorldGenerator
 */
package com.schnurritv.sexmod.world.gen;

import com.google.common.collect.Sets;
import com.schnurritv.sexmod.world.gen.generators.WorldGenStructure;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures
implements IWorldGenerator {
    public static final WorldGenStructure SEXHOUSE = new WorldGenStructure("sexhousechurch");
    public static final Set<Biome> SEXHOUSE_BIOMELIST = Sets.newHashSet((Object[])new Biome[]{Biomes.field_76785_t, Biomes.field_76770_e, Biomes.field_150585_R, Biomes.field_76769_d, Biomes.field_76772_c, Biomes.field_150588_X, Biomes.field_76767_f, Biomes.field_150583_P, Biomes.field_150589_Z, Biomes.field_76780_h, Biomes.field_76768_g});

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.field_73011_w.getDimension()) {
            case 0: {
                this.generateStructure(SEXHOUSE, world, random, chunkX, chunkZ, 25, SEXHOUSE_BIOMELIST);
                break;
            }
            case 1: {
                break;
            }
        }
    }

    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Set<Biome> BiomeList) {
        int x = chunkX * 16 + random.nextInt(15);
        int z = chunkZ * 16 + random.nextInt(15);
        int y = WorldGenCustomStructures.calculateGenerationHeight(world, x, z);
        BlockPos pos = new BlockPos(x, y, z);
        Biome biome = world.field_73011_w.getBiomeForCoords(pos);
        if (world.func_175624_G() != WorldType.field_77138_c && BiomeList.contains(biome) && random.nextInt(chance) == 0) {
            boolean canSpawnHere = true;
            if (world.func_180495_p(new BlockPos(x, y + 1, z)) != Blocks.field_150355_j.func_176223_P()) {
                for (int i = 0; i <= 16; ++i) {
                    for (int i1 = 0; i1 <= 16; ++i1) {
                        boolean isAir = world.func_175623_d(new BlockPos(x + i, y, z + i1));
                        canSpawnHere = canSpawnHere && !isAir;
                    }
                }
            } else {
                canSpawnHere = false;
            }
            if (canSpawnHere) {
                generator.func_180709_b(world, random, pos);
            }
        }
    }

    private static int calculateGenerationHeight(World world, int x, int z) {
        HashSet topBlocks = Sets.newHashSet((Object[])new Block[]{Blocks.field_150349_c, Blocks.field_150354_m, Blocks.field_180395_cM});
        int y = world.func_72800_K();
        boolean foundGround = false;
        while (!foundGround && y-- >= 0) {
            Block block = world.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
            foundGround = topBlocks.contains(block);
        }
        return y;
    }
}
