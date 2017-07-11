/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui

import java.util

import codechicken.lib.colour.{ColourRGBA, EnumColour}
import codechicken.lib.render.CCRenderState
import codechicken.lib.texture.TextureUtils
import codechicken.lib.vec.Vector3
import keri.ninetaillib.lib.gui.GuiContainerBase
import keri.projectx.ProjectX
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.inventory.{Container, Slot}
import org.lwjgl.opengl.GL11

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

abstract class GuiCommon(container: Container) extends GuiContainerBase(container) {
  private val menus = new ArrayBuffer[Menu]()

  override def initGui(): Unit = {
    super.initGui()
    ySize = 175
    guiLeft = (width - xSize) / 2
    guiTop = (height - ySize) / 2
  }

  override def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int): Unit = {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    GL11.glTranslatef(guiLeft, guiTop, 0.0F)
    drawBackgroundSlots()
    drawBackground()
    GL11.glTranslatef(-guiLeft, -guiTop, 0.0F)
  }

  private def drawBackgroundSlots(): Unit = {
    val slots = container.inventorySlots.asInstanceOf[util.List[Slot]]
    for (slot <- slots.asScala)
      drawSlot(slot)
  }

  private def drawSlot(slot: Slot): Unit = {
    val x = slot.xPos - 1
    val y = slot.yPos - 1
    drawTexturedModalRect(x, y, 176, 10, 18, 18)
  }

  def drawTankBig(x: Int, y: Int) {
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(x, y, 34, 175, 54, 60)
  }

  def drawProgressBar(x: Int, y: Int, progress: Double, colourRGBA: ColourRGBA) {
    val renderState = CCRenderState.instance()
    renderState.reset()
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    drawBar(x, y)
    val width = 26 * progress
    TextureUtils.bindBlockTexture()
    renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    colourRGBA.glColour()
    codechicken.lib.render.RenderUtils.renderFluidQuad(
      new Vector3(x + 2, 2 + y, this.zLevel),
      new Vector3(x + 2, 8 + y, this.zLevel),
      new Vector3(x + 2 + width, 8 + y, this.zLevel),
      new Vector3(x + 2 + width, 2 + y, this.zLevel),
      ProjectX.PROXY.getAnimatedTexture, 16.0D)
    renderState.draw()
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
  }

  private def drawBar(x: Int, y: Int) {
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(x, y, 176, 0, 30, 10)
  }

  def drawButton(x: Int, y: Int, icon: TextureAtlasSprite, resourceAction: ResourceAction, selected: Boolean): Unit = {
    //If it's selected draw the animation underneath the button
    //22 is the size of the button
    if (selected) {
      TextureUtils.bindBlockTexture()
      val renderState = CCRenderState.instance()
      renderState.reset()
      renderState.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
      new ColourRGBA(0, 100, 255, 255).glColour()
      codechicken.lib.render.RenderUtils.renderFluidQuad(
        new Vector3(x, y, zLevel),
        new Vector3(x, y + 21, zLevel),
        new Vector3(x + 21, y, zLevel),
        new Vector3(x + 21, y, zLevel),
        ProjectX.PROXY.getAnimatedTexture, 16.0D)
      renderState.draw()
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    }
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(x, y, 231, 26, 22, 22)
    resourceAction.bind()
    drawTexturedModalRect(x + 3, y + 3, icon, 16, 16)
    ResourceLib.baseGui.bind()
  }

  override def drawScreen(mouseX: Int, mouseY: Int, f3: Float): Unit = {
    this.drawDefaultBackground()
    super.drawScreen(mouseX, mouseY, f3)
    menus.foreach(menu => menu.manageTooltips((mouseX, mouseY)))
  }

  override def drawGuiContainerForegroundLayer(par1: Int, par2: Int): Unit = {
    fontRendererObj.drawString(guiName, xSize / 2 - fontRendererObj.getStringWidth(guiName) / 2, 8, EnumColour.LIGHT_GRAY.rgb())
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    GL11.glDisable(GL11.GL_LIGHTING)
    drawForeground()
    GL11.glEnable(GL11.GL_LIGHTING)
  }

  override def mouseClicked(x: Int, y: Int, button: Int): Unit = {
    menus.foreach(menu => menu.mouseClicked((x, y), button))
    super.mouseClicked(x, y, button)
  }

  def addMenu(menu: Menu): Menu = {
    menus += menu
    menu
  }

  def guiName: String

  def drawForeground()

  def drawBackground()
}
