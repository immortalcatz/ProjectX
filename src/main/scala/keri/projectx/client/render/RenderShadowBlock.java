/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.projectx.tile.BlockDef;
import keri.projectx.tile.TileEntityMultiShadow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scala.Option;

public class RenderShadowBlock implements IBlockRenderingHandler {

    public static EnumBlockRenderType RENDER_TYPE;

    static {
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderShadowBlock());
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, BlockRenderLayer layer) {
        TileEntity tile = world.getTileEntity(pos);

        if (!(tile instanceof TileEntityMultiShadow)) {
            return false;
        }

        Option<BlockDef> blockDef = ((TileEntityMultiShadow)tile).getCurrBlockDef();

        if (!blockDef.isDefined()) {
            return false;
        }

        return Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(blockDef.get().getBlockState(), pos, world, buffer);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        CCModel model = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D));
        Translation t = new Translation(Vector3.fromBlockPos(pos));
        model.apply(t).render(renderState, new IconTransformation(texture));
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {}

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
