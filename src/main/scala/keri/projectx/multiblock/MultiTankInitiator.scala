/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.multiblock

import keri.projectx.block.machine.multiblock.TBlockMulti
import keri.projectx.tile.TileMultiBlock
import keri.projectx.vec.CuboidCoord
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * Created by Adam on 7/10/2017.
  */
object MultiTankInitiator extends MultiBlockInitiator[MultiTank] {
  val matcher = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockPos): Boolean = {
      val block = world.getBlockState(pos).getBlock
      block.isInstanceOf[TBlockMulti] || MultiShadowCategoryMatcher.ANY_BUT_AIR.matches(world, pos)
    }
  }
  var innerBounds: CuboidCoord = new CuboidCoord()
  var pattern: MultiBlockPattern = null

  override def create(world: World, pos: BlockPos): Option[MultiTank] = {
    val tile = world.getTileEntity(pos)
    if (!tile.isInstanceOf[TileMultiBlock])
      None
    for (side <- EnumFacing.VALUES) {
      if (checkTank(world, side, pos)) {
        val multiBlock = new MultiTank(world, (pos.getX >> 4, pos.getZ >> 4), innerBounds.copy())
        pattern.addAndCheckPattern(multiBlock, innerBounds.min.copy().sub(1, 1, 1).pos())
        multiBlock.getWorldExt.addMultiBlock(multiBlock)
      }
    }
    None
  }

  /**
    * You have to validate each side, because a valve can be put on any face of the block.
    *
    * @return
    */
  def checkTank(world: World, side: EnumFacing, pos: BlockPos): Boolean = {
    computeBoundsForSide(world, pos, side)
    checkPattern(world: World)
  }

  def checkPattern(world: World): Boolean = {
    pattern = new MultiBlockPattern(innerBounds.max.x - innerBounds.min.x + 3, innerBounds.max.y - innerBounds.min.y + 3, innerBounds.max.z - innerBounds.min.z + 3)
    pattern.addMatcher(matcher)
    pattern.addMatcher(MultiShadowCategoryMatcher.ANY_OPAQUE_EXCEPT_VALVE)
    //Fill inner part with -1 being air or ignored
    pattern.fill(1, 1, 1, pattern.width - 2, pattern.height - 2, pattern.depth - 2, -1)
    pattern.fillFaces(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 0)
    pattern.fillEdgesAndCorners(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 1)
    pattern.worldDemonstratesPattern(world, innerBounds.min.copy().sub(1, 1, 1).pos())
  }

  def computeBoundsForSide(world: World, tileCoords: BlockPos, side: EnumFacing): Unit = {
    val sideId = side.ordinal()
    innerBounds.min.set(tileCoords).offset(sideId)
    innerBounds.max.set(innerBounds.min)

    for (s <- EnumFacing.VALUES) {
      while (innerBounds.size(s.getIndex) <= 9 && validateInnerBounds(world, s))
        innerBounds.expand(s.getIndex, 1)
    }
  }

  def validateInnerBounds(world: World, side: EnumFacing): Boolean = {
    val ebounds = innerBounds.copy().expand(side.getIndex, 1)
    for (x <- ebounds.min.x to ebounds.max.x)
      for (y <- ebounds.min.y to ebounds.max.y)
        for (z <- ebounds.min.z to ebounds.max.z) {
          if (!world.isAirBlock(new BlockPos(x, y, z)))
            return false
        }
    true
  }
}
