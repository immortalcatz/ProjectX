package keri.projectx.multiblock.block

import keri.ninetaillib.lib.texture.IIconRegister
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.block.BlockAnimationHandler
import keri.projectx.multiblock.MultiTankInitiator
import keri.projectx.multiblock.tile.TileValve
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}

class BlockValve extends BlockAnimationHandler[TileValve]("valve", Material.IRON) with BlockMulti {
  var texture: TextureAtlasSprite = _

  override def createNewTileEntity(world: World, meta: Int): TileValve = new TileValve

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    world.getTileEntity(pos) match {
      case valve: TileValve =>
        if (!world.isRemote) {
          if (valve.isAlreadyPartOfStructure)
            return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
          MultiTankInitiator.create(world, pos)
        }
      case _ =>
    }
    false
  }


  override def registerIcons(register: IIconRegister): Unit = {
    texture = register.registerIcon(s"${ModPrefs.MODID}:blocks/valve")
  }

  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()

  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()

  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220

  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220

  override def getIcon(meta: Int, side: Int): TextureAtlasSprite = texture

  override def getIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = texture
}

