/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.multiblock

import keri.projectx.block.BlockAnimationHandler
import keri.projectx.data.{ProjectXChunkExtension, ProjectXWorldExtension}
import keri.projectx.tile.{TileEntityMultiShadow, TileEntityMultiblock}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * Created by Adam on 7/10/2017.
  */
object MultiBlockUtils {
  def convertBlockToShadow(world: World, pos: BlockPos): Option[TileEntityMultiblock] = {
    val blockState = world.getBlockState(pos)
    val meta = blockState.getBlock.getMetaFromState(blockState)

    if (!(blockState.getBlock.isInstanceOf[BlockAnimationHandler[TileEntity]])) {
      if (blockState.getBlock.hasTileEntity(blockState))
        return None
    }

    //TODO add more types
    MultiCategoryMatcher.getCategoryForBlock(blockState).convertToShadowBlock(world, pos, meta)

    val tile = world.getTileEntity(pos).asInstanceOf[TileEntityMultiShadow]
    tile.setCurrentBlock(blockState.getBlock, meta)
    tile.markDirty()
    Some(tile)
  }

  //Creates Mutli-block from storage
  def createMultiBlock(multiBlockId: MultiBlockType, worldEx: ProjectXWorldExtension, chunkEx: ProjectXChunkExtension): MultiBlock = multiBlockId match {
    case MultiBlockType.TANK => new MultiTank(worldEx, chunkEx)
  }
}
