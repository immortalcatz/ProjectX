package keri.projectx.multiblock

import codechicken.lib.world.{ChunkExtension, WorldExtension, WorldExtensionInstantiator}
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk

/**
  * Created by Adam on 7/10/2017.
  */
//XYWorldExtensionInstantiator renamed to be shorter
object XYWorldExtensionInstantiator extends WorldExtensionInstantiator {
  override def createWorldExtension(world: World): WorldExtension = new XYWorldExtension(world)

  override def createChunkExtension(chunk: Chunk, worldExtension: WorldExtension): ChunkExtension = new XYChunkExtension(chunk, worldExtension.asInstanceOf[XYWorldExtension])

  def getExtensionXy(world: World): XYWorldExtension = getExtension(world).asInstanceOf[XYWorldExtension]
}
