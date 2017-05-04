package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.ProjectX;
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
public class RenderMultipassModel implements IBlockRenderingHandler {

    private IMultipassModel mpModel;

    public RenderMultipassModel(IMultipassModel model){
        this.mpModel = model;
    }

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        IAnimationSideHandler animationHandler = (IAnimationSideHandler) state.getBlock();
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 8 | (int)OpenGlHelper.lastBrightnessX;
        RenderData preData = new RenderData(state, null, iconProvider, animationHandler, 0, 0, lastBrightness, false);
        CCModel[] model = this.mpModel.getModel(preData, EnumRenderType.BLOCK);

        for(int pass = 0; pass < this.mpModel.getPassCount(preData, EnumRenderType.BLOCK); pass++){
            renderState.reset();

            for(int part = 0; part < model.length; part++){
                RenderData data = new RenderData(state, null, iconProvider, animationHandler, pass, part, lastBrightness, true);
                TextureAtlasSprite texture = this.mpModel.getModelTexture(data, EnumRenderType.BLOCK);
                Colour color = this.mpModel.getColorMultiplier(data, EnumRenderType.BLOCK);
                renderState.brightness = this.mpModel.getBrightness(data, EnumRenderType.BLOCK);
                model[part].setColour(color.rgba());
                model[part].render(renderState, new IconTransformation(texture));
            }
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        IAnimationSideHandler animationHandler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        RenderData preData = new RenderData(null, stack, iconProvider, animationHandler, 0, 0, lastBrightness, false);
        CCModel[] model = this.mpModel.getModel(preData, EnumRenderType.ITEM);

        if(!this.hasDynamicItemRendering()){
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < this.mpModel.getPassCount(preData, EnumRenderType.ITEM); pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);

                for(int part = 0; part < model.length; part++){
                    RenderData data = new RenderData(null, stack, iconProvider, animationHandler, pass, part, lastBrightness, true);
                    TextureAtlasSprite texture = this.mpModel.getModelTexture(data, EnumRenderType.ITEM);
                    Colour color = this.mpModel.getColorMultiplier(data, EnumRenderType.ITEM);
                    renderState.brightness = this.mpModel.getBrightness(data, EnumRenderType.ITEM);
                    model[part].setColour(color.rgba());
                    model[part].render(renderState, new IconTransformation(texture));
                }

                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }
        else{
            for(int pass = 0; pass < this.mpModel.getPassCount(preData, EnumRenderType.ITEM); pass++){
                for(int part = 0; part < model.length; part++){
                    RenderData data = new RenderData(null, stack, iconProvider, animationHandler, pass, part, lastBrightness, true);
                    TextureAtlasSprite texture = this.mpModel.getModelTexture(data, EnumRenderType.ITEM);
                    Colour color = this.mpModel.getColorMultiplier(data, EnumRenderType.ITEM);
                    model[part].setColour(color.rgba());
                    model[part].render(renderState, new IconTransformation(texture));
                }
            }
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture(){
        return this.mpModel.getParticleTexture();
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return !ProjectX.CONFIG.fastItemRendering.getValue();
    }

}
