package keri.projectx.machine.gui

import codechicken.lib.render.RenderUtils
import codechicken.lib.vec.Vector3
import keri.projectx.client.gui.GuiCommon
import keri.projectx.machine.multiblock.MultiTank
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.InventoryPlayer
import org.lwjgl.opengl.GL11

class GuiMultiTank(inventory: InventoryPlayer, multiTank: MultiTank) extends GuiCommon(new ContainerMultitank(inventory, multiTank)) {
  override def guiName: String = "Tank"

  override def drawBackground(): Unit = {
  }

  override def drawForeground(): Unit = {
    drawTankBig(24, 24)
    val fluid = multiTank.getTankInfo.fluid


    if (fluid != null && fluid.amount > 0) {
      val filled: Double = multiTank.tank.c_ammount / multiTank.tank.getCapacity
      val height = 58.0D * filled
      val buffer = Tessellator.getInstance().getBuffer
      RenderUtils.preFluidRender()
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
      RenderUtils.renderFluidQuad(new Vector3(25.0D, 83.0D - height, this.zLevel), new Vector3(25.0D, 83.0D, this.zLevel), new Vector3(77.0D, 83.0D, this.zLevel), new Vector3(77.0D, 83.0D - height, this.zLevel), Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFluid.getStill(fluid).toString), 16f)
      Tessellator.getInstance().draw()
    }

  }
}
