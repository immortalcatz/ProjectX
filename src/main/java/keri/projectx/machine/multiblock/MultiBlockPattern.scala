package keri.projectx.machine.multiblock

import keri.projectx.machine.multiblock.MultiBlockPattern._
import keri.projectx.machine.multiblock.tile.TileMultiBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

import scala.collection.mutable.ArrayBuffer

/**
  * Pattern maker for multiblcoks
  */
case class MultiBlockPattern(width: Int, height: Int, depth: Int) {
  val pattern = Array.ofDim[Int](width, height, depth)
  val matchers = new ArrayBuffer[MultiBlockMatcher]()

  def fill(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    for (xIndex <- minx to maxx)
      for (yIndex <- miny to maxy)
        for (zIndex <- minz to maxz)
          pattern(xIndex)(yIndex)(zIndex) = matcherIndex
  }

  def fillFaces(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    for (side <- EnumFacing.VALUES) {
      val shrunk = shrink(shift(minx, miny, minz, maxx, maxy, minz, 1), side)
      for (x <- shrunk(0) to shrunk(1))
        for (y <- shrunk(2) to shrunk(3))
          for (z <- shrunk(4) to shrunk(5))
            pattern(x)(y)(z) = matcherIndex
    }
  }

  /**
    * Fills the edges excluding corners.
    *
    * @param matcherIndex - can be -1 to be ignored, ex air.
    */
  def fillEdges(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    val shiftedBound = shift(minx, miny, minz, maxx, maxy, maxz, 1)
    for (x <- shiftedBound(0) to shiftedBound(1)) {
      pattern(x)(miny)(minz) = matcherIndex
      pattern(x)(miny)(maxy) = matcherIndex
      pattern(x)(maxy)(minz) = matcherIndex
      pattern(x)(maxy)(maxz) = matcherIndex
    }
    for (y <- shiftedBound(2) to shiftedBound(3)) {
      pattern(minx)(y)(minz) = matcherIndex
      pattern(minx)(y)(maxy) = matcherIndex
      pattern(maxx)(y)(minz) = matcherIndex
      pattern(maxx)(y)(maxz) = matcherIndex
    }
    for (z <- shiftedBound(4) to shiftedBound(5)) {
      pattern(minx)(miny)(z) = matcherIndex
      pattern(minx)(miny)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
    }
  }

  /**
    * Fills the edges with corners.
    *
    * @param matcherIndex - can be -1 to be ignored, ex air.
    */
  //Same as filledges but filles corners
  def fillEdgesAndCorners(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    val shifted = shift(minx, miny, minz, maxx, maxy, maxz, 1)
    for (x <- shifted(0) to shifted(1)) {
      pattern(x)(miny)(minz) = matcherIndex
      pattern(x)(miny)(maxz) = matcherIndex
      pattern(x)(maxy)(minz) = matcherIndex
      pattern(x)(maxy)(maxz) = matcherIndex
    }
    //Undo the offset of y to fill corners
    for (y <- shifted(2) - 1 to shifted(3) + 1) {
      pattern(minx)(y)(minz) = matcherIndex
      pattern(minx)(y)(maxz) = matcherIndex
      pattern(maxx)(y)(minz) = matcherIndex
      pattern(maxx)(y)(maxz) = matcherIndex
    }
    for (z <- shifted(4) to shifted(5)) {
      pattern(minx)(miny)(z) = matcherIndex
      pattern(minx)(maxy)(z) = matcherIndex
      pattern(maxx)(miny)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
    }
  }

  /**
    * Adds a matcher to the index.
    */
  def addMatcher(matcher: MultiBlockMatcher) = matchers += matcher

  def addAndCheckPattern(multiBlock: MultiBlock, pos: BlockPos): Unit = {
    for (x <- 0 until width; y <- 0 until height; z <- 0 until depth) {
      val blockPos = new BlockPos(pos).add(x, y, z)
      val tile = multiBlock.world.getTileEntity(pos)
      if (!tile.isInstanceOf[TileMultiBlock])
        MultiBlockManager.convertBlockToShadow(multiBlock.world, blockPos)
      multiBlock.addTile(blockPos)
    }
  }

  def worldDemonstratesPattern(world: World, pos: BlockPos): Boolean = {
    for (x <- 0 until width; y <- 0 until height; z <- 0 until depth) {
      val matcherId = pattern(x)(y)(z)
      if (matcherId >= 0 && !matchers(matcherId).matches(world, new BlockPos(pos).add(x, y, z))) {
        return false
      } else if (matcherId == -1) {
        val offsetPos = new BlockPos(pos).add(x, y, z)
        if (!world.isAirBlock(offsetPos)) {
          return false
        }
      }
    }
    true
  }
}

object MultiBlockPattern {
  def shrink(minxyzmaxxyz: Array[Int], side: EnumFacing): Array[Int] = {
    if (side.ordinal() % 2 == 0)
      minxyzmaxxyz(side.ordinal()) -= 1
    else
      minxyzmaxxyz(side.ordinal()) += 1
    minxyzmaxxyz
  }

  def shift(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, amt: Int): Array[Int] = Array(minx + amt, minx - amt, miny + amt, maxy - amt, minz + amt, maxz - amt)
}



