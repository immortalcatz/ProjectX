/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.data

import codechicken.lib.packet.PacketCustom
import codechicken.lib.world.WorldExtension
import keri.projectx.ProjectX
import keri.projectx.multiblock.{Multiblock, MultiblockManager, MultiblockType}
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ProjectXWorldExtension(worldObj: World) extends WorldExtension(worldObj) {
  val dim = world.provider.getDimension
  val chunkPackets = new ArrayBuffer[ProjectXChunkExtension]()
  private val multiBlocks = new mutable.HashMap[Int, Multiblock]()
  private val unloadedMultiBlocks = new mutable.HashSet[Multiblock]()
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
    chunkPackets.foreach(_.sendUpdatePackets())
    chunkPackets.clear()

    unloadedMultiBlocks.filter(multiBlock => {
      multiBlock.chunksLoaded()
    }).foreach(multiBlock => {
      addMultiBlock(multiBlock)
      unloadedMultiBlocks.remove(multiBlock)
    })
  }

  @SideOnly(Side.CLIENT)
  def handleDescriptionPacket(packet: PacketCustom): Unit = {
    multiBlockId = packet.readInt()
    creatingClientSideMultiBlock = true
    val multiblock = MultiblockManager.createMultiBlock(
      MultiblockType.values()(packet.readInt()),
      this,
      getChunkExtension(packet.readInt(), packet.readInt()).asInstanceOf[ProjectXChunkExtension])
    creatingClientSideMultiBlock = false
    multiblock.handleDescriptionPacket(packet)
    addMultiBlock(multiblock)
  }

  def addMultiBlock(multiBlock: Multiblock): Unit = {
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

  def unloadMutliBlock(multiBlock: Multiblock): Unit = {
    if (multiBlock.isValid && multiBlocks.remove(multiBlock.id).isEmpty) {
      return
    }
    unloadedMultiBlocks += multiBlock
    if (multiBlock.isValid) {
      multiBlock.inChunks.foreach(coord => {
        if (coord.eq(multiBlock.getChunkExt.coord)) {
          getChunkExtension(coord).removeMultiBlock(multiBlock)
        }
      })
      if (!world.isRemote) {
        val packet = new PacketCustom(ProjectX.INSTANCE, 3)
        packet.writeInt(dim)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
  }
  
  def getChunkExtension(chunkPos: ChunkPos): ProjectXChunkExtension = getChunkExtension(chunkPos.chunkXPos, chunkPos.chunkZPos).asInstanceOf[ProjectXChunkExtension]

  @SideOnly(Side.CLIENT)
  def handleRemoveMultiBlockPacket(packet: PacketCustom): Unit = {
    if (packet.readInt() != dim) {
      return
    }
    val multiBlock = multiBlocks.get(packet.readInt())
    if (multiBlock.isDefined) {
      removeMultiBlock(multiBlock.get)
    }
  }

  def removeMultiBlock(multiBlock: Multiblock, remove: Boolean = true): Unit = {
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
        val packet = new PacketCustom(ProjectX.INSTANCE, 3)
        packet.writeInt(dim)
        packet.writeInt(multiBlock.id)
        multiBlock.getChunkExt.sendPacketToPlayers(packet.toPacket)
      }
    }
    multiBlock.unload(remove)
  }

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


}
