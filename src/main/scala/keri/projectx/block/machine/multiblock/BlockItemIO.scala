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
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.common.registry.GameRegistry

/**
  * Created by Adam on 7/12/2017.
  */
class BlockItemIO extends BlockAnimationHandler[TileEntityItemIO]("item_io", Material.IRON) with TBlockMulti {
  var texture: TextureAtlasSprite = _

  override def registerIcons(register: IIconRegister): Unit = {
    texture = register.registerIcon(s"${ModPrefs.MODID}:blocks/valve")
  }

  override def getIcon(meta: Int, side: Int): TextureAtlasSprite = texture

  override def getIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = texture

  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.LIGHT.getColor.rgba()

  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.LIGHT.getColor.rgba()

  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220

  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileEntityItemIO], "tile." + ModPrefs.MODID + ".multi_block_item_io")
}
