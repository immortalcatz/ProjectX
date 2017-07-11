/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
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
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.client.render.connected.BlockRenderContext;
import keri.projectx.client.render.connected.ICTMBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

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
        CCRenderState renderState = CCRenderState.instance();
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        IAnimationHandler animationHandler = (IAnimationHandler)world.getBlockState(pos).getBlock();
        CCModel modelAnimation = BLOCK_MODEL.copy();
        modelAnimation.apply(new Translation(Vector3.fromBlockPos(pos)));
        renderState.bind(buffer);

        for(int side = 0; side < 6; side++){
            TextureAtlasSprite texture = animationHandler.getAnimationIcon(world, pos, side) ;
            int color = animationHandler.getAnimationColor(world, pos, side);
            int brightness = animationHandler.getAnimationBrightness(world, pos, side);
            renderState.brightness = brightness;
            modelAnimation.setColour(color);
            modelAnimation.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
        }

        List<BakedQuad> overlayQuads = Lists.newArrayList();

        if(layer == BlockRenderLayer.CUTOUT_MIPPED){
            if(world.getBlockState(pos).getBlock() instanceof ICTMBlock){
                TextureAtlasSprite texture = null;

                if(iconProvider.getIcon(world, pos, 0) != null){
                    texture = iconProvider.getIcon(world, pos, 0);
                }
                else{
                    texture = iconProvider.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), 0);
                }

                BlockRenderContext renderContext = new BlockRenderContext();
                renderContext.setBlockAccess(world);
                renderContext.setCurrentBlockState(world.getBlockState(pos));
                renderContext.setChangeBounds(true);
                renderContext.renderStandardBlock(pos, texture);
                BakingVertexBuffer parent = BakingVertexBuffer.create();
                parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                renderState.reset();
                renderState.bind(parent);
                renderContext.getModel().render(renderState);
                parent.finishDrawing();
                overlayQuads.addAll(parent.bake());
            }
            else{
                CCModel modelOverlay = BLOCK_MODEL.copy();
                BakingVertexBuffer parent = BakingVertexBuffer.create();
                parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                renderState.reset();
                renderState.bind(parent);

                for(int side = 0; side < 6; side++){
                    TextureAtlasSprite texture = null;

                    if(iconProvider.getIcon(world, pos, side) != null){
                        texture = iconProvider.getIcon(world, pos, side);
                    }
                    else{
                        texture = iconProvider.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), side);
                    }

                    int colorMultiplier = iconProvider.getColorMultiplier(BlockAccessUtils.getBlockMetadata(world, pos), side);
                    modelOverlay.setColour(colorMultiplier);
                    modelOverlay.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
                }

                parent.finishDrawing();
                overlayQuads.addAll(parent.bake());
            }
        }

        return RenderUtils.renderQuads(buffer, world, pos, overlayQuads);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCModel model = BLOCK_MODEL.copy();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        CCRenderState renderState = CCRenderState.instance();
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
        CCRenderState renderState = CCRenderState.instance();
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        IAnimationHandler animationHandler = (IAnimationHandler)Block.getBlockFromItem(stack.getItem());
        renderState.bind(buffer);
        CCModel modelAnimation = BLOCK_MODEL.copy();

        for(int side = 0; side < 6; side++){
            TextureAtlasSprite texture = animationHandler.getAnimationIcon(stack, side);
            int color = animationHandler.getAnimationColor(stack, side);
            int brightness = animationHandler.getAnimationBrightness(stack, side);
            renderState.brightness = brightness;
            modelAnimation.setColour(color);
            modelAnimation.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
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

        for(int side = 0; side < 6; side++){
            TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata(), side);
            int colorMultiplier = iconProvider.getColorMultiplier(stack.getMetadata(), side);
            modelOverlay.setColour(colorMultiplier);
            modelOverlay.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
        }

        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
