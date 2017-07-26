/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */
package keri.projectx.client.gui.machine

import codechicken.lib.texture.TextureUtils
import keri.projectx.ProjectX
import keri.projectx.api.color.EnumXycroniumColor
import keri.projectx.client.gui.GuiGeneric
import keri.projectx.container.ContainerFabricator
import keri.projectx.tile.TileEntityFabricator
import keri.projectx.util.ResourceLib
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.InventoryPlayer
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

@SideOnly(Side.CLIENT)
class GuiFaricator(val inventoryPlayer: InventoryPlayer, var tile: TileEntityFabricator) extends GuiGeneric(new ContainerFabricator(inventoryPlayer, tile)) {
  override def guiName: String = "Fabricator"


  override def drawBackgroundSlots(): Unit = {
    TextureUtils.bindBlockTexture()
    EnumXycroniumColor.BLUE.getColor.glColour()
    drawTexturedModalRect(6, 28, ProjectX.PROXY.getAnimatedTexture, 56, 56)
    EnumXycroniumColor.GREEN.getColor.glColour()
    drawTexturedModalRect(77, 46, ProjectX.PROXY.getAnimatedTexture, 20, 20)
    EnumXycroniumColor.RED.getColor.glColour()
    drawTexturedModalRect(114, 28, ProjectX.PROXY.getAnimatedTexture, 56, 56)
    GlStateManager.color(1, 1, 1, 1)
    ResourceLib.baseGui.bind()
    super.drawBackgroundSlots()
  }

  override def drawForeground(mouseX: Int, mouseY: Int): Unit = {

  }

  override def drawBackground(): Unit = {

  }
}