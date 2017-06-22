/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingConstants;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSimpleGlow implements IBlockRenderingHandler {

    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderSimpleGlow());
        BLOCK_MODEL = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
        CCRenderState renderState = RenderingConstants.getRenderState();
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        IAnimationHandler animationHandler = (IAnimationHandler)world.getBlockState(pos).getBlock();
        CCModel modelAnimation = BLOCK_MODEL.copy();
        modelAnimation.apply(new Translation(Vector3.fromBlockPos(pos)));
        renderState.reset();
        renderState.bind(buffer);

        for(EnumFacing side : EnumFacing.VALUES){
            TextureAtlasSprite texture = animationHandler.getAnimationIcon(world, pos, side.getIndex());
            int color = animationHandler.getAnimationColor(world, pos, side.getIndex());
            int brightness = animationHandler.getAnimationBrightness(world, pos, side.getIndex());
            renderState.brightness = brightness;
            modelAnimation.setColour(color);
            modelAnimation.render(renderState, (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
        }

        CCModel modelOverlay = BLOCK_MODEL.copy();
        BakingVertexBuffer parent = BakingVertexBuffer.create();
        parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(parent);

        for(EnumFacing side : EnumFacing.VALUES){
            TextureAtlasSprite texture = null;

            if(iconProvider.getIcon(world, pos, side.getIndex()) != null){
                texture = iconProvider.getIcon(world, pos, side.getIndex());
            }
            else{
                texture = iconProvider.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), side.getIndex());
            }

            int colorMultiplier = iconProvider.getColorMultiplier(BlockAccessUtils.getBlockMetadata(world, pos), side.getIndex());
            modelOverlay.setColour(colorMultiplier);
            modelOverlay.render(renderState, 0 + (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
        }

        parent.finishDrawing();
        return RenderUtils.renderQuads(buffer, world, pos, parent.bake());
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCModel model = BLOCK_MODEL.copy();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);
        model.render(renderState, new IconTransformation(texture));
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = RenderingConstants.getRenderState();
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        IAnimationHandler animationHandler = (IAnimationHandler)Block.getBlockFromItem(stack.getItem());
        renderState.reset();
        renderState.bind(buffer);
        CCModel modelAnimation = BLOCK_MODEL.copy();

        for(EnumFacing side : EnumFacing.VALUES){
            TextureAtlasSprite texture = animationHandler.getAnimationIcon(stack, side.getIndex());
            int color = animationHandler.getAnimationColor(stack, side.getIndex());
            int brightness = animationHandler.getAnimationBrightness(stack, side.getIndex());
            renderState.brightness = brightness;
            modelAnimation.setColour(color);
            modelAnimation.render(renderState, 0 + (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
        }

        Tessellator.getInstance().draw();
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = lastBrightness;
        CCModel modelOverlay = BLOCK_MODEL.copy();

        for(EnumFacing side : EnumFacing.VALUES){
            TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata(), side.getIndex());
            int colorMultiplier = iconProvider.getColorMultiplier(stack.getMetadata(), side.getIndex());
            modelOverlay.setColour(colorMultiplier);
            modelOverlay.render(renderState, (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
        }

        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
