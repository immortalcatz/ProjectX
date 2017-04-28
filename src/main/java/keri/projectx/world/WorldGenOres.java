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
        this.generateBlock(ProjectXContent.xycroniumOre, 0, Blocks.STONE, world, rand, blockX, blockZ, ProjectX.CONFIG.worldgenOreXycroniumBlue.getValue());
        this.generateBlock(ProjectXContent.xycroniumOre, 1, Blocks.STONE, world, rand, blockX, blockZ, ProjectX.CONFIG.worldgenOreXycroniumGreen.getValue());
        this.generateBlock(ProjectXContent.xycroniumOre, 2, Blocks.STONE, world, rand, blockX, blockZ, ProjectX.CONFIG.worldgenOreXycroniumRed.getValue());
        this.generateBlock(ProjectXContent.xycroniumOre, 3, Blocks.STONE, world, rand, blockX, blockZ, ProjectX.CONFIG.worldgenOreXycroniumDark.getValue());
        this.generateBlock(ProjectXContent.xycroniumOre, 4, Blocks.STONE, world, rand, blockX, blockZ, ProjectX.CONFIG.worldgenOreXycroniumLight.getValue());
    }

    private void generateNether(World world, Random rand, int blockX, int blockZ){

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
