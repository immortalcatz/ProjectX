/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.container;

import keri.ninetaillib.lib.container.ContainerBase;
import keri.ninetaillib.lib.container.slot.SlotOutput;
import keri.projectx.tile.TileEntityReinforcer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerReinforcer extends ContainerBase {

    private TileEntityReinforcer tile;

    public ContainerReinforcer(InventoryPlayer inventoryPlayer, TileEntityReinforcer tile) {
        this.tile = tile;
        this.bindPlayerInventory(inventoryPlayer, 8, 84);
        //input slot left
        this.addSlotToContainer(new Slot(tile, 0, 34, 35));
        //right slot left
        this.addSlotToContainer(new Slot(tile, 1, 56, 35));
        //output slot
        this.addSlotToContainer(new SlotOutput(tile, 2, 118, 35));
    }

    @Override
    public int getSizeInventory() {
        return this.tile.getSizeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

}
