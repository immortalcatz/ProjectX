package keri.projectx.client.gui;

import keri.projectx.container.ContainerEngineeringTable;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEngineeringTable extends GuiContainer {

    private InventoryPlayer inventoryPlayer;
    private TileEntityEngineeringTable tile;
    private ContainerEngineeringTable container;

    public GuiEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        super(new ContainerEngineeringTable(inventoryPlayer, tile));
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.container = (ContainerEngineeringTable)this.inventorySlots;
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
