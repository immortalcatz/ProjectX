package keri.projectx.container;

import codechicken.lib.inventory.container.ContainerExtended;
import codechicken.lib.inventory.container.SlotDummy;
import keri.projectx.tile.TileEntityFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerFabricator extends ContainerExtended {

    private InventoryPlayer inventoryPlayer;
    private TileEntityFabricator tile;

    public ContainerFabricator(InventoryPlayer inventoryPlayer, TileEntityFabricator tile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.bindPlayerInventory(inventoryPlayer, 16, 84);

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                int index = x + 3 * y;
                int posX = 16 + 18 * x;
                int posY = 17 + 18 * y;
                this.addSlotToContainer(new SlotDummy(tile, index, posX, posY));
            }
        }

        this.addSlotToContainer(new SlotCraftingResult(tile, 9, 88, 34));

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                int index = (x + 3 * y) + 10;
                int posX = 124 + 18 * x;
                int posY = 17 + 18 * y;
                this.addSlotToContainer(new Slot(tile, index, posX, posY));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

}
