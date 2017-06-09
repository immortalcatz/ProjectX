package keri.projectx.block;

import keri.ninetaillib.lib.block.BlockBase;
import keri.projectx.ProjectXTab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

public class BlockProjectX<T extends TileEntity> extends BlockBase<T> {

    public BlockProjectX(String blockName, Material material, MapColor mapColor) {
        super(blockName, material, mapColor);
    }

    public BlockProjectX(String blockName, Material material) {
        super(blockName, material);
    }

    public BlockProjectX(String blockName, Material material, MapColor mapColor, String... subNames) {
        super(blockName, material, mapColor, subNames);
    }

    public BlockProjectX(String blockName, Material material, String... subNames) {
        super(blockName, material, subNames);
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

}
