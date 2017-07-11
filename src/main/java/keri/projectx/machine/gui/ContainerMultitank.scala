package keri.projectx.machine.gui

import codechicken.lib.inventory.container.ContainerExtended
import keri.projectx.client.gui.{SlotLiquidContainer, SlotOutput}
import keri.projectx.machine.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack

class ContainerMultitank(inventory: InventoryPlayer, multiTank: MultiTank) extends ContainerExtended {
  addSlotToContainer(new SlotLiquidContainer(multiTank, 0, 98, 50))
  addSlotToContainer(new SlotOutput(multiTank, 1, 152, 50))
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
