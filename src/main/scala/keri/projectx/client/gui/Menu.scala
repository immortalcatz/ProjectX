/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui

import codechicken.lib.gui.GuiDraw
import codechicken.lib.vec.Rectangle4i
import net.minecraft.client.renderer.texture.TextureAtlasSprite

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Adam on 7/10/2017.
  */
case class Menu(gui: GuiGeneric, x: Int, y: Int) {
  val menuItems = new ArrayBuffer[MenuItem]()
  val callbacks = new ArrayBuffer[Int => Unit]()
  var selected = 0

  def addCallback(callback: Int => Unit) = callbacks += callback

  def addMenuItem(tooltip: String, icon: TextureAtlasSprite, resourceAction: ResourceAction): Menu = {
    menuItems += new MenuItem(gui, tooltip, icon, resourceAction)
    this
  }

  def draw(): Unit = {
    ResourceLib.baseGui.bind()
    //Top
    gui.drawTexturedModalRect(x - 35, y + 6, 222, 0, 34, 5)
    //Bottom
    gui.drawTexturedModalRect(x - 35, y + 33 + 24 * (menuItems.length - 1), 222, 21, 34, 5)
    //Back
    menuItems.indices.foreach(i => {
      gui.drawTexturedModalRect(x - 35, y + 11 + 24 * i, 222, 2, 34, 22)
      gui.drawTexturedModalRect(x - 35, y + 33 + 24 * i, 222, 2, 34, 2)
    })
    //Button
    menuItems.indices.foreach(i => gui.drawButton(this.x - 29, this.y + 11 + 24 * i, menuItems(i).icon, menuItems(i).resourceAction, i == selected))
  }

  def mouseClicked(mouse: (Int, Int), button: Int): Unit = {
    for (itemIndex <- menuItems.indices) {
      if (isButtonIn(mouse, itemIndex)) {
        selected = itemIndex
        callbacks.foreach(callback => callback(itemIndex))
        //gui.mc.getSoundHandler.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F))
      }
    }
  }

  def isButtonIn(mouse: (Int, Int), index: Int): Boolean = new Rectangle4i(x - 28 + gui.getGuiLeft, y + 25 * index + gui.getGuiTop + 11, 22, 22).contains(mouse._1, mouse._2)

  def setSelected(selected: Int) = this.selected = selected

  def manageTooltips(mouse: (Int, Int)): Unit = {

    for (itemIndex <- menuItems.indices) {
      if (isButtonIn(mouse, itemIndex)) {
        GuiDraw.drawTip(mouse._1, mouse._2, menuItems(itemIndex).tooltip)
      }
    }
  }
}

case class MenuItem(gui: GuiGeneric, tooltip: String, icon: TextureAtlasSprite, resourceAction: ResourceAction)
