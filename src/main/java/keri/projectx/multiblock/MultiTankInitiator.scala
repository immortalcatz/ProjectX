package keri.projectx.multiblock

import keri.projectx.multiblock.block.BlockMulti
import keri.projectx.multiblock.tile.TileMultiBlock
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
      block.isInstanceOf[BlockMulti] || MultiShadowCategoryMatcher.ANY_BUT_AIR.matches(world, pos)
    }
  }
  var bounds: CuboidCoord = new CuboidCoord()
  var pattern: MultiBlockPattern = null

  override def create(world: World, pos: BlockPos): Option[MultiTank] = {
    val tile = world.getTileEntity(pos)
    if (!tile.isInstanceOf[TileMultiBlock])
      None
    for (side <- EnumFacing.VALUES) {
      if (checkTank(world, side, pos)) {
        val multiBlock = new MultiTank(world, (pos.getX >> 4, pos.getZ >> 4), bounds.copy())
        pattern.addAndCheckPattern(multiBlock, bounds.min.copy().sub(1, 1, 1).pos())
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
    pattern = new MultiBlockPattern(bounds.max.x - bounds.min.x + 3, bounds.max.y - bounds.min.y + 3, bounds.max.z - bounds.min.z + 3)
    pattern.addMatcher(matcher)
    pattern.addMatcher(MultiShadowCategoryMatcher.ANY_OPAQUE_EXCEPT_VALVE)
    //Fill inner part with -1 being air or ignored
    pattern.fill(1, 1, 1, pattern.width - 2, pattern.height - 2, pattern.depth - 2, -1)
    pattern.fillFaces(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 0)
    pattern.fillEdgesAndCorners(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 1)
    pattern.worldDemonstratesPattern(world, bounds.min.copy().sub(1, 1, 1).pos())
  }

  def computeBoundsForSide(world: World, tileCoords: BlockPos, side: EnumFacing): Unit = {
    val sideId = side.ordinal()
    bounds.min.set(tileCoords).offset(sideId)
    bounds.max.set(bounds.min)

    for (s <- EnumFacing.VALUES) {
      while (bounds.size(s.getIndex) <= 9 && validateInnerBounds(world, s))
        bounds.expand(s.getIndex, 1)
    }
  }

  def validateInnerBounds(world: World, side: EnumFacing): Boolean = {
    val shrunk = MultiBlockPattern.shrink(Array(bounds.min.y, bounds.max.y, bounds.min.z, bounds.max.z, bounds.min.x, bounds.max.x), side)
    for (x <- shrunk(4) to shrunk(5))
      for (y <- shrunk(0) to shrunk(1))
        for (z <- shrunk(2) to shrunk(3)) {
          if (!world.isAirBlock(new BlockPos(x, y, z)))
            return false
        }
    true
  }
}
