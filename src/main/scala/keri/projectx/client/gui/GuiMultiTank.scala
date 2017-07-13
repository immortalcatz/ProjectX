/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui

import codechicken.lib.render.RenderUtils
import codechicken.lib.vec.Rectangle4i
import keri.ninetaillib.lib.gui.GuiContainerBase
import keri.ninetaillib.lib.math.{IPositionProvider, Point2i}
import keri.projectx.container.ContainerMultitank
import keri.projectx.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer

class GuiMultiTank(inventory: InventoryPlayer, multiTank: MultiTank) extends GuiGeneric(new ContainerMultitank(inventory, multiTank)) {
  override def guiName: String = "Tank"

  override def drawBackground(): Unit = {
  }

  override def drawForeground(mouseX: Int, mouseY: Int): Unit = {
    drawFluidGauge(new Point2i(32, 12),
      GuiContainerBase.BACKGROUND_DARK,
      multiTank.tank.getFluid.getFluid,
      multiTank.tank.c_ammount.toInt,
      multiTank.tank.getCapacity,
      new IPositionProvider {
        override def getPosition = new Point2i(mouseX, mouseY)
      })
    RenderUtils.renderFluidGauge(multiTank.tank.getFluid, new Rectangle4i(24, 24, 25, 83), 16, 16)
    //    drawTankBig(24, 24)
    //    val fluid = multiTank.getTankInfo.fluid
    //
    //
    //    if (fluid != null && fluid.amount > 0) {
    //      val filled: Double = multiTank.tank.c_ammount / multiTank.tank.getCapacity
    //      val height = 58.0D * filled
    //      val buffer = Tessellator.getInstance().getBuffer
    //      RenderUtils.preFluidRender()
    //      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    //      RenderUtils.renderFluidQuad(new Vector3(25.0D, 83.0D - height, this.zLevel), new Vector3(25.0D, 83.0D, this.zLevel), new Vector3(77.0D, 83.0D, this.zLevel), new Vector3(77.0D, 83.0D - height, this.zLevel), Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFluid.getStill(fluid).toString), 16f)
    //      Tessellator.getInstance().draw()
    //    }
  }
}
