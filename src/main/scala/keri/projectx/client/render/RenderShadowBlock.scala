/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render

import codechicken.lib.render.CCModel
import codechicken.lib.render.block.CCBlockRendererDispatcher
import codechicken.lib.vec.Cuboid6
import keri.ninetaillib.lib.render.{IBlockRenderingHandler, RenderingRegistry}
import keri.ninetaillib.lib.util.ModelUtils
import keri.projectx.tile.TileEntityMultiShadow
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.VertexBuffer
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType}
import net.minecraft.world.IBlockAccess

object RenderShadowBlock extends IBlockRenderingHandler {

  private final val DAMAGE_MODEL: CCModel = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D))
  final val RENDER_TYPE: EnumBlockRenderType = RenderingRegistry.getNextAvailableType
  RenderingRegistry.registerRenderingHandler(RenderShadowBlock.this)

  override def renderWorld(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, layer: BlockRenderLayer): Boolean = {
    val brd = new CCBlockRendererDispatcher(Minecraft.getMinecraft.getBlockRendererDispatcher, Minecraft.getMinecraft.getBlockColors)

    world.getTileEntity(pos) match {
      case tileMultiShadow: TileEntityMultiShadow => {
        val blockDef = tileMultiShadow.getCurrBlockDef

        if (blockDef.isDefined) {
          return brd.renderBlock(blockDef.get.getBlockState, pos, world, buffer)
        }

        false
      }
      case _ => return false
    }
  }

  override def renderDamage(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, texture: TextureAtlasSprite): Unit = {

  }

  override def renderInventory(stack: ItemStack, buffer: VertexBuffer): Unit = {}

  override def getRenderType: EnumBlockRenderType = RENDER_TYPE

}
