package keri.projectx.multiblock.tile

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity

class TileMultiShadow extends TileMultiBlock /* with TMachineTile*/ {
  private var currBlock: BlockDef = null

  def setCurrentBlock(block: Block, meta: Int): Unit = setCurrentBlock(new BlockDef(block, meta))

  def setCurrentBlock(blockDef: BlockDef) = currBlock = blockDef

  def getCurrMeta: Option[Int] = if (currBlock != null) Some(currBlock.meta) else None

  def getCurrBLock: Option[Block] = if (currBlock != null) Some(currBlock.block) else None

  def getCurrBlockDef: Option[BlockDef] = Option(currBlock)

  override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
    super.writeToNBT(compound)
    if (currBlock != null)
      currBlock.write(compound)
    compound
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    BlockDef.read(compound).foreach(value => setCurrentBlock(value))
  }
}

case class BlockDef(block: Block, meta: Int) {
  def write(nbt: NBTTagCompound): Unit = {
    if (block != null) {
      nbt.setInteger("blockId", Block.getIdFromBlock(block))
      nbt.setInteger("metaData", meta)
    }
  }

  def write(out: MCDataOutput): Unit = {
    if (block != null) {
      out.writeInt(Block.getIdFromBlock(block))
      out.writeInt(meta)
    }
  }

  def getBlockState(): IBlockState = block.getStateFromMeta(meta)
}

object BlockDef {
  def read(nbt: NBTTagCompound): Option[BlockDef] = {
    if (nbt.hasKey("blockId")) {
      val block = Block.getBlockById(nbt.getInteger("blockId"))
      return Some(new BlockDef(block, nbt.getInteger("metData")))
    }
    None
  }

  def read(in: MCDataInput): Option[BlockDef] = {
    val block = in.readInt()
    val meta = in.readInt()
    Some(new BlockDef(Block.getBlockById(block), meta))
  }
}