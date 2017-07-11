package keri.projectx.multiblock

import codechicken.lib.packet.PacketCustom
import codechicken.lib.texture.TextureUtils
import codechicken.lib.world.WorldExtensionManager
import keri.projectx.client.event.MultiBlockOverlayRenderer
import keri.projectx.data.ProjectXWorldExtensionInstantiator
import keri.projectx.featurehack.FeatureHack
import keri.projectx.multiblock.network.MultiBlocksCPH
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

//TODO: move this to ClientProxy/CommonProxy/ServerProxy
object MultiBlockCommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
  }

  def init(event: FMLInitializationEvent): Unit = {
    WorldExtensionManager.registerWorldExtension(ProjectXWorldExtensionInstantiator)
    FeatureHack.enableUpdateHook()
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {
  }
}

object MultiBlockClientProxy {
  @SideOnly(Side.CLIENT)
  def preInit(event: FMLPreInitializationEvent) {
    MinecraftForge.EVENT_BUS.register(MultiBlockOverlayRenderer)
    TextureUtils.addIconRegister(MultiBlockOverlayRenderer)
  }

  @SideOnly(Side.CLIENT)
  def init(event: FMLInitializationEvent) {
    FeatureHack.enableRenderHook()
    PacketCustom.assignHandler(MultiBlocksCPH.CHANNEL, MultiBlocksCPH)
  }

  @SideOnly(Side.CLIENT)
  def postInit(event: FMLPostInitializationEvent) {
  }
}