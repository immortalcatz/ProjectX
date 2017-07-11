package keri.projectx.multiblock

import codechicken.lib.world.ChunkExtension
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class XYChunkExtension(chunk: Chunk, worldExt: XYWorldExtension) extends ChunkExtension(chunk, worldExt) {
  private val multiBlocks = new mutable.HashSet[MultiBlock]()
  private val updateMultiBlocks = new mutable.HashSet[MultiBlock]()
  private val packetQueue = new ArrayBuffer[FMLProxyPacket]()
  var requestedUpdatePackets = false

  def addMultiBlock(multiBlock: MultiBlock): Unit = {
    multiBlocks += multiBlock
    chunk.setChunkModified()
  }

  def removeMultiBlock(multiBlock: MultiBlock): Unit = multiBlocks.remove(multiBlock)

  override def sendUpdatePackets(): Unit = {
    requestedUpdatePackets = false
    for (packet <- packetQueue)
      sendPacketToPlayers(packet)
    for (multiBlock <- updateMultiBlocks) {
      sendPacketToPlayers(multiBlock.getUpdatePacket)
      updateMultiBlocks -= multiBlock
      multiBlock.requestsedUpdatePacket = false
    }
    packetQueue.clear()
  }

  def sendMultiBlockPacket(packet: FMLProxyPacket): Unit = {
    packetQueue += packet
    requestUpdatePacket
  }

  def requestUpdatePacket = {
    worldExt.chunkPackets += this
    requestedUpdatePackets = true
  }

  override def saveData(tag: NBTTagCompound): Unit = {
    val mulitBlockNBT = new NBTTagList
    for (multiBlock <- multiBlocks) {
      if (multiBlock.getChunkExt == this) {
        val nbt = new NBTTagCompound
        nbt.setInteger("multiBlockId", multiBlock.id)
        nbt.setInteger("multiBlockType", multiBlock.getMultiBlockId.ordinal())
        multiBlock.writeToNBT(nbt)
        mulitBlockNBT.appendTag(nbt)
      }
    }
    //Set the tag to something unique so it doesn't collide with other mods.
    tag.setTag("ProjectXMulti", mulitBlockNBT)
  }

  override def loadData(tag: NBTTagCompound): Unit = {
    val tagList = tag.getTagList("ProjectXMulti", 10)
    for (i <- 0 until tagList.tagCount()) {
      val nbt = tagList.getCompoundTagAt(i)
      val multiblock = MultiBlockManager.createMultiBlock(MultiBlockTypes.values()(nbt.getInteger("multiBlockType")), worldExt, this)
      multiblock.readFromNBT(nbt)
      worldExt.unloadMutliBlock(multiblock)
    }
  }

  override def unload(): Unit = {
    for (multiblock <- multiBlocks) {
      if (multiblock.getChunkExt == this || world.world.isRemote) {
        worldExt.removeMultiBlock(multiblock, remove = false)
      } else {
        worldExt.unloadMutliBlock(multiblock)
      }
    }
  }

  /**
    * Send update packet to player when chunk is loaded.
    *
    * @param player
    */
  override def onWatchPlayer(player: EntityPlayerMP): Unit = {
    for (multiBlock <- multiBlocks) {
      if (multiBlock.getChunkExt == this)
      //playerNetServerHandler
        player.connection.sendPacket(multiBlock.getDescriptionPacket())
    }
  }

  def getChunk(): Chunk = chunk

  def markMultiBlockForUpdate(multiBlock: MultiBlock): Unit = {
    if (multiBlock.requestsedUpdatePacket)
      return
    chunk.setModified(true)
    requestUpdatePacket
    updateMultiBlocks += multiBlock
    multiBlock.requestsedUpdatePacket = true
  }
}
