package keri.projectx.client.gui;

import keri.projectx.container.ContainerHydrogenicSeperator;
import keri.projectx.tile.TileEntityHydrogenicSeperator;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiHydrogenicSeperator extends GuiContainer {

    private final ResourceLocation texture = new ResourceLocation(ModPrefs.MODID, "textures/gui/hydrogenic_seperator.png");

    public GuiHydrogenicSeperator(InventoryPlayer inventoryPlayer, TileEntityHydrogenicSeperator tile) {
        super(new ContainerHydrogenicSeperator(inventoryPlayer, tile));
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
