package keri.projectx.container;

import keri.ninetaillib.lib.container.ContainerBase;
import keri.projectx.tile.TileEntityHydrogenicSeperator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerHydrogenicSeperator extends ContainerBase {

    private TileEntityHydrogenicSeperator tile;

    public ContainerHydrogenicSeperator(InventoryPlayer inventoryPlayer, TileEntityHydrogenicSeperator tile) {
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

    @Override
    public int getSizeInventory() {
        return this.tile.getSizeInventory();
    }

}
