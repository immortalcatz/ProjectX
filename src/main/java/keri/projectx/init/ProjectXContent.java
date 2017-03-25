package keri.projectx.init;

import keri.projectx.block.BlockXycroniumBlock;
import keri.projectx.block.BlockXycroniumBricks;
import keri.projectx.block.BlockXycroniumOre;
import net.minecraft.block.Block;

public class ProjectXContent {

    public static Block xycroniumOre;
    public static Block xycroniumBlock;
    public static Block xycroniumBricks;

    public static void preInit(){
        xycroniumOre = new BlockXycroniumOre();
        xycroniumBlock = new BlockXycroniumBlock();
        xycroniumBricks = new BlockXycroniumBricks();
    }

    public static void init(){

    }

}
