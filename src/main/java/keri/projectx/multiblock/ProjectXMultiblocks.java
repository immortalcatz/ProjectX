package keri.projectx.multiblock;

import keri.ninetaillib.multiblock.MultiblockPattern;
import keri.ninetaillib.multiblock.SimpleMultiblockLoader;
import keri.projectx.util.ModPrefs;
import net.minecraft.util.ResourceLocation;

public class ProjectXMultiblocks {

    private static SimpleMultiblockLoader multiblockLoader;

    public static void preInit(){
        multiblockLoader = new SimpleMultiblockLoader();
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
