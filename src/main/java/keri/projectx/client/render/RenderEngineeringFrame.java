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
import keri.ninetaillib.util.CommonUtils;
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

import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class RenderEngineeringFrame implements IBlockRenderingHandler {

    private static CCModel[] model;

    static{
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(0D, 0D, 0D, 16D, 1D, 1D),
                new Cuboid6(0D, 0D, 15D, 16D, 1D, 16D),
                new Cuboid6(0D, 15D, 15D, 16D, 16D, 16D),
                new Cuboid6(0D, 15D, 0D, 16D, 16D, 1D),
                new Cuboid6(0D, 1D, 0D, 1D, 15D, 1D),
                new Cuboid6(0D, 1D, 15D, 1D, 15D, 16D),
                new Cuboid6(15D, 1D, 15D, 16D, 15D, 16D),
                new Cuboid6(15D, 1D, 0D, 16D, 15D, 1D),
                new Cuboid6(0D, 0D, 1D, 1D, 1D, 15D),
                new Cuboid6(15D, 0D, 1D, 16D, 1D, 15D),
                new Cuboid6(15D, 15D, 1D, 16D, 16D, 15D),
                new Cuboid6(0D, 15D, 1D, 1D, 16D, 15D),
                new Cuboid6(1D, 1D, 1D, 15D, 15D, 15D)
        };

        model = new CCModel[bounds.length];
        IntStream.range(0, bounds.length).forEach(index -> model[index] = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds[index], 16D)).computeNormals());
    }

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        if(state instanceof IExtendedBlockState){
            IExtendedBlockState extendedState = (IExtendedBlockState)state;
            NBTTagCompound tag = (NBTTagCompound)extendedState.getValue(CommonProperties.NBT_TAG_PROPERTY);

            if(tag != null && tag.hasKey("multiblock_data")){
                if(!tag.getCompoundTag("multiblock_data").getBoolean("is_formed")){
                    IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
                    IIconBlock iconProvider = (IIconBlock)state.getBlock();
                    int meta = state.getBlock().getMetaFromState(state);
                    TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);
                    TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
                    int animationBrightness = handler.getAnimationBrightness(state, 0);
                    ColourRGBA animationColor = handler.getAnimationColor(state, 0);
                    int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 8 | (int)OpenGlHelper.lastBrightnessX;

                    for(int pass = 0; pass < 2; pass++){
                        renderState.reset();
                        renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

                        for(int part = 0; part < model.length - 1; part++){
                            model[part].render(renderState, new IconTransformation(textureBlock));
                        }

                        model[12].render(renderState, new IconTransformation(textureBlock));
                    }
                }
            }
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        int animationBrightness = handler.getAnimationBrightness(stack, 0);
        ColourRGBA animationColor = handler.getAnimationColor(stack, 0);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        if(this.hasDynamicItemRendering()){
            GlStateManager.disableBlend();
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < 2; pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

                for(int part = 0; part < model.length - 1; part++){
                    model[part].render(renderState, new IconTransformation(textureBlock));
                }

                model[12].render(renderState, new IconTransformation(textureBlock));
                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            GlStateManager.enableBlend();
        }
        else{
            for(int pass = 0; pass < 2; pass++){
                renderState.reset();

                for(int part = 0; part < model.length - 1; part++){
                    model[part].render(renderState, new IconTransformation(textureBlock));
                }

                model[12].render(renderState, new IconTransformation(textureBlock));
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
