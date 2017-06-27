/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.ModelUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderReinforcedBlock implements IBlockRenderingHandler {

    public static final RenderReinforcedBlock INSTANCE = new RenderReinforcedBlock();
    public static EnumBlockRenderType RENDER_TYPE;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
        return false;
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {

    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();

        if(stack.getTagCompound() != null){
            ItemStack blockStack = new ItemStack(stack.getTagCompound().getCompoundTag("block"));

            if(blockStack != null){
                CCRenderState renderState = CCRenderState.instance();
                renderState.bind(buffer);
                CCModel model = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D));
                Block block = Block.getBlockFromItem(blockStack.getItem());

                for(int side = 0; side < 6; side++){
                    TextureAtlasSprite texture = TextureUtils.getIconsForBlock(block.getStateFromMeta(blockStack.getMetadata()), side)[0];
                    model.render(renderState, new IconTransformation(texture));
                }
            }
        }

        GlStateManager.popMatrix();
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
