/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.container

import codechicken.lib.inventory.container.ContainerExtended
import keri.projectx.client.gui.{SlotLiquidContainer, SlotOutput}
import keri.projectx.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack

class ContainerMultitank(inventory: InventoryPlayer, multiTank: MultiTank) extends ContainerExtended {
  addSlotToContainer(new SlotLiquidContainer(multiTank, 0, 78, 40))
  addSlotToContainer(new SlotOutput(multiTank, 1, 132, 40))
  bindPlayerInventory(inventory, 8, 93)

  override def doMergeStackAreas(slotIndex: Int, stack: ItemStack): Boolean = {
    if (slotIndex <= 1) {
      return mergeItemStack(stack, 2, 38, true)
    }
    if (getSlot(0).isItemValid(stack)) {
      return mergeItemStack(stack, 0, 1, true)
    }
    false
  }
}
