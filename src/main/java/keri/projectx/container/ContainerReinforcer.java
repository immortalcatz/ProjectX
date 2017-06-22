/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.container;

import keri.ninetaillib.lib.container.ContainerBase;
import keri.projectx.tile.TileEntityReinforcer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerReinforcer extends ContainerBase {

    private TileEntityReinforcer tile;

    public ContainerReinforcer(InventoryPlayer inventoryPlayer, TileEntityReinforcer tile) {
        this.tile = tile;
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
