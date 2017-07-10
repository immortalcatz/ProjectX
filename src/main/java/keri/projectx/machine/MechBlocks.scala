package keri.projectx.machine

import keri.projectx.machine.block.{BlockFabricator, BlockXyBasicMachine}
import keri.projectx.machine.multiblock.block.{BlockMultiShadow, BlockMultiTank}
import com.projectxy.world.block.item.ItemBlockMetaHandler
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MechBlocks {
  val blockShadowRock: BlockMultiShadow = new BlockMultiShadow(Material.ROCK, "Rock")
  val blockShadowAir: BlockMultiShadow = new BlockMultiShadow(Material.AIR, "Air")
  val blockShadowGlass: BlockMultiShadow = new BlockMultiShadow(Material.GLASS, "Glass")
  val blockShadowWood: BlockMultiShadow = new BlockMultiShadow(Material.WOOD, "Wood")
  val blockShadowGrass: BlockMultiShadow = new BlockMultiShadow(Material.GROUND, "Grass")
  val blockMultiBlock: BlockMultiTank = new BlockMultiTank()
  val blockBasicMachine: BlockXyBasicMachine = new BlockXyBasicMachine
  val blockFabricator: BlockFabricator = new BlockFabricator

  def init(): Unit = {
    MinecraftForge.EVENT_BUS.register(this)
  }

  @SubscribeEvent
  def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
    val registry = event.getRegistry
    registry.registerAll(blockShadowRock, blockShadowAir, blockShadowGlass, blockShadowWood, blockShadowGrass)
    registry.register(blockMultiBlock)
    registry.register(blockBasicMachine)
    registry.register(blockFabricator)
  }

  @SubscribeEvent
  def registerItemBlocks(event: RegistryEvent.Register[Item]): Unit = {
    val registry = event.getRegistry
    registry.register(new ItemBlockMetaHandler(blockMultiBlock))
    registry.register(new ItemBlockMetaHandler(blockBasicMachine))
    registry.register(new ItemBlockMetaHandler(blockFabricator))
  }
}
