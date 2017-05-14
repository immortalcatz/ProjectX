package keri.projectx.container;

import keri.ninetaillib.container.ContainerBase;
import keri.projectx.tile.TileEntityTankValve;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTank extends ContainerBase {

    private InventoryPlayer inventoryPlayer;
    private TileEntityTankValve tile;

    public ContainerTank(InventoryPlayer inventoryPlayer, TileEntityTankValve tile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUseableByPlayer(player);
    }

}
