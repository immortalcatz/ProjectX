package keri.projectx.machine.multiblock

import keri.projectx.machine.multiblock.block.BlockValve
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * Checks if a block matches based on category.
  * Ex any type of block that is ROCK
  */
object MultiCategoryMatcher {
  def getCategoryForBlock(state: IBlockState): MultiShadowTypes = {
    val block = state.getBlock
    if (block == null || block.getMaterial(state) == Material.AIR) {
      return MultiShadowTypes.AIR
    } else if (block.getMaterial(state) == Material.GLASS) {
      return MultiShadowTypes.GLASS
    } else if ( /*block.renderAsNormalBlock() &&*/ block.getMaterial(state).isOpaque) {
      if (block.getMaterial(state) == Material.ROCK || block.getMaterial(state) == Material.IRON) {
        return MultiShadowTypes.ROCK
      } else if (block.getMaterial(state) == Material.WOOD) {
        return MultiShadowTypes.WOOD
      } else {
        return MultiShadowTypes.GRASS
      }
    }
    MultiShadowTypes.NONE
  }
}

case class MultiShadowCategoryMatcher(category: MultiShadowTypes) extends MultiBlockMatcher {
  override def matches(world: World, pos: BlockPos): Boolean = MultiCategoryMatcher.getCategoryForBlock(world.getBlockState(pos)) == category
}

object MultiShadowCategoryMatcher {
  val AIR = new MultiShadowCategoryMatcher(MultiShadowTypes.AIR)
  val ROCK = new MultiShadowCategoryMatcher(MultiShadowTypes.ROCK)
  val WOOD = new MultiShadowCategoryMatcher(MultiShadowTypes.WOOD)
  val GLASS = new MultiShadowCategoryMatcher(MultiShadowTypes.GLASS)
  val GRASS = new MultiShadowCategoryMatcher(MultiShadowTypes.GRASS)
  val ANY_OPAQUE_EXCEPT_VALVE = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockPos): Boolean = {
      if (world.getBlockState(pos).getBlock.isInstanceOf[BlockValve])
        return false
      val category = MultiCategoryMatcher.getCategoryForBlock(world.getBlockState(pos))
      category == MultiShadowTypes.ROCK || category == MultiShadowTypes.WOOD || category == MultiShadowTypes.GRASS
    }
  }
  val ANY_BUT_AIR = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockPos): Boolean = {
      val category = MultiCategoryMatcher.getCategoryForBlock(world.getBlockState(pos))
      category == MultiShadowTypes.ROCK || category == MultiShadowTypes.WOOD || category == MultiShadowTypes.GRASS || category == MultiShadowTypes.GLASS
    }
  }
}

/**
  * Check if the block matches the specified pattern.
  */
abstract class MultiBlockMatcher {
  def matches(world: World, pos: BlockPos): Boolean
}

