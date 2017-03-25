package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.ninetaillib.render.VertexUtils;
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
public class RenderSimpleGlow implements IBlockRenderingHandler {

    private static CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        if(state.getBlock() instanceof IAnimationSideHandler){
            IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
            IIconBlock iconProvider = (IIconBlock)state.getBlock();
            int meta = state.getBlock().getMetaFromState(state);
            int prevBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

            for(int pass = 0; pass < 2; pass++){
                for(int side = 0; side < 6; side++){
                    renderState.reset();
                    renderState.brightness = pass == 0 ? handler.getAnimationBrightness(meta, side) : prevBrightness;
                    renderState.pushLightmap();
                    ColourRGBA color = pass == 0 ? handler.getAnimationColor(meta, side) : new ColourRGBA(255, 255, 255, 255);
                    TextureAtlasSprite texture = pass == 0 ? handler.getAnimationIcon(meta, side) : iconProvider.getIcon(meta, side);
                    model.setColour(color.rgba());
                    model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(texture));
                }
            }
        }
        else{
            throw new IllegalArgumentException("Block must be an instance of IAnimationSideHandler !");
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        if(Block.getBlockFromItem(stack.getItem()) instanceof IAnimationSideHandler){
            IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
            IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
            int meta = stack.getMetadata();

            if(this.hasDynamicItemRendering()){
                Tessellator.getInstance().draw();
                int prevBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

                for(int pass = 0; pass < 2; pass++){
                    for(int side = 0; side < 6; side++){
                        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                        buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                        CCRenderState state = CCRenderState.instance();
                        state.reset();
                        state.bind(buffer);
                        state.brightness = pass == 0 ? handler.getAnimationBrightness(meta, side) : prevBrightness;
                        state.pushLightmap();
                        ColourRGBA color = pass == 0 ? handler.getAnimationColor(meta, side) : new ColourRGBA(255, 255, 255, 255);
                        TextureAtlasSprite texture = pass == 0 ? handler.getAnimationIcon(meta, side) : iconProvider.getIcon(meta, side);
                        model.setColour(color.rgba());
                        model.render(state, 0 + (4 * side), 4 + (4 * side), new IconTransformation(texture));
                        Tessellator.getInstance().draw();
                    }
                }

                Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            }
            else{
                for(int side = 0; side < 6; side++){
                    model.setColour(handler.getAnimationColor(meta, side).rgba());
                    model.render(renderState, new IconTransformation(handler.getAnimationIcon(meta, side)));
                    model.setColour(new ColourRGBA(255, 255, 255, 255).rgba());
                    model.render(renderState, new IconTransformation(iconProvider.getIcon(meta, side)));
                }
            }
        }
        else{
            throw new IllegalArgumentException("Block must be an instance of IAnimationSideHandler !");
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IIconBlock block, int meta) {
        return block.getIcon(meta, 0);
    }

    @Override
    public boolean hasDynamicItemRendering(){
        return !ProjectX.CONFIG.useFastItemRendering;
    }

}
