/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.multiblock

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class MultiBlockCreator[T] {
  def create(world: World, origin: BlockPos): Option[T]
}
