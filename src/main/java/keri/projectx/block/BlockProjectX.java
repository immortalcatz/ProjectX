package keri.projectx.block;

import keri.ninetaillib.block.BlockBase;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockProjectX<T extends TileEntity> extends BlockBase<T> {

    public BlockProjectX(String blockName, Material material, MapColor mapColor) {
        super(ModPrefs.MODID, blockName, material, mapColor);
    }

    public BlockProjectX(String blockName, Material material) {
        super(ModPrefs.MODID, blockName, material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

}
