package keri.projectx.multiblock.fluid

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.fluid.FluidUtils
import keri.projectx.multiblock.MultiBlock
import keri.projectx.tile.TileValve
import keri.projectx.vec.CuboidCoord
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fluids.{FluidStack, FluidTankInfo}

import scala.collection.mutable.ArrayBuffer

trait TFluidMultiBlock extends MultiBlock {
  var tank: LevelSensitiveFluidTank = null
  var area: CuboidCoord

  def initTank(): Unit = tank = new LevelSensitiveFluidTank(this)

  def fill(pos: BlockPos, resource: FluidStack, doFill: Boolean): Int = tank.fill(resource, doFill)

  def drain(pos: BlockPos, maxDrain: Int, doDrain: Boolean): FluidStack = tank.access(pos.getY).drain(maxDrain, doDrain)

  def getTankInfo: FluidTankInfo = tank.getInfo

  override def writeToUpdatePacket(out: MCDataOutput): Unit = {
    tank.write(out)
  }

  override def readFromUpdatePacket(in: MCDataInput): Unit = {
    tank.read(in)
  }

  override def onUpdate(): Unit = {
    if (world.isRemote)
      tank.update()
    super.onUpdate()
  }


  override def onJoinTile(pos: BlockPos): Unit = {
    world.getTileEntity(pos) match {
      case valve: TileValve =>
        if (tank != null)
          tank.fill(valve.inactiveFluid, true)
        valve.inactiveFluid = FluidUtils.emptyFluid()
      case _ =>
    }
    super.onJoinTile(pos)
  }

  override def unload(remove: Boolean): Unit = {
    if (!world.isRemote && remove) {
      val valves = new ArrayBuffer[TileValve]()
      for (pos <- inBlocks) {
        world.getTileEntity(pos) match {
          case valve: TileValve =>
            if (valve.getTank(0).contains(this))
              valves += valve
          case _ =>
        }
      }
      if (valves.nonEmpty) {
        val splitAmnt = tank.getFluid.amount / valves.size
        for (valve <- valves) {
          valve.inactiveFluid = tank.getFluid
          valve.inactiveFluid.amount = splitAmnt
        }
      }
    }
    super.unload(remove)
  }
}
