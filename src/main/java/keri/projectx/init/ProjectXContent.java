package keri.projectx.init;

import keri.ninetaillib.lib.mod.ContentLoader;
import keri.projectx.block.BlockXycroniumOre;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;

@ContentLoader(modid = ModPrefs.MODID)
public class ProjectXContent {

    public static Block xycroniumOre = new BlockXycroniumOre();

}
