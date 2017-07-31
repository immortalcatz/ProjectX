/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block

import keri.projectx.client.render.{IAnimationHandler, RenderSimpleGlow}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

trait TBlockAnimationHandler extends Block with IAnimationHandler {

  override def canEntitySpawn(state: IBlockState, entity: Entity): Boolean = false

  @SideOnly(Side.CLIENT)
  override def getRenderType(state: IBlockState): EnumBlockRenderType = RenderSimpleGlow.RENDER_TYPE

  @SideOnly(Side.CLIENT)
  override def canRenderInLayer(state: IBlockState, layer: BlockRenderLayer): Boolean = layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED

}
