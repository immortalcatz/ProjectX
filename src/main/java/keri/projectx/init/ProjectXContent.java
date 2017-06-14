package keri.projectx.init;

import keri.ninetaillib.lib.mod.ContentLoader;
import keri.projectx.block.*;
import keri.projectx.block.machine.BlockFabricator;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;

@ContentLoader(modid = ModPrefs.MODID)
public class ProjectXContent {

    public static Block xycroniumOre = new BlockXycroniumOre();
    public static Block xycroniumNetherOre = new BlockXycroniumNetherOre();
    public static Block xycroniumStorage = new BlockXycroniumStorage();
    public static Block xycroniumBricks = new BlockXycroniumBricks();
    public static Block quartzCrystal = new BlockQuartzCrystal();
    public static Block fabricator = new BlockFabricator();

}
