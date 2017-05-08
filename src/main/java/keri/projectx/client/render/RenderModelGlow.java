package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.fms.FMSModel;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.ProjectX;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderModelGlow implements IBlockRenderingHandler {

    private CCModel[] model;
    private TextureAtlasSprite texture;

    public RenderModelGlow(FMSModel model){
        this.model = model.getModel();
    }

    @Override
    public List<BakedQuad> renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        this.texture = iconProvider.getIcon(0, 0);
        int meta = state.getBlock().getMetaFromState(state);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 8 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);
        int animationBrightness = handler.getAnimationBrightness(state, 0);
        ColourRGBA animationColor = handler.getAnimationColor(state, 0);
        ColourRGBA colorMultiplier = handler.getColorMultiplier(state, 0);

        for(int pass = 0; pass < 2; pass++){
            renderState.reset();

            for(int part = 0; part < model.length; part++){
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                model[part].setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                model[part].render(renderState, new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
            }
        }

        return null;
    }

    @Override
    public List<BakedQuad> renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler) Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);
        int animationBrightness = handler.getAnimationBrightness(stack, 0);
        ColourRGBA animationColor = handler.getAnimationColor(stack, 0);
        ColourRGBA colorMultiplier = handler.getColorMultiplier(stack, 0);

        if(this.hasDynamicItemRendering()){
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < 2; pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

                for(int part = 0; part < model.length; part++){
                    model[part].setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                    model[part].render(renderState, new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
                }

                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }
        else{
            for(int pass = 0; pass < 2; pass++){
                renderState.reset();

                for(int part = 0; part < model.length; part++){
                    model[part].setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                    model[part].render(renderState, new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
                }
            }
        }

        return null;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return !ProjectX.CONFIG.fastItemRendering.getValue();
    }

}
