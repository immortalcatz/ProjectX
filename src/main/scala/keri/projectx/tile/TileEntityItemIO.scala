/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.tile

import keri.projectx.multiblock.MultiTank
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable

import scala.beans.BeanProperty

/**
  * Created by Adam on 7/12/2017.
  */
class TileEntityItemIO extends TileEntityMultiblock with ITickable with IInventory {
  @BeanProperty var currentState: ItemIOState = ItemIOState.OUT

  override def update(): Unit = {
  }

  override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
    tag.setInteger("state", currentState.ordinal())
    super.writeToNBT(tag)
  }

  override def readFromNBT(tag: NBTTagCompound): Unit = {
    super.readFromNBT(tag)
    setState(ItemIOState.values()(tag.getInteger("state")))
  }
  override def onBlockActivated(player: EntityPlayer): Boolean = {
    if (player.isSneaking) {
      setState(currentState.nextState())
    }
    super.onBlockActivated(player)
  }
  def setState(state: ItemIOState): Unit = {
    if (state != currentState) {
      currentState = state
      markDirty()
      sendUpdatePacket(true)
    }
  }
  override def decrStackSize(index: Int, count: Int): ItemStack = {
    if (getTank(0).isDefined && index > 0 && currentState == ItemIOState.OUT)
      getTank(0).get.asInstanceOf[MultiTank].decrStackSize(index, count)
    else ItemStack.EMPTY
  }
  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
    if (getTank(0).isDefined)
      getTank(0).get.asInstanceOf[MultiTank].setInventorySlotContents(index, stack)
  }
  override def closeInventory(player: EntityPlayer): Unit = {}
  override def getField(id: Int): Int = 0
  override def getSizeInventory: Int = {
    if (getTank(0).isDefined)
      return getTank(0).get.asInstanceOf[MultiTank].getSizeInventory()
    0
  }
  override def removeStackFromSlot(index: Int): ItemStack = {
    if (getTank(0).isDefined && currentState == ItemIOState.OUT)
      return getTank(0).get.asInstanceOf[MultiTank].removeStackFromSlot(index)
    ItemStack.EMPTY
  }
  override def getFieldCount: Int = 0
  override def setField(id: Int, value: Int): Unit = {}
  override def isUsableByPlayer(player: EntityPlayer): Boolean = true
  override def getInventoryStackLimit: Int = 64
  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = {
    if (getTank(0).isDefined && currentState == ItemIOState.IN)
      return getTank(0).get.asInstanceOf[MultiTank].isItemValidForSlot(index, stack)
    false
  }
  override def openInventory(player: EntityPlayer): Unit = {}
  override def clear(): Unit = {}
  override def isEmpty: Boolean = {
    if (getTank(0).isDefined)
      return getTank(0).get.asInstanceOf[MultiTank].isEmpty
    true
  }
  override def getStackInSlot(index: Int): ItemStack = {
    if (getTank(0).isDefined) {
      if (index == 1 || currentState == ItemIOState.IN) {
        return getTank(0).get.asInstanceOf[MultiTank].getStackInSlot(index)
      }
    }

    ItemStack.EMPTY
  }
  override def getName: String = ""
  override def hasCustomName: Boolean = false
}

