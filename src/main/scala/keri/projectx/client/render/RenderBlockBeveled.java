/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
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
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderBlockBeveled implements IBlockRenderingHandler {

    public static final RenderBlockBeveled INSTANCE = new RenderBlockBeveled();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(1D, 1D, 1D, 15D, 15D, 15D),
                new Cuboid6(0D, 0D, 0D, 2D, 16D, 2D),
                new Cuboid6(14D, 0D, 0D, 16D, 16D, 2D),
                new Cuboid6(14D, 0D, 14D, 16D, 16D, 16D),
                new Cuboid6(0D, 0D, 14D, 2D, 16D, 16D),
                new Cuboid6(2D, 0D, 0D, 14D, 2D, 2D),
                new Cuboid6(2D, 0D, 14D, 14D, 2D, 16D),
                new Cuboid6(2D, 14D, 14D, 14D, 16D, 16D),
                new Cuboid6(2D, 14D, 0D, 14D, 16D, 2D),
                new Cuboid6(0D, 0D, 2D, 2D, 2D, 14D),
                new Cuboid6(0D, 14D, 2D, 2D, 16D, 14D),
                new Cuboid6(14D, 14D, 2D, 16D, 16D, 14D),
                new Cuboid6(14D, 0D, 2D, 16D, 2D, 14D),
                new Cuboid6(5D, 5D, 0D, 11D, 11D, 1D),
                new Cuboid6(5D, 11D, 0D, 11D, 13D, 1D),
                new Cuboid6(5D, 3D, 0D, 11D, 5D, 1D),
                new Cuboid6(3D, 5D, 0D, 5D, 11D, 1D),
                new Cuboid6(11D, 5D, 0D, 13D, 11D, 1D),
                new Cuboid6(5D, 5D, 15D, 11D, 11D, 16D),
                new Cuboid6(5D, 11D, 15D, 11D, 13D, 16D),
                new Cuboid6(5D, 3D, 15D, 11D, 5D, 16D),
                new Cuboid6(3D, 5D, 15D, 5D, 11D, 16D),
                new Cuboid6(11D, 5D, 15D, 13D, 11D, 16D),
                new Cuboid6(0D, 5D, 5D, 1D, 11D, 11D),
                new Cuboid6(0D, 5D, 3D, 1D, 11D, 5D),
                new Cuboid6(0D, 5D, 11D, 1D, 11D, 13D),
                new Cuboid6(0D, 11D, 5D, 1D, 13D, 11D),
                new Cuboid6(0D, 3D, 5D, 1D, 5D, 11D),
                new Cuboid6(15D, 5D, 5D, 16D, 11D, 11D),
                new Cuboid6(15D, 5D, 3D, 16D, 11D, 5D),
                new Cuboid6(15D, 5D, 11D, 16D, 11D, 13D),
                new Cuboid6(15D, 11D, 5D, 16D, 13D, 11D),
                new Cuboid6(15D, 3D, 5D, 16D, 5D, 11D),
                new Cuboid6(5D, 0D, 5D, 11D, 1D, 11D),
                new Cuboid6(5D, 0D, 3D, 11D, 1D, 5D),
                new Cuboid6(5D, 0D, 11D, 11D, 1D, 13D),
                new Cuboid6(3D, 0D, 5D, 5D, 1D, 11D),
                new Cuboid6(11D, 0D, 5D, 13D, 1D, 11D),
                new Cuboid6(5D, 15D, 5D, 11D, 16D, 11D),
                new Cuboid6(3D, 15D, 5D, 5D, 16D, 11D),
                new Cuboid6(11D, 15D, 5D, 13D, 16D, 11D),
                new Cuboid6(5D, 15D, 3D, 11D, 16D, 5D),
                new Cuboid6(5D, 15D, 11D, 11D, 16D, 13D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
        ModelUtils.rotate(BLOCK_MODEL[14], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 11D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[15], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 5D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[16], 45D, new Vector3(0D, 1D, 0D), new Vector3(5D, 8D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[17], -45D, new Vector3(0D, 1D, 0D), new Vector3(11D, 8D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[19], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 11D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[20], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 5D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[21], -45D, new Vector3(0D, 1D, 0D), new Vector3(5D, 8D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[22], 45D, new Vector3(0D, 1D, 0D), new Vector3(11D, 8D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[24], -45D, new Vector3(0D, 1D, 0D), new Vector3(0D, 8D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[25], 45D, new Vector3(0D, 1D, 0D), new Vector3(0D, 8D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[26], -45D, new Vector3(0D, 0D, 1D), new Vector3(0D, 11D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[27], 45D, new Vector3(0D, 0D, 1D), new Vector3(0D, 5D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[29], 45D, new Vector3(0D, 1D, 0D), new Vector3(16D, 8D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[30], -45D, new Vector3(0D, 1D, 0D), new Vector3(16D, 8D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[31], 45D, new Vector3(0D, 0D, 1D), new Vector3(16D, 11D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[32], -45D, new Vector3(0D, 0D, 1D), new Vector3(16D, 5D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[34], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 0D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[35], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 0D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[36], -45D, new Vector3(0D, 0D, 1D), new Vector3(5D, 0D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[37], 45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 0D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[39], 45D, new Vector3(0D, 0D, 1D), new Vector3(5D, 16D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[40], -45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 16D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[41], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 16D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[42], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 16D, 11D));
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, BlockRenderLayer layer) {
        CCRenderState renderState = CCRenderState.instance();
        IAnimationHandler animationHandler = (IAnimationHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        renderState.bind(buffer);

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelAnimation = BLOCK_MODEL[part].copy();
            modelAnimation.apply(new Translation(Vector3.fromBlockPos(pos)));

            for (int side = 0; side < 6; side++) {
                TextureAtlasSprite texture = animationHandler.getAnimationIcon(world, pos, side);
                int color = animationHandler.getAnimationColor(world, pos, side);
                int brightness = animationHandler.getAnimationBrightness(world, pos, side);
                renderState.brightness = brightness;
                modelAnimation.setColour(color);
                modelAnimation.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
            }
        }

        List<BakedQuad> overlayQuads = Lists.newArrayList();

        if (layer == BlockRenderLayer.CUTOUT_MIPPED) {
            for(int part = 0; part < BLOCK_MODEL.length; part++){
                CCModel modelOverlay = BLOCK_MODEL[part].copy();
                BakingVertexBuffer parent = BakingVertexBuffer.create();
                parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                renderState.reset();
                renderState.bind(parent);

                for (EnumFacing side : EnumFacing.VALUES) {
                    TextureAtlasSprite texture = null;

                    if (iconProvider.getIcon(world, pos, side) != null) {
                        texture = iconProvider.getIcon(world, pos, side);
                    } else {
                        texture = iconProvider.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), side);
                    }

                    int colorMultiplier = iconProvider.getColorMultiplier(BlockAccessUtils.getBlockMetadata(world, pos), side);
                    modelOverlay.setColour(colorMultiplier);
                    modelOverlay.render(renderState, 4 * side.getIndex(), 4 + (4 * side.getIndex()), new IconTransformation(texture));
                }

                parent.finishDrawing();
                overlayQuads.addAll(parent.bake());
            }
        }

        return RenderUtils.renderQuads(buffer, world, pos, state, overlayQuads);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        Arrays.stream(BLOCK_MODEL).forEach(mp -> mp.copy().apply(new Translation(Vector3.fromBlockPos(pos))).render(renderState, new IconTransformation(texture)));
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int) OpenGlHelper.lastBrightnessX;
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = CCRenderState.instance();
        IIconBlock iconProvider = (IIconBlock) Block.getBlockFromItem(stack.getItem());
        IAnimationHandler animationHandler = (IAnimationHandler) Block.getBlockFromItem(stack.getItem());
        renderState.bind(buffer);

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelAnimation = BLOCK_MODEL[part].copy();

            for (int side = 0; side < 6; side++) {
                TextureAtlasSprite texture = animationHandler.getAnimationIcon(stack, side);
                int color = animationHandler.getAnimationColor(stack, side);
                int brightness = animationHandler.getAnimationBrightness(stack, side);
                renderState.brightness = brightness;
                modelAnimation.setColour(color);
                modelAnimation.render(renderState, 4 * side, 4 + (4 * side), new IconTransformation(texture));
            }
        }

        Tessellator.getInstance().draw();
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = lastBrightness;

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelOverlay = BLOCK_MODEL[part].copy();

            for (EnumFacing side : EnumFacing.VALUES) {
                TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata(), side);
                int colorMultiplier = iconProvider.getColorMultiplier(stack.getMetadata(), side);
                modelOverlay.setColour(colorMultiplier);
                modelOverlay.render(renderState, 4 * side.getIndex(), 4 + (4 * side.getIndex()), new IconTransformation(texture));
            }
        }

        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
