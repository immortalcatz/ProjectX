package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.render.block.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.ProjectX;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTankValve implements IBlockRenderingHandler {

    private static CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        int meta = state.getBlock().getMetaFromState(state);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 8 | (int)OpenGlHelper.lastBrightnessX;

        if(state instanceof IExtendedBlockState){
            IExtendedBlockState extendedState = (IExtendedBlockState)state;
            NBTTagCompound tag = (NBTTagCompound)extendedState.getValue(CommonProperties.NBT_TAG_PROPERTY);

            if(tag != null && tag.hasKey("multiblock_data")){
                if(!tag.getCompoundTag("multiblock_data").getBoolean("is_formed")){
                    for(int pass = 0; pass < 2; pass++){
                        renderState.reset();

                        for(int side = 0; side < 6; side++){
                            TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, side);
                            TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, side);
                            int animationBrightness = handler.getAnimationBrightness(state, side);
                            ColourRGBA animationColor = handler.getAnimationColor(state, side);
                            ColourRGBA colorMultiplier = handler.getColorMultiplier(state, side);
                            renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                            model.setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                            model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
                        }
                    }
                }
                else{

                }
            }
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler) Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        if(this.hasDynamicItemRendering()){
            GlStateManager.disableBlend();
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < 2; pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);

                for(int side = 0; side < 6; side++){
                    TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, side);
                    TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, side);
                    int animationBrightness = handler.getAnimationBrightness(stack, side);
                    ColourRGBA animationColor = handler.getAnimationColor(stack, side);
                    ColourRGBA colorMultiplier = handler.getColorMultiplier(stack, side);
                    renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                    model.setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                    model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
                }

                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            GlStateManager.enableBlend();
        }
        else{
            for(int pass = 0; pass < 2; pass++){
                for(int side = 0; side < 6; side++){
                    TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, side);
                    TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, side);
                    ColourRGBA animationColor = handler.getAnimationColor(stack, side);
                    ColourRGBA colorMultiplier = handler.getColorMultiplier(stack, side);
                    model.setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                    model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
                }
            }
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IIconBlock block, int meta) {
        return block.getIcon(meta, 0);
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return !ProjectX.CONFIG.fastItemRendering;
    }

}