/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import keri.projectx.client.render.IAnimationHandler;
import keri.projectx.client.render.RenderSimpleGlow;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockAnimationHandler<T extends TileEntity> extends BlockProjectX<T> implements IAnimationHandler {

    public BlockAnimationHandler(String blockName, Material material, MapColor mapColor) {
        super(blockName, material, mapColor);
    }

    public BlockAnimationHandler(String blockName, Material material) {
        super(blockName, material);
    }

    public BlockAnimationHandler(String blockName, Material material, MapColor mapColor, String... subNames) {
        super(blockName, material, mapColor, subNames);
    }

    public BlockAnimationHandler(String blockName, Material material, String... subNames) {
        super(blockName, material, subNames);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderSimpleGlow.RENDER_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

}
