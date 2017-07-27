/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.data

import codechicken.lib.packet.PacketCustom
import codechicken.lib.world.WorldExtension
import keri.projectx.ProjectX
import keri.projectx.multiblock.{MultiBlock, MultiBlockType, MultiBlockUtils}
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ProjectXWorldExtension(worldObj: World) extends WorldExtension(worldObj) {
  val dimension_id: Int = world.provider.getDimension
  val requested_chunk_packets = new ArrayBuffer[ProjectXChunkExtension]()
  private val multi_blocks = new mutable.HashMap[Int, MultiBlock]()
  private val shelved_multi_blocks = new mutable.HashSet[MultiBlock]()
  private var current_multi_block_id: Int = 1
  private var creating_client_side_multi_block: Boolean = false

  def nextAvailableMultiBlockId: Int = {
    if (!creating_client_side_multi_block && worldObj.isRemote)
      throw new IllegalStateException("You cannot instantiate a Multiblock on the client side, bad kitty, *squirt*")
    val ret = current_multi_block_id
    current_multi_block_id += 1
    ret
  }

  override def postTick(): Unit = {
    requested_chunk_packets.foreach(_.sendUpdatePackets())
    requested_chunk_packets.clear()

    shelved_multi_blocks
      .filter(_.chunksLoaded())
      .foreach(multiBlock => {
        addMultiBlock(multiBlock)
        shelved_multi_blocks.remove(multiBlock)
      })
  }
  def addMultiBlock(multiBlock: MultiBlock): Unit = {
    if (multi_blocks.get(multiBlock.id).isDefined) {
      println("MultiBlock already exists")
      return
    }

    //Fixes the issue with chunks not being loaded, allowing the multiblock to fail initialization on the client.
    if (!multiBlock.chunksLoaded()) {
      shelveMultiBlock(multiBlock)
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
    multi_blocks.put(multiBlock.id, multiBlock)

    if (!world.isRemote) {
      multiBlock.getChunkExt.sendMultiBlockPacket(multiBlock.getDescriptionPacket())
    }
  }
  def shelveMultiBlock(multiBlock: MultiBlock): Unit = {
    if (multiBlock.isValid && multi_blocks.remove(multiBlock.id).isEmpty) {
      return
    }
    shelved_multi_blocks += multiBlock
    if (multiBlock.isValid) {
      multiBlock.inChunks.foreach(coord => {
        if (coord.eq(multiBlock.getChunkExt.coord)) {
          getChunkExtension(coord).removeMultiBlock(multiBlock)
        }
      })
      if (!world.isRemote) {
        val packet = new PacketCustom(ProjectX.INSTANCE, 3)
        packet.writeInt(dimension_id)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
  }
  def handleDescriptionPacket(packet: PacketCustom): Unit = {
    current_multi_block_id = packet.readInt()
    creating_client_side_multi_block = true
    val multiblock = MultiBlockUtils.createMultiBlock(
      MultiBlockType.values()(packet.readInt()),
      this,
      getChunkExtension(new ChunkPos(packet.readInt(), packet.readInt())))
    creating_client_side_multi_block = false
    multiblock.handleDescriptionPacket(packet)
    addMultiBlock(multiblock)
  }
  @SideOnly(Side.CLIENT)
  def handleRemoveMultiBlockPacket(packet: PacketCustom): Unit = {
    if (packet.readInt() != dimension_id) {
      return
    }
    multi_blocks.get(packet.readInt()).foreach(removeMultiBlock)
  }

  def removeMultiBlock(multiBlock: MultiBlock): Unit = removeMultiBlock(multiBlock, remove = true)

  def removeMultiBlock(multiBlock: MultiBlock, remove: Boolean): Unit = {
    if (shelved_multi_blocks.remove(multiBlock)) {
      return
    }

    if (multiBlock.isValid && multi_blocks.remove(multiBlock.id).isEmpty) {
      return
    }

    if (multiBlock.isValid) {
      for (coord <- multiBlock.inChunks) {
        if (coord.eq(multiBlock.getChunkExt.coord) || remove) {
          getChunkExtension(coord).removeMultiBlock(multiBlock)
        }
      }
      if (!world.isRemote && remove) {
        val packet = new PacketCustom(ProjectX.INSTANCE, 3)
        packet.writeInt(dimension_id)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
    multiBlock.unload(remove)
  }
  def getChunkExtension(chunkPos: ChunkPos): ProjectXChunkExtension = getChunkExtension(chunkPos.chunkXPos, chunkPos.chunkZPos).asInstanceOf[ProjectXChunkExtension]
  @SideOnly(Side.CLIENT)
  def handleMultiBlockUpdate(packet: PacketCustom): Unit = {
    val id = packet.readInt()
    multi_blocks.get(id).foreach(multiBlock => {
      multiBlock.readFromUpdatePacket(packet)
    })
  }

}
