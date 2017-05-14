package keri.projectx.client.gui;

import keri.projectx.container.ContainerTank;
import keri.projectx.tile.TileEntityTankValve;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTank extends GuiContainer {

    private InventoryPlayer inventoryPlayer;
    private TileEntityTankValve tile;
    private ContainerTank container;

    public GuiTank(InventoryPlayer inventoryPlayer, TileEntityTankValve tile) {
        super(new ContainerTank(inventoryPlayer, tile));
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.container = (ContainerTank)this.inventorySlots;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

}
