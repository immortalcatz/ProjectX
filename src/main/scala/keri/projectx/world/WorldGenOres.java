/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.world;

import keri.projectx.ProjectX;
import keri.projectx.init.ProjectXContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenOres implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()){
            case -1:
                this.generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                this.generateOverworld(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                this.generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
        }
    }

    private void generateOverworld(World world, Random rand, int blockX, int blockZ){
        int[] xycroniumOreBlue = (int[])ProjectX.CONFIG.getProperty("xycroniumOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_ORE, 0, Blocks.STONE, world, rand, blockX, blockZ, xycroniumOreBlue);
        int[] xycroniumOreGreen = (int[])ProjectX.CONFIG.getProperty("xycroniumOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_ORE, 1, Blocks.STONE, world, rand, blockX, blockZ, xycroniumOreGreen);
        int[] xycroniumOreRed = (int[])ProjectX.CONFIG.getProperty("xycroniumOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_ORE, 2, Blocks.STONE, world, rand, blockX, blockZ, xycroniumOreRed);
        int[] xycroniumOreDark = (int[])ProjectX.CONFIG.getProperty("xycroniumOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_ORE, 3, Blocks.STONE, world, rand, blockX, blockZ, xycroniumOreDark);
        int[] xycroniumOreLight = (int[])ProjectX.CONFIG.getProperty("xycroniumOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_ORE, 4, Blocks.STONE, world, rand, blockX, blockZ, xycroniumOreLight);
    }

    private void generateNether(World world, Random rand, int blockX, int blockZ){
        int[] xycroniumNetherOreBlue = (int[])ProjectX.CONFIG.getProperty("xycroniumNetherOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_NETHER_ORE, 0, Blocks.NETHERRACK, world, rand, blockX, blockZ, xycroniumNetherOreBlue);
        int[] xycroniumNetherOreGreen = (int[])ProjectX.CONFIG.getProperty("xycroniumNetherOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, Blocks.NETHERRACK, world, rand, blockX, blockZ, xycroniumNetherOreGreen);
        int[] xycroniumNetherOreRed = (int[])ProjectX.CONFIG.getProperty("xycroniumNetherOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_NETHER_ORE, 2, Blocks.NETHERRACK, world, rand, blockX, blockZ, xycroniumNetherOreRed);
        int[] xycroniumNetherOreDark = (int[])ProjectX.CONFIG.getProperty("xycroniumNetherOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_NETHER_ORE, 3, Blocks.NETHERRACK, world, rand, blockX, blockZ, xycroniumNetherOreDark);
        int[] xycroniumNetherOreLight = (int[])ProjectX.CONFIG.getProperty("xycroniumNetherOreBlue").getValue();
        this.generateBlock(ProjectXContent.XYCRONIUM_NETHER_ORE, 4, Blocks.NETHERRACK, world, rand, blockX, blockZ, xycroniumNetherOreLight);
    }

    private void generateEnd(World world, Random rand, int blockX, int blockZ){

    }

    @SuppressWarnings("deprecation")
    private void generateBlock(Block block, int meta, Block target, World world, Random rand, int blockX, int blockZ, int[] params){
        int chance = params[0];
        int minHeight = params[1];
        int maxHeight = params[2];
        int minVeinSize = params[3];
        int maxVeinSize = params[4];

        for(int i = 0; i < chance; i++){
            int x = blockX + rand.nextInt(16);
            int y = minHeight + rand.nextInt(maxHeight - minHeight);
            int z = blockZ + rand.nextInt(16);
            int v = minVeinSize + rand.nextInt(maxVeinSize - minVeinSize);
            new WorldGenMinable(block.getStateFromMeta(meta), v, BlockMatcher.forBlock(target)).generate(world, rand, new BlockPos(x, y, z));
        }
    }

}
