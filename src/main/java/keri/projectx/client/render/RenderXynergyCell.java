package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.IItemRenderingHandler;
import keri.ninetaillib.render.VertexUtils;
import keri.ninetaillib.texture.IIconItem;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.ProjectX;
import keri.projectx.property.EnumXycroniumColor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;
import java.util.stream.IntStream;

public class RenderXynergyCell implements IItemRenderingHandler {

    private static CCModel[] model;

    static{
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(4D, 0D, 4D, 12D, 1D, 12D),
                new Cuboid6(4D, 13D, 4D, 12D, 14D, 12D),
                new Cuboid6(5D, 1D, 5D, 6D, 13D, 6D),
                new Cuboid6(10D, 1D, 5D, 11D, 13D, 6D),
                new Cuboid6(10D, 1D, 10D, 11D, 13D, 11D),
                new Cuboid6(5D, 1D, 10D, 6D, 13D, 11D),
                new Cuboid6(6D, 6.5D, 5.5D, 10D, 7.5D, 6D),
                new Cuboid6(6D, 6.5D, 10D, 10D, 7.5D, 10.5D),
                new Cuboid6(10D, 6.5D, 6D, 10.5D, 7.5D, 10D),
                new Cuboid6(5.5D, 6.5D, 6D, 6D, 7.5D, 10D),
                new Cuboid6(6D, 1D, 6D, 10D, 13D, 10D)
        };

        model = new CCModel[bounds.length];
        IntStream.range(0, bounds.length).forEach(index -> model[index] = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds[index], 16D)).computeNormals());
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        IIconItem iconProvider = ((IIconItem)stack.getItem());

        if(this.useRenderCache()){
            model[10].setColour(EnumXycroniumColor.values()[stack.getMetadata()].getColor().rgba());
            model[10].render(renderState, new IconTransformation(iconProvider.getIcon(2)));

            IntStream.range(0, 6).forEach(index -> {
                model[index].setColour(new ColourRGBA(255, 255, 255, 255).rgba());
                model[index].render(renderState, new IconTransformation(iconProvider.getIcon(0)));
            });

            IntStream.range(6, 10).forEach(index -> {
                model[index].setColour(new ColourRGBA(255, 255, 255, 255).rgba());
                model[index].render(renderState, new IconTransformation(iconProvider.getIcon(1)));
            });
        }
        else{
            Tessellator.getInstance().draw();
            int prevBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

            IntStream.range(0, 2).forEach(pass -> {
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                CCRenderState state = CCRenderState.instance();
                state.reset();
                state.bind(buffer);

                if(pass == 0){
                    state.brightness = 0x00F000F0;
                    state.pushLightmap();
                    model[10].setColour(EnumXycroniumColor.values()[stack.getMetadata()].getColor().rgba());
                    model[10].render(state, new IconTransformation(iconProvider.getIcon(2)));
                    state.reset();
                }
                else{
                    state.brightness = prevBrightness;
                    state.pushLightmap();

                    IntStream.range(0, 6).forEach(index -> {
                        model[index].setColour(new ColourRGBA(255, 255, 255, 255).rgba());
                        model[index].render(state, new IconTransformation(iconProvider.getIcon(0)));
                    });

                    IntStream.range(6, 10).forEach(index -> {
                        model[index].setColour(new ColourRGBA(255, 255, 255, 255).rgba());
                        model[index].render(state, new IconTransformation(iconProvider.getIcon(1)));
                    });

                    state.reset();
                }

                Tessellator.getInstance().draw();
            });

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, TransformUtils.DEFAULT_BLOCK.getTransforms(), cameraTransformType);
    }

    @Override
    public boolean useRenderCache() {
        return ProjectX.CONFIG.useFastItemRendering;
    }

    @Override
    public boolean useStandardItemLighting() {
        return true;
    }

}
