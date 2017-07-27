/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.tile

import keri.projectx.multiblock.MultiBlock
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable

/**
  * Created by Adam on 7/11/2017.
  */
class TileEntityFluidDetector extends TileEntityMultiblock with ITickable {
  var power: Int = 0

  override def update(): Unit = {
    if (getWorld.isRemote)
      return

    val tank = getTank(0)
    if (tank.isDefined) {
      val tankAccess = tank.get.tank.access(getPos.getY)
      setPower(if (tankAccess.getFluidAmount > 0) 15 else 0)
    }
  }

  def setPower(power: Int): Unit = {
    if (this.power != power) {
      this.power = power
      world.notifyNeighborsOfStateChange(pos, getBlockType, false)
      sendUpdatePacket(true)
      markDirty()
    }
  }

  override def removeMultiBlock(multiBlock: MultiBlock): Boolean = {
    setPower(0)
    super.removeMultiBlock(multiBlock)
  }

  override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
    tag.setInteger("current_power", power)
    super.writeToNBT(tag)
  }


  override def readFromNBT(tag: NBTTagCompound): Unit = {
    super.readFromNBT(tag)
    power = tag.getInteger("current_power")
    world.notifyNeighborsOfStateChange(pos, getBlockType, false)
  }
}
