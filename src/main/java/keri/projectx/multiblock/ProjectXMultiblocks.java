package keri.projectx.multiblock;

import keri.ninetaillib.multiblock.MultiblockLoader;
import keri.ninetaillib.multiblock.MultiblockPattern;
import keri.projectx.util.ModPrefs;
import net.minecraft.util.ResourceLocation;

public class ProjectXMultiblocks {

    private static MultiblockLoader multiblockLoader;

    public static void preInit(){
        multiblockLoader = new MultiblockLoader();
        multiblockLoader.loadMultiblock("tank_tier_1", new ResourceLocation(ModPrefs.MODID, "multiblocks/tank_tier_1"));
        multiblockLoader.loadMultiblock("tank_tier_2", new ResourceLocation(ModPrefs.MODID, "multiblocks/tank_tier_2"));
    }

    public static void init(){
        multiblockLoader.loadMultiblocks();
    }

    public static MultiblockPattern getMultiblock(String name){
        return multiblockLoader.getMutliblock(name);
    }

}
