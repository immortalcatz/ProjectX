package keri.projectx.client.render.tesr;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ClientUtils;
import keri.projectx.client.render.RenderTruncatedIcosahedron;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class TESREngineeringTable extends TileEntitySpecialRenderer<TileEntityEngineeringTable> {

    private static final RenderTruncatedIcosahedron ICOSA_RENDERER = RenderTruncatedIcosahedron.getInstance();

    @Override
    public void renderTileEntityAt(TileEntityEngineeringTable tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
        GlStateManager.rotate((float) ClientUtils.getRenderTime() * 4F, 0F, 1F, 0F);
        Color colorHsb = Color.getHSBColor((float)ClientUtils.getRenderTime() / 120F, 1F, 1F);
        Colour colorHex = new ColourRGBA(colorHsb.getRed(), colorHsb.getGreen(), colorHsb.getBlue(), colorHsb.getAlpha());
        Colour colorPent = colorHex.copy().multiplyC(0.75D);
        ICOSA_RENDERER.render(0.92D, colorPent, colorHex, RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
        GlStateManager.popMatrix();
    }

}
