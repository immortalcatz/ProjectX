package keri.projectx.init;

import keri.projectx.block.BlockQuartzCrystal;
import keri.projectx.block.BlockXycroniumBlock;
import keri.projectx.block.BlockXycroniumBricks;
import keri.projectx.block.BlockXycroniumOre;
import keri.projectx.item.ItemXynergyCell;
import keri.projectx.tile.TileEntityQuartzCrystal;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProjectXContent {

    public static Block xycroniumOre;
    public static Block xycroniumBlock;
    public static Block xycroniumBricks;
    public static Block quartzCrystal;

    public static Item xynergyCell;

    public static void preInit(){
        xycroniumOre = new BlockXycroniumOre();
        xycroniumBlock = new BlockXycroniumBlock();
        xycroniumBricks = new BlockXycroniumBricks();
        quartzCrystal = new BlockQuartzCrystal();

        xynergyCell = new ItemXynergyCell();
    }

    public static void init(){
        registerTile(TileEntityQuartzCrystal.class, quartzCrystal);
    }

    private static void registerTile(Class<? extends TileEntity> tileClass, Block owner){
        String registryName = "tile." + owner.getRegistryName().getResourceDomain() + "." + owner.getRegistryName().getResourcePath();
        GameRegistry.registerTileEntity(tileClass, registryName);
    }

}
