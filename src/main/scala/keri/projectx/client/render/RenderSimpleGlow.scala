/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render

import codechicken.lib.render.CCRenderState
import codechicken.lib.render.buffer.BakingVertexBuffer
import codechicken.lib.vec.uv.IconTransformation
import codechicken.lib.vec.{Cuboid6, Translation, Vector3}
import keri.ninetaillib.lib.render.connected.{ConnectedRenderContext, ICTMBlock}
import keri.ninetaillib.lib.render.{IBlockRenderingHandler, RenderingRegistry}
import keri.ninetaillib.lib.texture.IIconBlock
import keri.ninetaillib.lib.util.{ModelUtils, RenderUtils}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, OpenGlHelper, Tessellator, VertexBuffer}
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType, EnumFacing}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT)
object RenderSimpleGlow extends IBlockRenderingHandler {

  private final val BLOCK_MODEL = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D))
  final val RENDER_TYPE: EnumBlockRenderType = RenderingRegistry.getNextAvailableType
  RenderingRegistry.registerRenderingHandler(RenderSimpleGlow.this)

  override def renderWorld(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, layer: BlockRenderLayer): Boolean = {
    val iconProvider = state.getBlock.asInstanceOf[IIconBlock]
    val animationHandler = state.getBlock.asInstanceOf[IAnimationHandler]
    val renderState = CCRenderState.instance
    val translation = new Translation(Vector3.fromBlockPos(pos))
    val meta = state.getBlock.getMetaFromState(state)
    renderState.reset()
    renderState.bind(buffer)

    for (side <- 0 until 6) {
      val texture = animationHandler.getAnimationIcon(world, pos, side)
      val color = animationHandler.getAnimationColor(world, pos, side)
      renderState.brightness = animationHandler.getAnimationBrightness(world, pos, side)
      BLOCK_MODEL.copy.apply(translation).setColour(color).render(renderState, 4 * side, 4 + 4 * side, new IconTransformation(texture))
    }

    if (layer == BlockRenderLayer.CUTOUT_MIPPED) {
      val parentBuffer = BakingVertexBuffer.create
      parentBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)
      renderState.reset
      renderState.bind(parentBuffer)

      state.getBlock match {
        case ctmBlock: ICTMBlock => {
          val renderContext: ConnectedRenderContext = new ConnectedRenderContext

          for (side <- 0 until 6) {
            val texture: Array[TextureAtlasSprite] = ctmBlock.getConnectedTexture(world, pos, EnumFacing.getFront(side))
            renderContext.setBlockAccess(world)
            renderContext.setCurrentBlockState(state)
            renderContext.setChangeBounds(true)

            if (ctmBlock.canTextureConnect(world, pos, EnumFacing.getFront(side))) {
              renderContext.renderFace(pos, texture, EnumFacing.getFront(side))
            }
          }

          renderContext.getModel.copy.render(renderState)
        }
        case _ => {
          for (side <- 0 until 6) {
            var texture: TextureAtlasSprite = null
            val color = iconProvider.getColorMultiplier(meta, EnumFacing.getFront(side))

            if (iconProvider.getIcon(world, pos, EnumFacing.getFront(side)) != null) {
              texture = iconProvider.getIcon(world, pos, EnumFacing.getFront(side))
            }
            else {
              texture = iconProvider.getIcon(meta, EnumFacing.getFront(side))
            }

            BLOCK_MODEL.copy.setColour(color).render(renderState, 4 * side, 4 + 4 * side, new IconTransformation(texture))
          }
        }
      }

      parentBuffer.finishDrawing()
      RenderUtils.renderQuads(buffer, world, pos, parentBuffer.bake())
    }

    false
  }

  override def renderDamage(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, texture: TextureAtlasSprite): Unit = {
    val renderState = CCRenderState.instance
    val translation = new Translation(Vector3.fromBlockPos(pos))
    renderState.reset
    renderState.bind(buffer)
    BLOCK_MODEL.copy.apply(translation).render(renderState, new IconTransformation(texture))
  }

  override def renderInventory(stack: ItemStack, buffer: VertexBuffer): Unit = {
    val iconProvider = Block.getBlockFromItem(stack.getItem).asInstanceOf[IIconBlock]
    val animationHandler = Block.getBlockFromItem(stack.getItem).asInstanceOf[IAnimationHandler]
    val meta = stack.getMetadata
    val lastBrightness = OpenGlHelper.lastBrightnessY.toInt << 16 | OpenGlHelper.lastBrightnessX.toInt
    val renderState = CCRenderState.instance
    Tessellator.getInstance.draw
    GlStateManager.pushMatrix
    GlStateManager.disableLighting
    val filterData = RenderUtils.disableMipmap
    buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM))
    renderState.reset
    renderState.bind(buffer)

    for (side <- 0 until 6) {
      val texture = animationHandler.getAnimationIcon(stack, side)
      val color = animationHandler.getAnimationColor(stack, side)
      renderState.brightness = animationHandler.getAnimationBrightness(stack, side)
      BLOCK_MODEL.copy.setColour(color).render(renderState, 4 * side, 4 + 4 * side, new IconTransformation(texture))
    }

    Tessellator.getInstance.draw
    GlStateManager.popMatrix
    GlStateManager.pushMatrix()
    GlStateManager.enableLighting
    RenderUtils.enableMipmap(filterData)
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)
    renderState.reset
    renderState.bind(buffer)
    renderState.brightness = lastBrightness

    for (side <- 0 until 6) {
      val texture = iconProvider.getIcon(meta, EnumFacing.getFront(side))
      val color = iconProvider.getColorMultiplier(meta, EnumFacing.getFront(side))
      BLOCK_MODEL.copy.setColour(color).render(renderState, 4 * side, 4 + 4 * side, new IconTransformation(texture))
    }

    Tessellator.getInstance.draw
    GlStateManager.popMatrix
    buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM))
  }

  override def getRenderType: EnumBlockRenderType = RENDER_TYPE

}
