package keri.projectx.machine.multiblock

import keri.projectx.machine.MechBlocks
import keri.projectx.machine.multiblock.tile.{TileMultiBlock, TileMultiShadow}
import net.minecraft.block.ITileEntityProvider
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object MultiBlockManager {
  def convertBlockToShadow(world: World, pos: BlockPos): Option[TileMultiBlock] = {
    val blockState = world.getBlockState(pos)
    val meta = blockState.getBlock.getMetaFromState(blockState)

    if (blockState.isInstanceOf[ITileEntityProvider] || blockState.getBlock.hasTileEntity(blockState))
      return None

    MultiCategoryMatcher.getCategoryForBlock(blockState) match {
      case MultiShadowTypes.ROCK => world.setBlockState(pos, MechBlocks.blockShadowRock.getStateFromMeta(meta), 3)
      case MultiShadowTypes.WOOD => world.setBlockState(pos, MechBlocks.blockShadowWood.getStateFromMeta(meta), 3)
      case MultiShadowTypes.GLASS => world.setBlockState(pos, MechBlocks.blockShadowGlass.getStateFromMeta(meta), 3)
      case MultiShadowTypes.GRASS => world.setBlockState(pos, MechBlocks.blockShadowGrass.getStateFromMeta(meta), 3)
      case MultiShadowTypes.AIR => world.setBlockState(pos, MechBlocks.blockShadowAir.getStateFromMeta(meta), 3)
      case _ => world.setBlockState(pos, MechBlocks.blockShadowRock.getStateFromMeta(meta), 3)
    }

    val tile = world.getTileEntity(pos).asInstanceOf[TileMultiShadow]
    tile.setCurrentBlock(blockState.getBlock, meta)
    Some(tile)
  }

  def createMultiBlock(multiBlockId: MultiBlockTypes, worldEx: XYWorldExtension, chunkEx: XYChunkExtension): MultiBlock = {
    multiBlockId match {
      case MultiBlockTypes.TANK => new MultiTank(worldEx, chunkEx)
    }
  }
}
