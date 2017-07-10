package keri.projectx.machine.gui

import codechicken.lib.texture.TextureUtils
import com.projectxy.core.gui.{GuiCommon, Menu, ResourceAction}
import keri.projectx.machine.tile.TileFabricator
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.player.InventoryPlayer

case class GuiFabricator(inventory: InventoryPlayer, tile: TileFabricator) extends GuiCommon(new ContainerFabricator(inventory, tile)) {
  val redstone_control = addMenu(new Menu(this, 0, 12)
    .addMenuItem("Auto: Low",
      TextureUtils.getBlockTexture("redstone_torch_off"),
      new ResourceAction(TextureMap.LOCATION_BLOCKS_TEXTURE))
    .addMenuItem("Auto: High",
      TextureUtils.getBlockTexture("redstone_torch_on"),
      new ResourceAction(TextureMap.LOCATION_BLOCKS_TEXTURE))
    .addMenuItem("Pulse", TextureUtils.getItemTexture("redstone_dust"),
      new ResourceAction(TextureMap.LOCATION_BLOCKS_TEXTURE)))
  redstone_control.addCallback(onRedstoneControlSelected)
  redstone_control.setSelected(tile.mode)

  override def guiName: String = "Fabricator"

  override def drawBackground(): Unit = {}

  override def drawForeground(): Unit = {
    redstone_control.draw()
  }

  def onRedstoneControlSelected(selected: Int): Unit = inventorySlots.asInstanceOf[ContainerFabricator].handleGuiChange(selected)
}
