package keri.projectx.block.machine

import codechicken.lib.texture.TextureUtils
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.block.BlockAnimationHandler
import keri.projectx.network.ProjectXGuiHandler
import keri.projectx.tile.TileEntityFabricator
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockFabricator extends BlockAnimationHandler[TileEntityFabricator]("fabricator", Material.IRON) {

  //TODO: Adam pls help

  @SideOnly(Side.CLIENT)
  var texture: Array[TextureAtlasSprite] = _

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if(!world.isRemote){
      player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.GUIID_FABRICATOR, world, pos.getX, pos.getY, pos.getZ)
    }

    true
  }

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileEntityFabricator], "tile." + ModPrefs.MODID + ".fabricator")

  override def createNewTileEntity(world: World, meta: Int): TileEntityFabricator = new TileEntityFabricator

  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = TextureUtils.getMissingSprite

  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()

  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()

  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 0x00F000F0

  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 0x00F000F0

}
