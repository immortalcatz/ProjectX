/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.fluid.FluidUtils
import codechicken.lib.inventory.InventoryUtils
import codechicken.lib.render.{CCRenderState, RenderUtils}
import codechicken.lib.vec.Cuboid6
import keri.projectx.ProjectX
import keri.projectx.data.{ProjectXChunkExtension, ProjectXWorldExtension, ProjectXWorldExtensionInstantiator}
import keri.projectx.multiblock.fluid.TFluidMultiBlock
import keri.projectx.network.ProjectXGuiHandler
import keri.projectx.tile.TileMultiBlock
import keri.projectx.util.FluidCapUtils
import keri.projectx.vec.{BlockCoord, CuboidCoord}
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fluids.capability.IFluidHandlerItem
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11

/**
  * Created by Adam on 7/10/2017.
  */
case class MultiTank(worldExt: ProjectXWorldExtension, chunkExt: ProjectXChunkExtension) extends MultiBlock(worldExt, chunkExt) with TFluidMultiBlock with IInventory {
  val inv = Array.fill[ItemStack](2)(ItemStack.EMPTY)
  var area: CuboidCoord = null

  def this(worldObj: World, location: (Int, Int), airArea: CuboidCoord) = {
    this(ProjectXWorldExtensionInstantiator.getExtension(worldObj).asInstanceOf[ProjectXWorldExtension],
      ProjectXWorldExtensionInstantiator.getExtension(worldObj).getChunkExtension(location._1, location._2).asInstanceOf[ProjectXChunkExtension])
    area = airArea
    initTank()
  }

  /**
    * @param blockPos the position of the block being clicked
    * @param player   the player
    * @return a int value corresponding to the type of action that should be completed after the block has been activated, 0 - nothing, 1 - finish, 2 - continue or finish at end
    */
  override def onActivated(blockPos: BlockPos, player: EntityPlayer): Int = {
    world.getTileEntity(blockPos) match {
      case tile: TileMultiBlock => if (tile.formedMultiBlocks.size() > 1) return 2
        player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.MULTI_TANK, world, blockPos.getX, blockPos.getY, blockPos.getZ)
        return 1
      case _ =>
    }
    super.onActivated(blockPos, player)
  }

  override def getMultiBlockId: MultiBlockTypes = MultiBlockTypes.TANK

  override def writeToNBT(nbt: NBTTagCompound): Unit = {
    super.writeToNBT(nbt)
    nbt.setTag("tank", tank.toTag)
    nbt.setTag("item", InventoryUtils.writeItemStacksToTag(inv))

  }

  override def writeBlockCoords(out: NBTTagCompound): NBTTagCompound = {
    out.setIntArray("block_coords", area.intArray())
    out
  }

  override def readBlockCoords(in: NBTTagCompound): Unit = {
    inBlocks.clear()
    area = new CuboidCoord(in.getIntArray("block_coords"))
    for (x <- area.min.x - 1 to area.max.x + 1)
      for (y <- area.min.y - 1 to area.max.y + 1)
        for (z <- area.min.z - 1 to area.max.z + 1)
          addTile(new BlockPos(x, y, z))
  }

  override def readFromDescriptionPacket(in: MCDataInput): Unit = {
    super.readFromDescriptionPacket(in)
    initTank()
    tank.read(in)
    inv(0) = in.readItemStack()
    inv(1) = in.readItemStack()
    println(inv(1))
  }


  override def writeToDescriptionPacket(out: MCDataOutput): Unit = {
    tank.write(out)
    out.writeItemStack(inv(0))
    out.writeItemStack(inv(1))
  }

  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    super.readFromNBT(nbt)
    initTank()
    tank.fromTag(nbt.getCompoundTag("tank"))
    InventoryUtils.readItemStacksFromTag(inv, nbt.getTagList("item", 10))
  }


  @SideOnly(Side.CLIENT) override
  def render(v: Float, i: Int): Unit = {
    val renderState = CCRenderState.instance()
    renderState.reset()
    val center = area.getCenter(new BlockCoord())
    renderState.setBrightness(world, center.pos())
    renderState.brightness += FluidUtils.getLuminosity(tank.getFluid, 16) * 0xF
    renderState.pushLightmap()
    renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    RenderUtils.preFluidRender()
    RenderUtils.renderFluidCuboid(tank.getFluid, new Cuboid6(area.min.x, area.min.y, area.min.z, area.max.x + 1, (((area.max.y + 1) - area.min.y) * (tank.c_ammount / tank.getCapacity.toDouble)) + area.min.y, area.max.z + 1), 1D, 1D)
    renderState.draw()
    RenderUtils.postFluidRender()
  }


  override def onUpdate(): Unit = {
    super.onUpdate()
    if (!world.isRemote) {
      useItems()
    }
  }

  def useItems(): Unit = {
    if (FluidUtil.getFluidHandler(inv(0)) != null) {
      val fluid = FluidUtil.getFluidContained(inv(0))
      val emptyItemStack = inv(0).getItem.getContainerItem(inv(0))
      if (fluid != null) {
        if (emptyItemStack.getCount == 0 || inv(1).getCount == 0 || InventoryUtils.incrStackSize(inv(1), emptyItemStack) > 0) {
          if (tank.fill(fluid, false) == fluid.amount) {
            tank.fill(fluid, true)
            decrStackSize(0, 1)
            if (emptyItemStack != null) {
              if (inv(1).getCount == 0) {
                inv(1) = emptyItemStack
              } else {
                inv(1).grow(emptyItemStack.getCount)
              }
            }
          }
        }
      } else if (tank.getFluid != null) {
        val slotContent = inv(0).copy()
        slotContent.setCount(1)
        val fill = FluidCapUtils.tryFillContainer(slotContent, tank.getFluid)
        if (fill.result.fluidStack == null) {
          return
        }
        var drain = true
        if (!inv(1).isEmpty) {
          if (inv(1).isStackable && InventoryUtils.canStack(inv(0), fill.result.itemStack) && inv(1).getCount() < inv(1).getMaxStackSize) {
            fill.result.itemStack.grow(inv(1).getCount)
          } else {
            drain = false
          }
        }
        if (drain) {
          var amount = if (fill.remainder.fluidStack != null) fill.remainder.fluidStack.amount else 0
          tank.drain(tank.getFluidAmount - amount, true)
          decrStackSize(0, 1)
          setInventorySlotContents(1, fill.result.itemStack)
        }
      }
    }
  }

  override def decrStackSize(index: Int, count: Int): ItemStack = InventoryUtils.decrStackSize(this, index, count)

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = inv(index) = stack

  //Inventory
  def onInvChanged(): Unit = requestUpdate()

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def isUsableByPlayer(player: EntityPlayer): Boolean = true

  override def getSizeInventory: Int = inv.length

  override def getInventoryStackLimit: Int = 64

  override def clear(): Unit = {}

  override def markDirty(): Unit = chunkExt.getChunk().setChunkModified()

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = FluidUtil.getFluidHandler(stack) != null || stack.getItem.isInstanceOf[IFluidHandlerItem]

  override def openInventory(player: EntityPlayer): Unit = {}

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def getStackInSlot(index: Int): ItemStack = inv(index)

  override def removeStackFromSlot(index: Int): ItemStack = InventoryUtils.removeStackFromSlot(this, index)

  override def setField(id: Int, value: Int): Unit = {}

  override def isEmpty: Boolean = false

  //IWorldNamable
  override def getDisplayName: ITextComponent = ???

  override def getName: String = ???

  override def hasCustomName: Boolean = ???
}
