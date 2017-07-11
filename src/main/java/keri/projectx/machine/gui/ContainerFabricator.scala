//package keri.projectx.machine.gui
//
//import codechicken.lib.inventory.container.{ContainerExtended, SlotDummy, SlotDummyOutput}
//import codechicken.lib.packet.PacketCustom
//import keri.projectx.machine.tile.TileFabricator
//import com.projectxy.world.ProjectXYWorld
//import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
//import net.minecraft.inventory.{ClickType, Slot}
//import net.minecraft.item.ItemStack
//import net.minecraftforge.fml.relauncher.{Side, SideOnly}
//
//class ContainerFabricator(inventoryPlayer: InventoryPlayer, tile: TileFabricator) extends ContainerExtended {
//  for (row <- 0 until 3)
//    for (column <- 0 until 3)
//      addSlotToContainer(new SlotDummy(tile, row * 3 + column, 8 + column * 18, 30 + row * 18, 1) {
//        override def canTakeStack(player: EntityPlayer): Boolean = false
//      })
//  addSlotToContainer(new SlotDummyOutput(tile, 9, 79, 48) {
//    override def slotClick(container: ContainerExtended, player: EntityPlayer, button: Int, modifier: ClickType): ItemStack = {
//      //Clears crafting in one click
//      if (button == 1)
//        for (i <- 0 until 9)
//          tile.setInventorySlotContents(i, ItemStack.EMPTY)
//      ItemStack.EMPTY
//    }
//
//    override def canTakeStack(player: EntityPlayer): Boolean = false
//  })
//  for (row <- 0 until 3)
//    for (column <- 0 until 3)
//      addSlotToContainer(new Slot(tile, row * 3 + column + 10, 116 + column * 18, 30 + row * 18))
//  bindPlayerInventory(inventoryPlayer, 8, 93)
//
//  val REDSTONE_SIGNAL_CHANGE = 1
//  val CRAFTING_SLOTS_CHANGED = 2
//
//  override def doMergeStackAreas(slotIndex: Int, stack: ItemStack): Boolean = {
//    if (slotIndex < 19) return mergeItemStack(stack, 19, 55, true)
//    mergeItemStack(stack, 10, 19, false)
//  }
//
//  @SideOnly(Side.CLIENT)
//  def handleGuiChange(selected_redstone_signal: Int): Unit = {
//    tile.mode = selected_redstone_signal
//    tile.markDirty()
//    val packet = getPacket()
//    packet.writeInt(REDSTONE_SIGNAL_CHANGE)
//    packet.writeInt(selected_redstone_signal)
//    packet.sendToServer()
//  }
//
//  def getPacket(): PacketCustom = new PacketCustom(ProjectXYWorld.MOD_ID, 1)
//
//  def changeCraftingRecipe(stacks: Array[ItemStack]): Unit = {
//    val packet = getPacket()
//    packet.writeInt(CRAFTING_SLOTS_CHANGED)
//    for (i <- stacks.indices) {
//      packet.writeItemStack(stacks(i))
//    }
//    packet.sendToServer()
//  }
//
//  def handleServerPacket(packet: PacketCustom): Unit = {
//    packet.readInt match {
//      case REDSTONE_SIGNAL_CHANGE =>
//        tile.mode = packet.readInt
//        tile.markDirty()
//      case CRAFTING_SLOTS_CHANGED =>
//        for (i <- 0 until 9)
//          tile.setInventorySlotContents(i, packet.readItemStack())
//      case _ =>
//    }
//  }
//}