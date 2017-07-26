/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.gui.machine;

import keri.ninetaillib.lib.gui.GuiContainerBase;
import keri.projectx.container.ContainerEngineeringTable;
import keri.projectx.tile.TileEntityEngineeringTable;
import keri.projectx.util.ModPrefs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEngineeringTable extends GuiContainerBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModPrefs.MODID, "textures/gui/engineering_table.png");

    public GuiEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        super(new ContainerEngineeringTable(inventoryPlayer, tile));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
