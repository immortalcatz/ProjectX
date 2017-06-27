/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui;

import keri.ninetaillib.lib.gui.GuiContainerBase;
import keri.projectx.container.ContainerReinforcer;
import keri.projectx.tile.TileEntityReinforcer;
import keri.projectx.util.ModPrefs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiReinforcer extends GuiContainerBase {

    private final ResourceLocation texture = new ResourceLocation(ModPrefs.MODID, "textures/gui/reinforcer.png");
    private TileEntityReinforcer tile;

    public GuiReinforcer(InventoryPlayer inventoryPlayer, TileEntityReinforcer tile) {
        super(new ContainerReinforcer(inventoryPlayer, tile));
        this.tile = tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
