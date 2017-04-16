package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Vector3;
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
public class RenderXycroniumLadder implements IBlockRenderingHandler {

    private static CCModel[] model;

    static{
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(0D, 0D, 0D, 2D, 16D, 2D),
                new Cuboid6(14D, 0D, 0D, 16D, 16D, 2D),
                new Cuboid6(2D, 1.5D, 0.5D, 16D, 2.5D, 1.5D),
                new Cuboid6(2D, 5.5D, 0.5D, 16D, 6.5D, 1.5D),
                new Cuboid6(2D, 9.5D, 0.5D, 16D, 10.5D, 1.5D),
                new Cuboid6(2D, 13.5D, 0.5D, 16D, 14.5D, 1.5D)
        };

        model = new CCModel[bounds.length];
        IntStream.range(0, bounds.length).forEach(index -> model[index] = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds[index], 16D)).computeNormals());
    }

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        int meta = state.getBlock().getMetaFromState(state);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 8 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        int animationBrightness = handler.getAnimationBrightness(state, 0);
        ColourRGBA animationColor = handler.getAnimationColor(state, 0);

        for(int pass = 0; pass < 2; pass++){
            renderState.reset();

            for(int part = 0; part < model.length; part++){
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                model[part].setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                model[part].render(renderState, pass == 0 ? new IconTransformation(textureAnimation) : new IconTransformation(textureBlock));
            }
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        int animationBrightness = handler.getAnimationBrightness(stack, 0);
        ColourRGBA animationColor = handler.getAnimationColor(stack, 0);
        Transformation rotation = new Rotation(180D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center);

        if(this.hasDynamicItemRendering()){
            GlStateManager.disableBlend();
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < 2; pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

                for(int part = 0; part < model.length; part++){
                    model[part].apply(rotation);
                    model[part].setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                    model[part].render(renderState, pass == 0 ? new IconTransformation(textureAnimation) : new IconTransformation(textureBlock));
                    model[part].apply(rotation.inverse());
                }

                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            GlStateManager.enableBlend();
        }
        else{
            for(int pass = 0; pass < 2; pass++){
                for(int part = 0; part < model.length; part++){
                    model[part].apply(rotation);
                    model[part].setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                    model[part].render(renderState, pass == 0 ? new IconTransformation(textureAnimation) : new IconTransformation(textureBlock));
                    model[part].apply(rotation.inverse());
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

    @Override
    public String getBlockKey(IExtendedBlockState state){
        return Integer.toString(((NBTTagCompound)state.getValue(CommonProperties.NBT_TAG_PROPERTY)).getInteger("orientation"));
    }

    @Override
    public EnumFacing getRotation(IBlockState state) {
        if(state != null && state instanceof IExtendedBlockState){
            IExtendedBlockState extendedState = (IExtendedBlockState)state;
            NBTTagCompound tag = (NBTTagCompound)extendedState.getValue(CommonProperties.NBT_TAG_PROPERTY);

            if(tag != null){
                return EnumFacing.getFront(tag.getInteger("orientation"));
            }
        }

        return EnumFacing.NORTH;
    }

}
