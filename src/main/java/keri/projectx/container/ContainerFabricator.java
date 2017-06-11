package keri.projectx.container;

import keri.projectx.tile.TileEntityFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFabricator extends ContainerBase {

    private InventoryPlayer inventoryPlayer;
    private TileEntityFabricator tile;

    public ContainerFabricator(InventoryPlayer inventoryPlayer, TileEntityFabricator tile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.addPlayerInventory(inventoryPlayer, 8, 84);

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                int index = x * 3 + y;
                int posX = 16 + 18 * x;
                int posY = 17 + 18 * y;
                this.addSlotToContainer(new Slot(tile, index, posX, posY));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return this.transferStack(player, index);
    }

}
