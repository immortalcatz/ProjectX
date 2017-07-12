/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render;

import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.projectx.tile.BlockDef;
import keri.projectx.tile.TileMultiShadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Option;
//TODO make less cancerous
@SideOnly(Side.CLIENT)
public class RenderShadowBlock implements IBlockRenderingHandler {
    public static EnumBlockRenderType RENDER_TYPE;

    static {
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderShadowBlock());
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileMultiShadow)) {
            return false;
        }

        Option<BlockDef> blockDef = ((TileMultiShadow) tile).getCurrBlockDef();

        if (!blockDef.isDefined())
            return false;

        return Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(blockDef.get().getBlockState(), pos, world, buffer);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {

    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {

    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }
}