/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.container;

import codechicken.lib.inventory.container.ContainerExtended;
import keri.ninetaillib.lib.container.slot.SlotFalseCopy;
import keri.projectx.container.slot.SlotDisabled;
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
                this.addSlotToContainer(new SlotFalseCopy(tile, index, posX, posY));
            }
        }

        this.addSlotToContainer(new SlotDisabled(tile, 9, 88, 34));

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
