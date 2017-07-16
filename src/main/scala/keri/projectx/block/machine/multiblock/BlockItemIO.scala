/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine.multiblock

import keri.ninetaillib.lib.texture.IIconRegister
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.block.BlockAnimationHandler
import keri.projectx.tile.TileEntityItemIO
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.common.registry.GameRegistry

/**
  * Created by Adam on 7/12/2017.
  */
class BlockItemIO extends BlockAnimationHandler[TileEntityItemIO]("item_io", Material.IRON) with TBlockMulti {
  setHardness(1.4F)
  var texture: Array[TextureAtlasSprite] = Array.fill[TextureAtlasSprite](2)(null)

  override def registerIcons(register: IIconRegister): Unit = {
    texture(0) = register.registerIcon(s"${ModPrefs.MODID}:blocks/item_io_out")
    texture(1) = register.registerIcon(s"${ModPrefs.MODID}:blocks/item_io_in")
  }


  override def createTileEntity(world: World, state: IBlockState): TileEntity = new TileEntityItemIO

  override def getIcon(meta: Int, side: Int): TextureAtlasSprite = texture(0)
  override def getIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = {
    world.getTileEntity(pos) match {
      case tileItemIO: TileEntityItemIO => texture(tileItemIO.currentState.ordinal())
      case _ => texture(0)
    }
  }

  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.LIGHT.getColor.rgba()
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.LIGHT.getColor.rgba()

  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileEntityItemIO], "tile." + ModPrefs.MODID + ".multi_block_item_io")
}
