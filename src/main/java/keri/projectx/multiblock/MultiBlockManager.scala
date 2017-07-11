package keri.projectx.multiblock

import keri.projectx.data.{ProjectXChunkExtension, ProjectXWorldExtension}
import keri.projectx.init.ProjectXContent
import keri.projectx.tile.{TileMultiBlock, TileMultiShadow}
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * Created by Adam on 7/10/2017.
  */
object MultiBlockManager {
  def convertBlockToShadow(world: World, pos: BlockPos): Option[TileMultiBlock] = {
    val blockState = world.getBlockState(pos)
    val meta = blockState.getBlock.getMetaFromState(blockState)

    if (blockState.getBlock.hasTileEntity(blockState))
      return None

    MultiCategoryMatcher.getCategoryForBlock(blockState) match {
      case MultiShadowTypes.ROCK => world.setBlockState(pos, ProjectXContent.SHADOW_ROCK.getStateFromMeta(meta), 3)
      case MultiShadowTypes.WOOD => world.setBlockState(pos, ProjectXContent.SHADOW_WOOD.getStateFromMeta(meta), 3)
      case MultiShadowTypes.GLASS => world.setBlockState(pos, ProjectXContent.SHADOW_GLASS.getStateFromMeta(meta), 3)
      case MultiShadowTypes.GRASS => world.setBlockState(pos, ProjectXContent.SHADOW_GRASS.getStateFromMeta(meta), 3)
      case MultiShadowTypes.AIR => world.setBlockState(pos, ProjectXContent.SHADOW_AIR.getStateFromMeta(meta), 3)
      case _ => world.setBlockState(pos, ProjectXContent.SHADOW_ROCK.getStateFromMeta(meta), 3)
    }

    val tile = world.getTileEntity(pos).asInstanceOf[TileMultiShadow]
    tile.setCurrentBlock(blockState.getBlock, meta)
    tile.markDirty()
    Some(tile)
  }

  def createMultiBlock(multiBlockId: MultiBlockTypes, worldEx: ProjectXWorldExtension, chunkEx: ProjectXChunkExtension): MultiBlock = {
    multiBlockId match {
      case MultiBlockTypes.TANK => new MultiTank(worldEx, chunkEx)
    }
  }
}
