package keri.projectx.container;

import keri.ninetaillib.container.ContainerBase;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerEngineeringTable extends ContainerBase {

    private InventoryPlayer inventoryPlayer;
    private TileEntityEngineeringTable tile;

    public ContainerEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUseableByPlayer(player);
    }

}
