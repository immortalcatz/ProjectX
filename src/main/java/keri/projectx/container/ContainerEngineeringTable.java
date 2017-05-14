package keri.projectx.container;

import keri.ninetaillib.container.ContainerBase;
import keri.ninetaillib.slot.SlotBase;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerEngineeringTable extends ContainerBase {

    private InventoryPlayer inventoryPlayer;
    private TileEntityEngineeringTable tile;

    public ContainerEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.bindPlayerInventory(inventoryPlayer, 0, 103);
        //3 top hex-slots
        this.addSlotToContainer(new SlotBase(tile, 0, 61, 38));
        this.addSlotToContainer(new SlotBase(tile, 1, 80, 28));
        this.addSlotToContainer(new SlotBase(tile, 2, 99, 38));
        //center hex-slot
        this.addSlotToContainer(new SlotBase(tile, 4, 80, 47));
        //3 bottom hex-slots
        this.addSlotToContainer(new SlotBase(tile, 5, 61, 58));
        this.addSlotToContainer(new SlotBase(tile, 6, 80, 66));
        this.addSlotToContainer(new SlotBase(tile, 7, 99, 58));
        //left input slot
        this.addSlotToContainer(new SlotBase(tile, 8, 28, 47));
        //right output slot
        this.addSlotToContainer(new SlotBase(tile, 9, 132, 47));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUseableByPlayer(player);
    }

}
