package keri.projectx.client.renderold;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.property.EnumDyeColor;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.ProjectX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;
import java.util.stream.IntStream;

//TODO Convert to IMultipassModel...
@SideOnly(Side.CLIENT)
public class RenderColorAnalyzer implements IItemRenderingHandler {

    private static CCModel[] model;

    static{
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(4D, 0D, 0D, 12D, 12D, 2D),
                new Cuboid6(4D, 4D, 2D, 5D, 12D, 3D),
                new Cuboid6(11D, 4D, 2D, 12D, 12D, 3D),
                new Cuboid6(5D, 11D, 2D, 11D, 12D, 3D),
                new Cuboid6(5D, 4D, 2D, 11D, 5D, 3D),
                new Cuboid6(5D, 5D, 2D, 11D, 11D, 2.5D),
                new Cuboid6(4D, 0D, 2D, 5D, 4D, 3D),
                new Cuboid6(11D, 0D, 2D, 12D, 4D, 3D),
                new Cuboid6(5D, 0D, 2D, 11D, 1D, 3D),
                new Cuboid6(5.5D, 1.75D, 2D, 6.5D, 3.25D, 2.5D),
                new Cuboid6(7.5D, 1.75D, 2D, 8.5D, 3.25D, 2.5D),
                new Cuboid6(9.5D, 1.75D, 2D, 10.5D, 3.25D, 2.5D),
                new Cuboid6(5D, 12D, 0.75D, 6.5D, 13D, 2.25D)
        };

        model = new CCModel[bounds.length];
        IntStream.range(0, bounds.length).forEach(index -> model[index] = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds[index], 16D)).computeNormals());
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand){
        IIconItem iconProvider = (IIconItem)stack.getItem();
        TextureAtlasSprite textureBase = iconProvider.getIcon(0);
        TextureAtlasSprite textureFrame = iconProvider.getIcon(1);
        TextureAtlasSprite textureButton = iconProvider.getIcon(2);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        Transformation translation = new Translation(new Vector3(0D, 0.1D, 0D));

        if(!this.useRenderCache()){
            GlStateManager.disableBlend();
            Tessellator.getInstance().draw();

            for(int pass = 0; pass < 2; pass++){
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                renderState.reset();
                renderState.bind(buffer);
                renderState.brightness = pass == 0 ? 0x00F000F0 : lastBrightness;

                for(int part = 0; part < model.length; part++){
                    model[part].apply(translation);
                }

                if(pass == 0){
                    //TODO actually make the color of this thing change...
                    model[5].setColour(EnumDyeColor.ORANGE.getColor().rgba());
                    model[5].render(renderState, new IconTransformation(ProjectX.PROXY.getAnimationIcon()));
                    model[12].setColour(EnumDyeColor.ORANGE.getColor().rgba());
                    model[12].render(renderState, new IconTransformation(ProjectX.PROXY.getAnimationIcon()));
                }
                else{
                    model[0].render(renderState, new IconTransformation(textureBase));
                    model[1].render(renderState, new IconTransformation(textureFrame));
                    model[2].render(renderState, new IconTransformation(textureFrame));
                    model[3].render(renderState, new IconTransformation(textureFrame));
                    model[4].render(renderState, new IconTransformation(textureFrame));
                    model[6].render(renderState, new IconTransformation(textureFrame));
                    model[7].render(renderState, new IconTransformation(textureFrame));
                    model[8].render(renderState, new IconTransformation(textureFrame));
                    model[9].setColour(EnumDyeColor.RED.getColor().rgba());
                    model[9].render(renderState, new IconTransformation(textureButton));
                    model[10].setColour(EnumDyeColor.GREEN.getColor().rgba());
                    model[10].render(renderState, new IconTransformation(textureButton));
                    model[11].setColour(EnumDyeColor.BLUE.getColor().rgba());
                    model[11].render(renderState, new IconTransformation(textureButton));
                }

                for(int part = 0; part < model.length; part++){
                    model[part].apply(translation.inverse());
                }

                Tessellator.getInstance().draw();
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            GlStateManager.enableBlend();
        }
        else{
            for(int pass = 0; pass < 2; pass++){
                for(int part = 0; part < model.length; part++){
                    model[part].apply(translation);
                }

                if(pass == 0){
                    //TODO actually make the color of this thing change...
                    model[5].setColour(EnumDyeColor.ORANGE.getColor().rgba());
                    model[5].render(renderState, new IconTransformation(ProjectX.PROXY.getAnimationIcon()));
                    model[12].setColour(EnumDyeColor.ORANGE.getColor().rgba());
                    model[12].render(renderState, new IconTransformation(ProjectX.PROXY.getAnimationIcon()));
                }
                else{
                    model[0].render(renderState, new IconTransformation(textureBase));
                    model[1].render(renderState, new IconTransformation(textureFrame));
                    model[2].render(renderState, new IconTransformation(textureFrame));
                    model[3].render(renderState, new IconTransformation(textureFrame));
                    model[4].render(renderState, new IconTransformation(textureFrame));
                    model[6].render(renderState, new IconTransformation(textureFrame));
                    model[7].render(renderState, new IconTransformation(textureFrame));
                    model[8].render(renderState, new IconTransformation(textureFrame));
                    model[9].setColour(EnumDyeColor.RED.getColor().rgba());
                    model[9].render(renderState, new IconTransformation(textureButton));
                    model[10].setColour(EnumDyeColor.GREEN.getColor().rgba());
                    model[10].render(renderState, new IconTransformation(textureButton));
                    model[11].setColour(EnumDyeColor.BLUE.getColor().rgba());
                    model[11].render(renderState, new IconTransformation(textureButton));
                }

                for(int part = 0; part < model.length; part++){
                    model[part].apply(translation.inverse());
                }
            }
        }
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType){
        //TODO fix transformations...
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, TransformUtils.DEFAULT_ITEM.getTransforms(), cameraTransformType);
    }

    @Override
    public boolean useRenderCache() {
        return ProjectX.CONFIG.fastItemRendering.getValue();
    }

    @Override
    public boolean useStandardItemLighting() {
        return true;
    }

    @Override
    public String getItemKey(ItemStack stack) {
        if(stack.getTagCompound() != null){
            NBTTagCompound tag = stack.getTagCompound();
            StringBuilder builder = new StringBuilder();
            builder.append(tag.getInteger("color_r"));
            builder.append(':');
            builder.append(tag.getInteger("color_g"));
            builder.append(':');
            builder.append(tag.getInteger("color_b"));
            return builder.toString();
        }

        return null;
    }

}
