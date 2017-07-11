package keri.projectx.machine

import codechicken.lib.packet.PacketCustom
import codechicken.lib.texture.TextureUtils
import codechicken.lib.world.WorldExtensionManager
import keri.projectx.featurehack.FeatureHack
import keri.projectx.machine.multiblock._
import keri.projectx.machine.multiblock.client.MultiBlockOverlayRenderer
import keri.projectx.machine.multiblock.network.MultiBlocksCPH
import keri.projectx.machine.multiblock.tile.{TileMultiShadow, TileValve}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
    GameRegistry.registerTileEntity(classOf[TileMultiShadow], ProjectXYMech.MOD_ID.toLowerCase + ".tileMultiShadow")
    GameRegistry.registerTileEntity(classOf[TileValve], ProjectXYMech.MOD_ID.toLowerCase + ".tileValve")
  }

  def init(event: FMLInitializationEvent): Unit = {
    WorldExtensionManager.registerWorldExtension(XYWorldExtensionInstantiator)
    FeatureHack.enableUpdateHook()
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {
  }
}

class ClientProxy extends CommonProxy {
  @SideOnly(Side.CLIENT)
  override def preInit(event: FMLPreInitializationEvent) {
    super.preInit(event)
    MinecraftForge.EVENT_BUS.register(MultiBlockOverlayRenderer)
    TextureUtils.addIconRegister(MultiBlockOverlayRenderer)
  }

  @SideOnly(Side.CLIENT)
  override def init(event: FMLInitializationEvent) {
    FeatureHack.enableRenderHook()
    PacketCustom.assignHandler(MultiBlocksCPH.CHANNEL, MultiBlocksCPH)
    super.init(event)
  }

  @SideOnly(Side.CLIENT)
  override def postInit(event: FMLPostInitializationEvent) {
    super.postInit(event)
  }
}

object ProjectXYMechProxy extends ClientProxy