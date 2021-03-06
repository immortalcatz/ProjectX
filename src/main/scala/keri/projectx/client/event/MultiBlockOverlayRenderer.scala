/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.event

import codechicken.lib.render
import codechicken.lib.texture.TextureUtils
import codechicken.lib.texture.TextureUtils.IIconRegister
import keri.projectx.tile.TileEntityMultiblock
import keri.projectx.util.{ModPrefs, PlayerStareTracker}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.{TextureAtlasSprite, TextureMap}
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import net.minecraft.util.math.{BlockPos, RayTraceResult}
import net.minecraft.util.{EnumFacing, ResourceLocation}
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11

import scala.collection.JavaConversions._
import scala.collection.mutable

object MultiBlockOverlayRenderer extends IIconRegister {
  val stareTracker = new PlayerStareTracker
  var overlayTexture: TextureAtlasSprite = _

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  def onDrawBlockHighlight(event: DrawBlockHighlightEvent): Unit = {
    stareTracker.update(event.getTarget)

    val target = event.getTarget
    val world = Minecraft.getMinecraft.world
    if (target.typeOfHit == RayTraceResult.Type.BLOCK) {
      world.getTileEntity(target.getBlockPos) match {
        case tileMulti: TileEntityMultiblock =>
          val player = event.getPlayer
          GL11.glPushMatrix()

          val interpPosX: Double = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks
          val interpPosY: Double = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks
          val interpPosZ: Double = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks
          GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ)


          val alpha: Float = 1.0F - stareTracker.getStareTime / 100.0f

          GlStateManager.color(1, 1, 1, alpha)

          renderOverlay(tileMulti)

          GL11.glPopMatrix()
        case _ =>
      }
    }
  }

  def renderOverlay(tile: TileEntityMultiblock): Unit = {
    val blocksToRender = new mutable.HashSet[BlockPos]
    tile.formedMultiBlocks.foreach(multiBlock => {
      blocksToRender ++= multiBlock.inBlocks
    })
    val tess = Tessellator.getInstance().getBuffer

    GlStateManager.enableBlend()
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
    GlStateManager.depthMask(false)
    tess.reset()
    tess.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    TextureUtils.bindBlockTexture()
    blocksToRender.foreach(coord =>
      EnumFacing.VALUES.foreach(side =>
        if (!blocksToRender.contains(new BlockPos(coord).offset(side))) {
          render.RenderUtils.renderBlockOverlaySide(coord.getX, coord.getY, coord.getZ, side.getIndex, overlayTexture.getMinU, overlayTexture.getMaxU, overlayTexture.getMinV, overlayTexture.getMaxV)
        }
      )
    )
    Tessellator.getInstance().draw()
  }

  override def registerIcons(textureMap: TextureMap): Unit = {
    overlayTexture = textureMap.registerSprite(new ResourceLocation(ModPrefs.MODID, s"blocks/machine/overlay_multi_block"))
  }
}
