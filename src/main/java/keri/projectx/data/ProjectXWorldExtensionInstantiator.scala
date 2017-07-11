/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.data

import codechicken.lib.world.{ChunkExtension, WorldExtension, WorldExtensionInstantiator}
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk

/**
  * Created by Adam on 7/10/2017.
  */
object ProjectXWorldExtensionInstantiator extends WorldExtensionInstantiator {
  override def createWorldExtension(world: World): WorldExtension = new ProjectXWorldExtension(world)

  override def createChunkExtension(chunk: Chunk, worldExtension: WorldExtension): ChunkExtension = new ProjectXChunkExtension(chunk, worldExtension.asInstanceOf[ProjectXWorldExtension])

  def getExtensionXy(world: World): ProjectXWorldExtension = getExtension(world).asInstanceOf[ProjectXWorldExtension]
}
