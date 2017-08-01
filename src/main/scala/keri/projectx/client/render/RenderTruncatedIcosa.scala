/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render

import codechicken.lib.colour.{Colour, ColourRGBA}
import codechicken.lib.math.MathHelper
import codechicken.lib.vec.Vector3
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11
import keri.projectx.util.ThreadLocal

@SideOnly(Side.CLIENT)
class RenderTruncatedIcosa {

  private var verts: Array[Vector3] = Array.fill[Vector3](60)(null)
  private var listIndex = 0
  generate()

  def render(size: Double, colorPent: Int, colorHex: Int, texture: EnumHedronTexture): Unit = {
    render(size, new ColourRGBA(colorPent), new ColourRGBA(colorHex), texture)
  }

  def render(size: Double, colorPent: Colour, colorHex: Colour, texture: EnumHedronTexture): Unit = {
    GlStateManager.pushMatrix
    GlStateManager.pushAttrib
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glDisable(GL11.GL_LIGHT0)
    GL11.glDisable(GL11.GL_LIGHT1)
    Minecraft.getMinecraft.getTextureManager.bindTexture(texture.getTexture)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glScaled(size * 0.1D, size * 0.1D, size * 0.1D)
    colorPent.glColour
    GL11.glCallList(listIndex)
    colorHex.glColour
    GL11.glCallList(listIndex + 1)
    GL11.glScaled(1D / (size * 0.1D), 1D / (size * 0.1D), 1D / (size * 0.1D))
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glEnable(GL11.GL_LIGHT1)
    GL11.glEnable(GL11.GL_LIGHT0)
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glColor4f(1F, 1F, 1F, 1F)
    GlStateManager.popAttrib
    GlStateManager.popMatrix
  }

  private def generate(): Unit = {
    val s = Array(1, -1)
    for (i <- 0 until 4) verts(i) = new Vector3(0D, s(i / 2), s(i % 2) * 3 * MathHelper.phi)
    for (i <- 0 until 8) {
      verts(i + 4) = new Vector3(s(i / 4) * 2, s(i / 2 % 2) * 4.2360679999999995D, s(i % 2) * MathHelper.phi)
      verts(i + 12) = new Vector3(s(i / 4), s(i / 2 % 2) * 3.6180339999999998D, s(i % 2) * 2 * MathHelper.phi)
    }
    for (i <- 0 until 20) {
      verts(i + 20) = new Vector3(verts(i).y, verts(i).z, verts(i).x)
      verts(i + 40) = new Vector3(verts(i).z, verts(i).x, verts(i).y)
    }
    listIndex = GL11.glGenLists(2)
    GL11.glNewList(listIndex, GL11.GL_COMPILE)
    GL11.glBegin(GL11.GL_LINE_BIT)
    for (rot <- 0 until 3) {
      for (i <- 0 until 4) {
        hexagon1(rot, i)
      }
    }
    for (i <- 0 until 8) hexagon2(i)
    GL11.glEnd
    GL11.glEndList
  }

  private def pentagon(rot: Int, i: Int): Unit = {
    val pentagon = new Array[Vector3](5)
    pentagon(0) = verts(rot * 20 + i)
    pentagon(1) = verts((rot * 20 + 2 * i + 44) % 60)
    pentagon(2) = verts((rot * 20 + i + 12) % 60)
    pentagon(3) = verts((rot * 20 + i + 16) % 60)
    pentagon(4) = verts((rot * 20 + 2 * i + 45) % 60)
    renderShape(pentagon, (i != 0) && (i != 3))
  }

  private def hexagon1(rot: Int, i: Int): Unit = {
    val hexagon = new Array[Vector3](6)
    hexagon(0) = verts(rot * 20 + i / 2)
    hexagon(1) = verts((rot * 20 + i + 44) % 60)
    hexagon(2) = verts((rot * 20 + i + 52) % 60)
    hexagon(3) = verts((rot * 20 + i + 56) % 60)
    hexagon(4) = verts((rot * 20 + i + 48) % 60)
    hexagon(5) = verts(rot * 20 + i / 2 + 2)
    renderShape(hexagon, (i == 0) || (i == 3))
  }

  private def hexagon2(i: Int): Unit = {
    val hexagon = new Array[Vector3](6)
    hexagon(0) = verts(4 + i)
    hexagon(1) = verts(12 + i)
    hexagon(2) = verts(44 + i / 4 + i % 4 * 2)
    hexagon(3) = verts(52 + i / 4 + i % 4 * 2)
    hexagon(4) = verts(24 + i / 2 + i % 2 * 4)
    hexagon(5) = verts(32 + i / 2 + i % 2 * 4)
    renderShape(hexagon, (i % 3 != 0) && (i != 5))
  }

  private def renderShape(verts: Array[Vector3], reverse: Boolean): Unit = {
    var center = Vector3.zero
    verts.foreach(center.add)
    center.multiply(1D / verts.length)
    var prev = verts(0)
    var start = if (reverse) verts.length else 1
    var end = if (reverse) -1 else verts.length + 2
    var step = if (reverse) -1 else 1
    for (i <- Range.apply(start, end, step)) {
      GL11.glTexCoord2d(0.5D, 0.5D)
      GL11.glVertex3d(center.x, center.y, center.z)
      GL11.glTexCoord2d(0D, 0D)
      GL11.glVertex3d(prev.x, prev.y, prev.z)
      GL11.glTexCoord2d(1D, 0D)
      GL11.glVertex3d(verts(i % verts.length).x, verts(i % verts.length).y, verts(i % verts.length).z)
      prev = verts(i % verts.length)
    }
  }

}

object RenderTruncatedIcosa {

  private final val instance: ThreadLocal[RenderTruncatedIcosa] = new ThreadLocal[RenderTruncatedIcosa](new RenderTruncatedIcosa)

  def getInstance(): RenderTruncatedIcosa = instance.get

}
