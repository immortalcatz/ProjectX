package keri.projectx.machine.multiblock

import codechicken.lib.packet.PacketCustom
import codechicken.lib.world.{ChunkExtension, WorldExtension}
import keri.projectx.machine.multiblock.network.MultiBlocksCPH
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class XYWorldExtension(worldObj: World) extends WorldExtension(worldObj) {

  val dim = world.provider.getDimension
  val chunkPackets = new ArrayBuffer[XYChunkExtension]()
  private val multiBlocks = new mutable.HashMap[Int, MultiBlock]()
  private val unloadedMultiBlocks = new mutable.HashSet[MultiBlock]()
  private var multiBlockId = 1
  private var creatingClientSideMultiBlock = false

  def nextAvailableMultiBlockId: Int = {
    if (!creatingClientSideMultiBlock && worldObj.isRemote)
      throw new IllegalStateException("You cannot instantiate a Multiblock on the client side, bad kitty, *squirt*")
    val ret = multiBlockId
    multiBlockId += 1
    ret
  }

  override def postTick(): Unit = {
    for (chunkExt <- chunkPackets)
      chunkExt.sendUpdatePackets()
    chunkPackets.clear()

    for (multiblock <- unloadedMultiBlocks) {
      if (multiblock.chunksLoaded()) {
        addMultiBlock(multiblock)
        unloadedMultiBlocks.remove(multiblock)
      }
    }
  }

  def addMultiBlock(multiBlock: MultiBlock): Unit = {
    if (multiBlocks.get(multiBlock.id).isDefined) {
      println("MultiBlock already exists")
      return
    }

    //Fixes the issue with chunks not being loaded, allowing the multiblock to fail initialization on the client.
    if (!multiBlock.chunksLoaded()) {
      unloadMutliBlock(multiBlock)
      return
    }

    //Convert blocks and such and such to make sure it initializes fine.
    if (!multiBlock.initiate()) {
      println("MultiBlock failed initiation")
      return
    }

    println(s"Adding Multiblock to ${multiBlock.id} on ${if (world.isRemote) "client" else "server"}")
    //Add it to all chunks
    for (chunk <- multiBlock.inChunks)
      getChunkExtension(chunk).addMultiBlock(multiBlock)

    //Put the multiblock with its id and the mulitblock itself.
    multiBlocks.put(multiBlock.id, multiBlock)

    if (!world.isRemote)
      multiBlock.getChunkExt.sendMultiBlockPacket(multiBlock.getDescriptionPacket())
  }

  @SideOnly(Side.CLIENT)
  def handleDescriptionPacket(packet: PacketCustom): Unit = {
    multiBlockId = packet.readInt()
    creatingClientSideMultiBlock = true
    val multiblock = MultiBlockManager.createMultiBlock(
      MultiBlockTypes.values()(packet.readInt()),
      this,
      getChunkExtension(packet.readInt(), packet.readInt()).asInstanceOf[XYChunkExtension])
    creatingClientSideMultiBlock = false
    multiblock.handleDescriptionPacket(packet)
    addMultiBlock(multiblock)
  }

  @SideOnly(Side.CLIENT)
  def handleRemoveMultiBlockPacket(packet: PacketCustom): Unit = {
    if (packet.readInt() != dim)
      return
    val multiBlock = multiBlocks.get(packet.readInt())
    if (multiBlock.isDefined)
      removeMultiBlock(multiBlock.get)
  }

  def removeMultiBlock(multiBlock: MultiBlock, remove: Boolean = true): Unit = {
    if (unloadedMultiBlocks.remove(multiBlock)) {
      return
    }
    if (multiBlock.isValid && multiBlocks.remove(multiBlock.id).isEmpty) {
      return
    }
    if (multiBlock.isValid) {
      for (coord <- multiBlock.inChunks) {
        if (coord.eq(multiBlock.getChunkExt.coord) || remove)
          getChunkExtension(coord).removeMultiBlock(multiBlock)
      }
      if (!world.isRemote && remove) {
        val packet = new PacketCustom(MultiBlocksCPH.CHANNEL, 2)
        packet.writeInt(dim)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
    multiBlock.unload(remove)
  }

  def getChunkExtension(chunkPos: (Int, Int)): XYChunkExtension = getChunkExtension(chunkPos._1, chunkPos._2).asInstanceOf[XYChunkExtension]

  override def getChunkExtension(chunkXPos: Int, chunkZPos: Int): ChunkExtension = chunkMap.get(world.getChunkFromChunkCoords(chunkXPos, chunkZPos)).asInstanceOf[XYChunkExtension]

  @SideOnly(Side.CLIENT)
  def handleMultiBlockUpdate(packet: PacketCustom): Unit = {
    val id = packet.readInt()
    if (multiBlocks.contains(id)) {
      multiBlocks.get(id).exists(x => {
        x.readFromUpdatePacket(packet)
        true
      })
    }
  }

  def unloadMutliBlock(multiBlock: MultiBlock): Unit = {
    if (multiBlock.isValid && multiBlocks.remove(multiBlock.id).isEmpty) {
      return
    }
    unloadedMultiBlocks += multiBlock
    if (multiBlock.isValid) {
      for (coord <- multiBlock.inChunks) {
        if (coord.eq(multiBlock.getChunkExt.coord))
          getChunkExtension(coord).removeMultiBlock(multiBlock)
      }
      if (!world.isRemote) {
        val packet = new PacketCustom(MultiBlocksCPH.CHANNEL, 2)
        packet.writeInt(dim)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
  }


}
