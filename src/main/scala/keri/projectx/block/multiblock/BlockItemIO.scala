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
import keri.projectx.client.render.RenderBlockBeveled
import keri.projectx.tile.TileEntityItemIO
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumBlockRenderType, EnumFacing}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockItemIO extends BlockAnimationHandler[TileEntityItemIO]("item_io", Material.IRON) with TBlockMulti {
  setHardness(1.4F)

  @SideOnly(Side.CLIENT)
  var texture: Array[TextureAtlasSprite] = _

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileEntityItemIO], "tile." + ModPrefs.MODID + ".multi_block_item_io")

  override def createTileEntity(world: World, state: IBlockState): TileEntity = new TileEntityItemIO

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = Array.fill[TextureAtlasSprite](2)(null)
    texture(0) = register.registerIcon(s"${ModPrefs.MODID}:blocks/machine/item_io_out")
    texture(1) = register.registerIcon(s"${ModPrefs.MODID}:blocks/machine/item_io_in")
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture(0)

  @SideOnly(Side.CLIENT)
  override def getIcon(world: IBlockAccess, pos: BlockPos, side: EnumFacing): TextureAtlasSprite = {
    world.getTileEntity(pos) match {
      case tileItemIO: TileEntityItemIO => texture(tileItemIO.currentState.ordinal())
      case _ => texture(0)
    }
  }

  @SideOnly(Side.CLIENT)
  override def getRenderType(state: IBlockState): EnumBlockRenderType = RenderBlockBeveled.RENDER_TYPE

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.GREEN.getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.GREEN.getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220

}
