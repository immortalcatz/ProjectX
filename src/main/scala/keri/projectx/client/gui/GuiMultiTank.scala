/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.gui

import java.text.DecimalFormat

import codechicken.lib.colour.EnumColour
import codechicken.lib.render.RenderUtils
import codechicken.lib.vec.Rectangle4i
import keri.ninetaillib.lib.gui.GuiContainerBase
import keri.ninetaillib.lib.math.{IPositionProvider, Point2i}
import keri.projectx.container.ContainerMultitank
import keri.projectx.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer

class GuiMultiTank(inventory: InventoryPlayer, multiTank: MultiTank) extends GuiGeneric(new ContainerMultitank(inventory, multiTank)) {
  val format = new DecimalFormat("#,###.##")

  override def guiName: String = "Tank"

  override def drawBackground(): Unit = {
  }

  override def drawForeground(mouseX: Int, mouseY: Int): Unit = {
    drawFluidGauge(new Point2i(32, 5),
      GuiContainerBase.BACKGROUND_DARK,
      multiTank.tank.getFluid.getFluid,
      multiTank.tank.c_ammount.toInt,
      multiTank.tank.getCapacity,
      new IPositionProvider {
        override def getPosition = new Point2i(mouseX, mouseY)
      })

    val fluidStored = s"${format.format(multiTank.tank.getFluidAmount.toDouble / 1000)} B / ${format.format(multiTank.tank.getCapacity.toDouble / 1000)} B"
    fontRendererObj.drawString(fluidStored, 10, 80, EnumColour.WHITE.rgb())
    RenderUtils.renderFluidGauge(multiTank.tank.getFluid, new Rectangle4i(24, 24, 25, 83), 16, 16)
  }
}
