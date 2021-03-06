/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.multiblock

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.packet.PacketCustom
import keri.projectx.ProjectX
import keri.projectx.data.{ProjectXChunkExtension, ProjectXWorldExtension, ProjectXWorldExtensionInstantiator}
import keri.projectx.featurehack.{EntityRenderHook, EntityUpdateHook}
import keri.projectx.tile.TileEntityMultiblock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.math.{BlockPos, ChunkPos}
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class MultiBlock(worldExt: ProjectXWorldExtension, chunkExt: ProjectXChunkExtension) extends EntityUpdateHook.IUpdateCallback with EntityRenderHook.IRenderCallback {
  val world: World = worldExt.world
  val id: Int = worldExt.nextAvailableMultiBlockId
  private var valid = false
  val inBlocks = new ArrayBuffer[BlockPos]()
  val inChunks = new mutable.HashSet[ChunkPos]()
  var requestsedUpdatePacket = false

  def this(worldObj: World, location: ChunkPos) =
    this(ProjectXWorldExtensionInstantiator.getExtensionX(worldObj),
      ProjectXWorldExtensionInstantiator.getExtensionX(worldObj).getChunkExtension(location))


  /**
    * @param blockPos the position of the block being clicked
    * @param player   the player
    * @return a int value corresponding to the type of action that should be completed after the block has been activated, 0 - nothing, 1 - finish, 2 - continue or finish at end
    */
  def onActivated(blockPos: BlockPos, player: EntityPlayer): Int = 0
  /**
    * Called before adding to the world
    */
  def initiate(): Boolean = {
    for (pos <- inBlocks) {
      world.getTileEntity(pos) match {
        case noop: TileEntityMultiblock =>
        case _ =>
          if (!world.isRemote) {
            val tile = MultiBlockUtils.convertBlockToShadow(world, pos)
            if (tile.isEmpty)
              return false
          } else {
            //Client side should always be false if the tile does not conform to TileMultiBlock. The server can fix itself.
            return false
          }
      }
      onJoinTile(pos)
    }

    inBlocks.foreach(world.getTileEntity(_) match {
      case multiblock: TileEntityMultiblock => multiblock.joinMultiBlock(this)
      case _ => sys.error("Tile is invalid.")
    })

    //Entity should always spawn at the head of the positions
    val position = inBlocks.head
    valid = true
    world.spawnEntity(new EntityUpdateHook(world, position.getX, position.getY, position.getZ, this))
    if (world.isRemote) {
      world.spawnEntity(new EntityRenderHook(world, position.getX, position.getY, position.getZ, this))
    }
    true
  }

  /**
    * An event called during initialization
    *
    * @param blockPos the position of the the that the multiblock joined.
    */
  def onJoinTile(blockPos: BlockPos): Unit = {}

  /**
    * unloads from the world
    *
    * @param remove weather it's being removing from the world or not.
    */
  def unload(remove: Boolean): Unit = {
    valid = false
    inBlocks foreach {
      world.getTileEntity(_) match {
        case tileMulti: TileEntityMultiblock => tileMulti.removeMultiBlock(this)
        case _ =>
      }
    }
  }

  /**
    * @return true if all chunks are loaded. Used to detect if the multiblock should be loaded into the world
    */
  final def chunksLoaded(): Boolean = {
    inChunks.foreach(chunkPos => if (worldExt.getChunkExtension(chunkPos) == null) {
      return false
    })
    true
  }

  /**
    * Looped when the multiblock is valid
    */
  override def onUpdate(): Unit = {
  }

  /**
    * From the entity update hook.
    *
    * @return
    */
  override def isValid: Boolean = valid

  /**
    * @return the packet that sends to the client when either added or reloaded or another player is added to the world
    */
  def getDescriptionPacket(): FMLProxyPacket = {
    val packet = new PacketCustom(ProjectX.INSTANCE, 2)
    packet.writeInt(id)
    packet.writeInt(getMultiBlockType.ordinal())
    packet.writeInt(chunkExt.coord.chunkXPos)
    packet.writeInt(chunkExt.coord.chunkZPos)
    packet.writeNBTTagCompound(writeBlockCoords(new NBTTagCompound))
    writeToDescriptionPacket(packet)
    packet.toPacket
  }

  def writeToDescriptionPacket(out: MCDataOutput): Unit = {}
  /**
    * Writes the block coords that compromises the structure to NBT so it can be sent to client and be saved to NBT
    */
  def writeBlockCoords(out: NBTTagCompound): NBTTagCompound = {
    val list = new NBTTagList
    inBlocks.foreach(blockCoord => {
      val inner = new NBTTagCompound
      inner.setInteger("x", blockCoord.getX)
      inner.setInteger("y", blockCoord.getY)
      inner.setInteger("z", blockCoord.getZ)
      list.appendTag(inner)
    })
    out.setTag("coords", list)
    out
  }
  def handleDescriptionPacket(packet: PacketCustom): Unit = {
    readBlockCoords(packet.readNBTTagCompound())
    readFromDescriptionPacket(packet)
  }
  def readFromDescriptionPacket(in: MCDataInput): Unit = {}
  /**
    * Reads the block coords that are written to NBT
    */
  def readBlockCoords(in: NBTTagCompound): Unit = {
    inBlocks.clear()
    val list = in.getTagList("coords", 10)
    0 until list.tagCount() map {
      list.getCompoundTagAt
    } foreach { list =>
      addTile(new BlockPos(list.getInteger("x"), list.getInteger("y"), list.getInteger("z")))
    }
  }
  /**
    * Adds a block to the structure
    *
    * @param blockPos the position of the block
    */
  def addTile(blockPos: BlockPos): Unit = {
    inBlocks += blockPos
    inChunks += new ChunkPos(blockPos.getX >> 4, blockPos.getZ >> 4)
  }
  def writeToNBT(nbt: NBTTagCompound): Unit = {
    writeBlockCoords(nbt)
  }
  def readFromNBT(nbt: NBTTagCompound): Unit = {
    readBlockCoords(nbt)
  }

  /**
    * The type id so that the server and client can recreate the block when reloaded.
    *
    * @return
    */
  def getMultiBlockType: MultiBlockType

  def readFromUpdatePacket(in: MCDataInput): Unit = {}

  def getUpdatePacket: FMLProxyPacket = {
    val packet = new PacketCustom(ProjectX.INSTANCE, 4)
    packet.writeInt(id)
    writeToUpdatePacket(packet)
    packet.toPacket
  }

  def writeToUpdatePacket(out: MCDataOutput): Unit = {}

  def requestUpdate(): Unit = chunkExt.markMultiBlockForUpdate(this)

  def getChunkExt: ProjectXChunkExtension = chunkExt

  def getWorldExt: ProjectXWorldExtension = worldExt

  @SideOnly(Side.CLIENT)
  override def shouldRenderInPass(i: Int): Boolean = i == 0

  @SideOnly(Side.CLIENT)
  override def render(v: Float, i: Int): Unit = {}

  /**
    * Idk why but this fixes a bug
    */
  override def equals(obj: scala.Any): Boolean = super.equals(obj)

  def partOfChunkExt(chunkExtension: ProjectXChunkExtension): Boolean = chunkExtension == this.chunkExt
}
