/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.tesr;

import codechicken.lib.colour.Colour;
import codechicken.lib.util.ClientUtils;
import keri.projectx.api.energy.EnumXynergyClass;
import keri.projectx.api.energy.EnumXynergyType;
import keri.projectx.client.render.RenderTruncatedIcosahedron;
import keri.projectx.tile.TileEntityXynergyNode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TESRXynergyNode extends TileEntitySpecialRenderer<TileEntityXynergyNode> {

    private static final RenderTruncatedIcosahedron ICOSA_RENDERER = RenderTruncatedIcosahedron.getInstance();

    @Override
    public void renderTileEntityAt(TileEntityXynergyNode tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);

        if(tile.getHasCore() && tile.getXynergyClass() != null && tile.getXynergyType() != null){
            EnumXynergyClass xynergyClass = tile.getXynergyClass();
            EnumXynergyType xynergyType = tile.getXynergyType();
            Colour colorCore = xynergyClass.getColor();
            Colour colorShell = xynergyType.getColor();
            GlStateManager.pushMatrix();

            if(tile.getEnergyStored() > 0){
                GlStateManager.rotate((float)ClientUtils.getRenderTime() * 4F, 0F, 1F, 0F);
            }

            ICOSA_RENDERER.render(0.62D, colorCore, colorCore.copy().multiplyC(0.75D), RenderTruncatedIcosahedron.EnumHedrontexture.FILL);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();

            if(tile.getEnergyStored() > 0){
                GlStateManager.rotate((float)-ClientUtils.getRenderTime() * 6F, 0F, 1F, 0F);
            }

            ICOSA_RENDERER.render(0.86D, colorShell, colorShell.copy().multiplyC(0.75D), RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }

}
