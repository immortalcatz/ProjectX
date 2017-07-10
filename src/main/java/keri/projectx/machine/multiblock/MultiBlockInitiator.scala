package keri.projectx.machine.multiblock

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class MultiBlockInitiator[T] {
  def create(world: World, pos: BlockPos): Option[T]
}
