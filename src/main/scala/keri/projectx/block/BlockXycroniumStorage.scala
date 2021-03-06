/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block

import keri.ninetaillib.lib.render.connected.ICTMBlock
import keri.ninetaillib.lib.texture.IIconRegister
import keri.ninetaillib.lib.util.{BlockAccessUtils, ClientUtils}
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockXycroniumStorage extends BlockProjectX("xycronium_block", Material.ROCK, EnumXycroniumColor.toStringArray) with TBlockAnimationHandler with ICTMBlock {
  setHardness(1.4F)

  @SideOnly(Side.CLIENT)
  var texture: Array[TextureAtlasSprite] = _

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = ClientUtils.registerConnectedTexture(register, ModPrefs.MODID + ":blocks/xycronium_block/xycronium_block");
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture(0)

  @SideOnly(Side.CLIENT)
  override def getConnectedTexture(world: IBlockAccess, pos: BlockPos, side: EnumFacing): Array[TextureAtlasSprite] = texture

  @SideOnly(Side.CLIENT)
  override def canTextureConnect(world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean = true

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.VALUES(stack.getMetadata).getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.VALUES(BlockAccessUtils.getBlockMetadata(world, pos)).getColor.rgba()

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 0x00F000F0

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 0x00F000F0

}
