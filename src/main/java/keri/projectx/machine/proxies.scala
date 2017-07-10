package keri.projectx.machine

import codechicken.lib.packet.PacketCustom
import codechicken.lib.render.block.BlockRenderingRegistry
import codechicken.lib.texture.TextureUtils
import codechicken.lib.world.WorldExtensionManager
import com.projectxy.featurehack.FeatureHack
import keri.projectx.machine.client.{ShadowBlockRenderer, TileFabricatorRenderer}
import keri.projectx.machine.multiblock._
import keri.projectx.machine.multiblock.client.MultiBlockOverlayRenderer
import keri.projectx.machine.multiblock.network.MultiBlocksCPH
import keri.projectx.machine.multiblock.tile.{TileMultiShadow, TileValve}
import keri.projectx.machine.tile.TileFabricator
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
    MechBlocks.init()
    GameRegistry.registerTileEntity(classOf[TileFabricator], ProjectXYMech.MOD_ID.toLowerCase + ".tileFabricator")
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
  lazy val shadowRenderType = BlockRenderingRegistry.createRenderType("shadow_block")

  @SideOnly(Side.CLIENT)
  override def preInit(event: FMLPreInitializationEvent) {
    //BlockIconRegistry.add("multiBlockOverlay", "overlay/multiblockOverlay")
    super.preInit(event)
    MinecraftForge.EVENT_BUS.register(MultiBlockOverlayRenderer)
    TextureUtils.addIconRegister(MultiBlockOverlayRenderer)
    BlockRenderingRegistry.registerRenderer(shadowRenderType, new ShadowBlockRenderer)
  }

  @SideOnly(Side.CLIENT)
  override def init(event: FMLInitializationEvent) {
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFabricator], new TileFabricatorRenderer)
    //RenderingRegistry.registerBlockHandler(new RenderBlockMultiShadow)
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