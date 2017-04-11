package keri.projectx.integration.tinkers.materials;

import keri.projectx.init.ProjectXContent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;

public class TinkersMaterials {

    public static final Material XYCRONIUM_BLUE = new Material("xycroniumBlue", 0xFFFFFFFF);
    public static final Material XYCRONIUM_GREEN = new Material("xycroniumGreen", 0xFFFFFFFF);
    public static final Material XYCRONIUM_RED = new Material("xycroniumRed", 0xFFFFFFFF);
    public static final Material XYCRONIUM_DARK = new Material("xycroniumDark", 0xFFFFFFFF);
    public static final Material XYCRONIUM_LIGHT = new Material("xycroniumLight", 0xFFFFFFFF);

    public static void preInit(){
        XYCRONIUM_BLUE.setShard(new ItemStack(ProjectXContent.xycroniumNugget, 1, 0));
        XYCRONIUM_BLUE.addItem(new ItemStack(ProjectXContent.xycroniumCrystal, 1, 0), 1, Material.VALUE_Shard);
        XYCRONIUM_BLUE.addItem(new ItemStack(ProjectXContent.xycroniumIngot, 1, 0), 1, Material.VALUE_Ingot);

        XYCRONIUM_GREEN.setShard(new ItemStack(ProjectXContent.xycroniumNugget, 1, 1));
        XYCRONIUM_GREEN.addItem(new ItemStack(ProjectXContent.xycroniumCrystal, 1, 1), 1, Material.VALUE_Shard);
        XYCRONIUM_GREEN.addItem(new ItemStack(ProjectXContent.xycroniumIngot, 1, 1), 1, Material.VALUE_Ingot);

        XYCRONIUM_RED.setShard(new ItemStack(ProjectXContent.xycroniumNugget, 1, 2));
        XYCRONIUM_RED.addItem(new ItemStack(ProjectXContent.xycroniumCrystal, 1, 2), 1, Material.VALUE_Shard);
        XYCRONIUM_RED.addItem(new ItemStack(ProjectXContent.xycroniumIngot, 1, 2), 1, Material.VALUE_Ingot);

        XYCRONIUM_DARK.setShard(new ItemStack(ProjectXContent.xycroniumNugget, 1, 3));
        XYCRONIUM_DARK.addItem(new ItemStack(ProjectXContent.xycroniumCrystal, 1, 3), 1, Material.VALUE_Shard);
        XYCRONIUM_DARK.addItem(new ItemStack(ProjectXContent.xycroniumIngot, 1, 3), 1, Material.VALUE_Ingot);

        XYCRONIUM_LIGHT.setShard(new ItemStack(ProjectXContent.xycroniumNugget, 1, 4));
        XYCRONIUM_LIGHT.addItem(new ItemStack(ProjectXContent.xycroniumCrystal, 1, 4), 1, Material.VALUE_Shard);
        XYCRONIUM_LIGHT.addItem(new ItemStack(ProjectXContent.xycroniumIngot, 1, 4), 1, Material.VALUE_Ingot);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderInfo(){

    }

}
