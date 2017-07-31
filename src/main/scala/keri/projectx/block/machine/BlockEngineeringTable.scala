/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block.machine

import keri.ninetaillib.lib.texture.IIconRegister
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.block.{BlockProjectX, TBlockAnimationHandler}
import keri.projectx.client.render.RenderEngineeringTable
import keri.projectx.tile.TileEntityEngineeringTable
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumBlockRenderType, EnumFacing}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockEngineeringTable extends BlockProjectX[TileEntityEngineeringTable]("engineering_table", Material.IRON) with TBlockAnimationHandler {
  setHardness(1.4F);

  @SideOnly(Side.CLIENT)
  var texture: TextureAtlasSprite = _

  override def registerTileEntities(): Unit = registerTile(classOf[TileEntityEngineeringTable])

  override def createNewTileEntity(world: World, meta: Int): TileEntityEngineeringTable = new TileEntityEngineeringTable

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_side")
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba

  @SideOnly(Side.CLIENT)
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220

  @SideOnly(Side.CLIENT)
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220

  @SideOnly(Side.CLIENT)
  override def getRenderType(state: IBlockState): EnumBlockRenderType = RenderEngineeringTable.RENDER_TYPE

  @SideOnly(Side.CLIENT)
  override def isOpaqueCube(state: IBlockState): Boolean = false

  @SideOnly(Side.CLIENT)
  override def isFullCube(state: IBlockState): Boolean = false

}
