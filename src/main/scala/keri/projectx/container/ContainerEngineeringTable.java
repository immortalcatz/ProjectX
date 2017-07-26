/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.container;

import codechicken.lib.inventory.container.ContainerExtended;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import javax.annotation.Nonnull;

public class ContainerEngineeringTable extends ContainerExtended {

    private TileEntityEngineeringTable tile;

    public ContainerEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

}
