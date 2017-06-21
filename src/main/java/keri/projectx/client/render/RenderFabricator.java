package keri.projectx.client.render;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.render.item.IItemRenderer;
import keri.projectx.tile.TileEntityFabricator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RenderFabricator extends TileEntitySpecialRenderer<TileEntityFabricator> {

    public static final RenderFabricator INSTANCE = new RenderFabricator();

    @Override
    public void renderTileEntityAt(TileEntityFabricator tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.85D, z + 0.5D);
        double spacing = 0.1875D;

        for(int i = 0; i < 9; i++){
            ItemStack stack = tile.getStackInSlot(i).copy();
            stack.setCount(1);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
            double offset = model != null && model instanceof IItemRenderer ? -0.0925D : 0D;
            int offsetX = i / 3;
            int offsetZ = i % 3;
            double translateX = -spacing + offsetX * spacing;
            double translateZ = -spacing + offsetZ * spacing;
            GlStateManager.translate(translateX, offset, translateZ);
            GlStateManager.scale(0.5D, 0.5D, 0.5D);

            if(!stack.isEmpty()){
                RenderUtils.renderItemUniform(stack);
            }

            GlStateManager.scale(1D / 0.5D, 1D / 0.5D, 1D / 0.5D);
            GlStateManager.translate(-translateX, -offset, -translateZ);
        }

        GlStateManager.popMatrix();
    }

}
