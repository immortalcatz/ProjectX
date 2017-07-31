/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block.multiblock

import keri.ninetaillib.lib.texture.IIconRegister
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.block.BlockAnimationHandler
import keri.projectx.tile.TileEntityFluidDetector
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockFluidDetector extends BlockAnimationHandler[TileEntityFluidDetector]("fluid_detector", Material.IRON) with TBlockMulti {
  setHardness(1.4F)

  @SideOnly(Side.CLIENT)
  var texture: TextureAtlasSprite = _

  override def registerTileEntities(): Unit = registerTile(classOf[TileEntityFluidDetector])

  override def createNewTileEntity(world: World, meta: Int): TileEntityFluidDetector = new TileEntityFluidDetector

  override def canProvidePower(state: IBlockState): Boolean = true

  override def getWeakPower(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Int = {
    blockAccess.getTileEntity(pos) match {
      case tileLevelDetector: TileEntityFluidDetector =>
        tileLevelDetector.power
      case _ => 0
    }
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = register.registerIcon(s"${ModPrefs.MODID}:blocks/machine/fluid_detector")
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture

  @SideOnly(Side.CLIENT)
  override def getIcon(world: IBlockAccess, pos: BlockPos, side: EnumFacing): TextureAtlasSprite = texture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.RED.getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.RED.getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = {
    world.getTileEntity(pos) match {
      case fluidDetector: TileEntityFluidDetector => if (fluidDetector.power > 0) 220 else 50
      case _ => 220
    }
  }

}
