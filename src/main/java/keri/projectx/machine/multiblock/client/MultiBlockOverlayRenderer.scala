package keri.projectx.machine.multiblock.client

import codechicken.lib.render
import codechicken.lib.texture.TextureUtils
import codechicken.lib.texture.TextureUtils.IIconRegister
import keri.projectx.machine.multiblock.tile.TileMultiBlock
import com.projectxy.world.ProjectXYWorld
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
  var overlayTexture: TextureAtlasSprite = _


  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  def onDrawBlockHighlight(event: DrawBlockHighlightEvent): Unit = {
    val target = event.getTarget
    val world = Minecraft.getMinecraft.world
    if (target.typeOfHit == RayTraceResult.Type.BLOCK) {
      world.getTileEntity(target.getBlockPos) match {
        case tileMulti: TileMultiBlock =>
          val player = event.getPlayer
          GL11.glPushMatrix()
          //Translate player coords to world coords used in entities
          val interpPosX: Double = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks
          val interpPosY: Double = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks
          val interpPosZ: Double = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks
          GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ)
          renderOverlay(tileMulti)
          GL11.glPopMatrix()
        case _ =>
      }
    }
  }

  def renderOverlay(tile: TileMultiBlock): Unit = {
    val blocksToRender = new mutable.HashSet[BlockPos]
    for (multiBlock <- tile.formedMultiBlocks) {
      blocksToRender ++= multiBlock.inBlocks
    }
    val tess = Tessellator.getInstance().getBuffer

    GlStateManager.enableBlend()
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
    GlStateManager.depthMask(false)

    TextureUtils.bindBlockTexture()
    for (coord <- blocksToRender) {
      for (side <- EnumFacing.VALUES) {
        if (!blocksToRender.contains(new BlockPos(coord).offset(side))) {
          tess.reset()
          tess.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
          //tess.color(1, 1, 1, .25f)
          render.RenderUtils.renderBlockOverlaySide(coord.getX, coord.getY, coord.getZ, side.getIndex, overlayTexture.getMinU, overlayTexture.getMaxU, overlayTexture.getMinV, overlayTexture.getMaxV)
          Tessellator.getInstance().draw()
        }
      }
    }
  }

  override def registerIcons(textureMap: TextureMap): Unit = {
    overlayTexture = textureMap.registerSprite(new ResourceLocation(ProjectXYWorld.MOD_ID, s"blocks/overlay/overlay_multi_block"))
  }
}
