/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.data

import codechicken.lib.world.ChunkExtension
import keri.projectx.data.ProjectXChunkExtension._
import keri.projectx.multiblock.{MultiBlock, MultiBlockType, MultiBlockUtils}
import keri.projectx.util.ScalaNBTExtensions.asIterableCompound
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ProjectXChunkExtension(chunk: Chunk, worldExt: ProjectXWorldExtension) extends ChunkExtension(chunk, worldExt) {
  private val multi_blocks = new mutable.HashSet[MultiBlock]()
  private val multi_block_update_queue = new mutable.HashSet[MultiBlock]()
  private val packet_queue = new ArrayBuffer[FMLProxyPacket]()
  var requested_packet_update = false

  def getChunk: Chunk = chunk

  def addMultiBlock(multiBlock: MultiBlock): Unit = {
    multi_blocks += multiBlock
    chunk.setChunkModified()
  }

  def removeMultiBlock(multiBlock: MultiBlock): Unit = multi_blocks.remove(multiBlock)

  override def loadData(tag: NBTTagCompound): Unit =
    tag.getTagList(TAG_NAME, 10)
      .map(createMultiBlockFromNBT)
      .foreach(worldExt.shelveMultiBlock)
  private def createMultiBlockFromNBT(tag: NBTTagCompound): MultiBlock = {
    val multiblock = MultiBlockUtils.createMultiBlock(MultiBlockType.fromNBT(tag), worldExt, this)
    multiblock.readFromNBT(tag)
    multiblock
  }
  override def saveData(tag: NBTTagCompound): Unit = {
    val tagList = new NBTTagList
    multi_blocks
      .filter(_.partOfChunkExt(this))
      .map(saveMultiBlockToNBT)
      .foreach(tagList.appendTag)
    tag.setTag(TAG_NAME, tagList)
  }
  private def saveMultiBlockToNBT(multiBlock: MultiBlock): NBTTagCompound = {
    val nbt = new NBTTagCompound
    multiBlock.getMultiBlockType.writeToNBT(nbt)
    multiBlock.writeToNBT(nbt)
    nbt
  }
  /**
    * Called when the chunk is unloaded
    * Here we want to unload any multi-blocks that are in this chunk
    */
  override def unload(): Unit = {
    multi_blocks.foreach(multiBlock => {
      if (multiBlock.getChunkExt == this || world.world.isRemote) {
        worldExt.removeMultiBlock(multiBlock, remove = false)
      } else {
        worldExt.shelveMultiBlock(multiBlock)
      }
    })
  }

  /**
    * Send update packet to player when chunk is loaded.
    */
  override def onWatchPlayer(player: EntityPlayerMP): Unit = {
    multi_blocks.filter(_.partOfChunkExt(this)) foreach {
      multiBlock => player.connection.sendPacket(multiBlock.getDescriptionPacket())
    }
  }

  //Packets
  def markMultiBlockForUpdate(multiBlock: MultiBlock): Unit = {
    if (multiBlock.requestsedUpdatePacket) {
      return
    }
    chunk.setModified(true)
    multi_block_update_queue += multiBlock
    multiBlock.requestsedUpdatePacket = true
    requestUpdatePacket()
  }

  override def sendUpdatePackets(): Unit = {
    requested_packet_update = false
    packet_queue.foreach(sendPacketToPlayers)
    multi_block_update_queue.foreach(multiBlock => {
      sendPacketToPlayers(multiBlock.getUpdatePacket)
      multi_block_update_queue -= multiBlock
      multiBlock.requestsedUpdatePacket = false
    })
    packet_queue.clear()
  }

  def sendMultiBlockPacket(packet: FMLProxyPacket): Unit = {
    packet_queue += packet
    requestUpdatePacket()
  }

  /**
    * Requests update packet for the chunk
    */
  def requestUpdatePacket(): Unit = {
    worldExt.requested_chunk_packets += this
    requested_packet_update = true
  }
}

object ProjectXChunkExtension {
  val TAG_NAME = s"project_x_multi_blocks_version_${MultiblockVersion.VERSION}"
}
