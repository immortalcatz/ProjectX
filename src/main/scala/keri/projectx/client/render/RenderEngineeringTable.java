package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.util.ClientUtils;
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

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderEngineeringTable implements IBlockRenderingHandler {

    public static final RenderEngineeringTable INSTANCE = new RenderEngineeringTable();
    private static final RenderTruncatedIcosahedron ICOSA_RENDERER = RenderTruncatedIcosahedron.getInstance();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(0D, 0D, 0D, 16D, 8D, 16D),
                new Cuboid6(0D, 15D, 0D, 16D, 16D, 16D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, BlockRenderLayer layer) {
        CCRenderState renderState = CCRenderState.instance();
        IAnimationHandler animationHandler = (IAnimationHandler)state.getBlock();
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
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        BakingVertexBuffer parent = BakingVertexBuffer.create();
        parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(parent);

        if (layer == BlockRenderLayer.CUTOUT_MIPPED) {
            for(int part = 0; part < BLOCK_MODEL.length; part++){
                CCModel modelOverlay = BLOCK_MODEL[part].copy();

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
            }
        }

        parent.finishDrawing();
        return RenderUtils.renderQuads(buffer, world, pos, state, parent.bake());
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
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, 0.5D, 0.5D);
        GlStateManager.rotate((float)ClientUtils.getRenderTime() * 4F, 0F, 1F, 0F);
        Color colorHsb = Color.getHSBColor((float)ClientUtils.getRenderTime() / 120F, 1F, 1F);
        Colour colorHex = new ColourRGBA(colorHsb.getRed(), colorHsb.getGreen(), colorHsb.getBlue(), colorHsb.getAlpha());
        Colour colorPent = colorHex.copy().multiplyC(0.75D);
        ICOSA_RENDERER.render(0.92D, colorPent, colorHex, RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
