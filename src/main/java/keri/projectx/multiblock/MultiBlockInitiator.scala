/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class MultiBlockInitiator[T] {
  def create(world: World, pos: BlockPos): Option[T]
}
