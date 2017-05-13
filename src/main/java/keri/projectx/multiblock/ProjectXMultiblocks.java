package keri.projectx.multiblock;

import keri.ninetaillib.multiblock.MultiblockLoader;
import keri.ninetaillib.multiblock.MultiblockPattern;
import keri.projectx.util.ModPrefs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProjectXMultiblocks {

    private static final MultiblockLoader multiblockLoader = new MultiblockLoader();

    public static void preInit(FMLPreInitializationEvent event){
        multiblockLoader.loadMultiblock("tank_tier_1", new ResourceLocation(ModPrefs.MODID, "multiblocks/tank_tier_1"));
        multiblockLoader.loadMultiblock("tank_tier_2", new ResourceLocation(ModPrefs.MODID, "multiblocks/tank_tier_2"));
        multiblockLoader.loadMultiblocks(event);
    }

    public static MultiblockPattern getMultiblock(String name){
        return multiblockLoader.getMutliblock(name);
    }

}
