/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile

import codechicken.lib.fluid.FluidUtils
import keri.projectx.api.fluid.IFluidSource
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{EnumFacing, ITickable}
import net.minecraftforge.fluids._
import net.minecraftforge.fluids.capability.{IFluidHandler, IFluidTankProperties}

class TileValve extends TileMultiBlock with IFluidHandler with ITickable {
  var inactiveFluid = FluidUtils.emptyFluid()

  override def drain(resource: FluidStack, doDrain: Boolean): FluidStack = drain(resource.amount, doDrain)

  override def drain(maxDrain: Int, doDrain: Boolean): FluidStack = {
    val tank = getTank(0)
    if (tank.isDefined) {
      return tank.get.drain(getPos, maxDrain, doDrain)
    }
    null
  }

  override def fill(resource: FluidStack, doFill: Boolean): Int = {
    val tank = getTank(0)
    if (tank.isDefined) {
      return tank.get.fill(getPos, resource, doFill)
    }
    0
  }


  override def writeToNBT(nbt: NBTTagCompound): NBTTagCompound = {
    nbt.setTag("inactive_fluid", FluidUtils.write(inactiveFluid, new NBTTagCompound))
    super.writeToNBT(nbt)
  }


  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    super.readFromNBT(nbt)
    inactiveFluid = FluidUtils.read(nbt.getCompoundTag("inactive_fluid"))
  }

  //TODO
  override def getTankProperties: Array[IFluidTankProperties] = null

  override def update(): Unit = {
    if (!world.isRemote && !formedMultiBlocks.isEmpty) {
      for (side <- EnumFacing.VALUES) {
        val state = getWorld.getBlockState(getPos.offset(side))
        state.getBlock match {
          case fluidSource: IFluidSource => getTank(0).get.tank.fill(fluidSource.getFluid(getWorld, getPos, state), true)
          case _ =>
        }
      }
    }
  }
}
