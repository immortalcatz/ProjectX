/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block;

import keri.ninetaillib.lib.block.BlockBase;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockProjectX<T extends TileEntity> extends BlockBase<T> {

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

    protected void registerTile(Class<? extends TileEntity> clazz){
        String internalName = "tile." + ModPrefs.MODID + "." + this.getBlockName();
        GameRegistry.registerTileEntity(clazz, internalName);
    }

    protected void registerTile(Class<? extends TileEntity> clazz, String name){
        String internalName = "tile." + ModPrefs.MODID + "." + name;
        GameRegistry.registerTileEntity(clazz, internalName);
    }

}
