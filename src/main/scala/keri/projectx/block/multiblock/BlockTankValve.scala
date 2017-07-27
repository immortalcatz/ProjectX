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
import keri.projectx.multiblock.MultiTankCreator
import keri.projectx.tile.TileEntityTankValve
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumBlockRenderType, EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockTankValve extends BlockAnimationHandler[TileEntityTankValve]("valve", Material.IRON) with TBlockMulti {
  setHardness(1.4F)
  @SideOnly(Side.CLIENT)
  var texture: TextureAtlasSprite = _

  override def createNewTileEntity(world: World, meta: Int): TileEntityTankValve = new TileEntityTankValve

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    world.getTileEntity(pos) match {
      case valve: TileEntityTankValve =>
        if (!world.isRemote) {
          if (valve.isAlreadyPartOfStructure)
            return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
          MultiTankCreator.create(world, pos)
        }
      case _ =>
    }
    false
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = register.registerIcon(s"${ModPrefs.MODID}:blocks/machine/tank_valve")
  }

  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture
  override def getIcon(world: IBlockAccess, pos: BlockPos, side: EnumFacing): TextureAtlasSprite = texture
  override def getAnimationIcon(stack: ItemStack, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture
  override def getAnimationIcon(world: IBlockAccess, pos: BlockPos, side: Int): TextureAtlasSprite = ProjectX.PROXY.getAnimatedTexture
  override def getAnimationColor(stack: ItemStack, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()
  override def getAnimationColor(world: IBlockAccess, pos: BlockPos, side: Int): Int = EnumXycroniumColor.BLUE.getColor.rgba()
  override def getAnimationBrightness(stack: ItemStack, side: Int): Int = 220
  override def getAnimationBrightness(world: IBlockAccess, pos: BlockPos, side: Int): Int = 220
  @SideOnly(Side.CLIENT)
  override def getRenderType(state: IBlockState): EnumBlockRenderType = RenderBlockBeveled.RENDER_TYPE

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileEntityTankValve], "tile." + ModPrefs.MODID + ".multi_block_valve")
}

