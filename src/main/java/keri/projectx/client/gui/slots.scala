/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui

import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidUtil

class SlotLiquidContainer(inventory: IInventory, index: Int, posX: Int, posY: Int) extends Slot(inventory, index, posX, posY) {
  override def isItemValid(stack: ItemStack): Boolean = FluidUtil.getFluidHandler(stack) != null
}

class SlotOutput(inventory: IInventory, index: Int, posX: Int, posY: Int) extends Slot(inventory, index, posX, posY) {
  override def isItemValid(stack: ItemStack): Boolean = false
}