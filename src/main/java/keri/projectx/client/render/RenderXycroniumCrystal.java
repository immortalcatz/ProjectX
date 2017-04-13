package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCModelLibrary;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import keri.ninetaillib.render.item.IItemRenderingHandler;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;

@SideOnly(Side.CLIENT)
public class RenderXycroniumCrystal implements IItemRenderingHandler {

    private static CCModel model = CCModelLibrary.icosahedron7.copy().computeNormals();

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {

    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, TransformUtils.DEFAULT_ITEM.getTransforms(), cameraTransformType);
    }

    @Override
    public boolean useRenderCache() {
        return false;
    }

    @Override
    public boolean useStandardItemLighting() {
        return false;
    }

}
