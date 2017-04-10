package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.block.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSimpleGlow implements IBlockRenderingHandler {

    private static CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        int meta = state.getBlock().getMetaFromState(state);
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        for(int pass = 0; pass < 2; pass++){
            renderState.reset();

            for(int side = 0; side < 6; side++){
                TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, side);
                TextureAtlasSprite textureAnimation = handler.getAnimationIcon(meta, side);
                int animationBrightness = handler.getAnimationBrightness(meta, side);
                ColourRGBA animationColor = handler.getAnimationColor(meta, side);
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                renderState.pushLightmap();
                model.setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
            }

            renderState.reset();
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        Tessellator.getInstance().draw();
        IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        for(int pass = 0; pass < 2; pass++){
            VertexBuffer buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            CCRenderState ccrs = CCRenderState.instance();
            ccrs.reset();
            ccrs.bind(buffer);

            for(int side = 0; side < 6; side++){
                TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, side);
                TextureAtlasSprite textureAnimation = handler.getAnimationIcon(meta, side);
                int animationBrightness = handler.getAnimationBrightness(meta, side);
                ColourRGBA animationColor = handler.getAnimationColor(meta, side);
                ccrs.brightness = pass == 0 ? animationBrightness : lastBrightness;
                ccrs.pushLightmap();
                model.setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                model.render(ccrs, 0 + (4 * side), 4 + (4 * side), new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
            }

            Tessellator.getInstance().draw();
        }

        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IIconBlock block, int meta) {
        return block.getIcon(meta, 0);
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return true;
    }

}
