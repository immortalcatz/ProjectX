//package keri.projectx.machine.tile
//
//import codechicken.lib.data.{MCDataInput, MCDataOutput}
//import codechicken.lib.inventory.{InventoryRange, InventoryUtils}
//import codechicken.lib.util.ItemUtils
//import com.projectxy.core.tile.TMachineTile
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.inventory.{Container, ISidedInventory, InventoryCrafting}
//import net.minecraft.item.ItemStack
//import net.minecraft.item.crafting.{CraftingManager, IRecipe}
//import net.minecraft.nbt.NBTTagCompound
//import net.minecraft.util.{EnumFacing, ITickable}
//
//import scala.collection.JavaConversions._
//import scala.util.control.Breaks._
//
//class TileFabricator extends TMachineTile with ISidedInventory with ITickable {
//  val INVENTORY_SIZE = 19
//  val OUTPUT_SLOT_INDEX = 9
//  val CRAFTING_INVENTORY_RANGE = 0 until 9
//  val inv = Array.fill[ItemStack](INVENTORY_SIZE)(ItemStack.EMPTY)
//  var currentRecipe: IRecipe = null
//  var mode = 0
//  var powered = false
//  var prevPowered = false
//  var crafting_render_time = 0
//  //Inventory
//  var inv_changed = false
//
//  //Standard TileEntity stuff
//  override def validate(): Unit = {
//    inv_changed = true
//    super.validate()
//  }
//
//  override def update(): Unit = {
//    if (crafting_render_time > 0)
//      crafting_render_time -= 1
//    prevPowered = powered
//    powered = world.isBlockIndirectlyGettingPowered(getPos) > 0
//    if (powered != prevPowered) {
//      //mark block for update world.markAndNotifyBlock(getPos)
//    }
//    if (!world.isRemote) {
//      if (inv_changed) {
//        inv_changed = false
//        loadRecipe()
//      }
//      if (shouldCraft())
//        craft()
//    } else {
//    }
//  }
//
//  def shouldCraft(): Boolean = {
//    mode match {
//      case 0 => !powered
//      case 1 => powered
//      case 2 => powered && !prevPowered
//    }
//  }
//
//  def dropItems(): Unit = {
//    for (i <- 10 until INVENTORY_SIZE) {
//      if (inv(i) != null) {
//        //InventoryUtils.dropOnClose(inv(i), worldObj, Vector3.fromTileEntityCenter(this))
//      }
//    }
//  }
//
//  def craft(result: ItemStack, items: Array[RecipeInvStack], crafting: InventoryCrafting): Unit = {
//    for (i <- CRAFTING_INVENTORY_RANGE) {
//      val stack = crafting.getStackInSlot(i)
//      if (stack != null) {
//        crafting.decrStackSize(i, 1)
//        if (stack.getItem.hasContainerItem(stack)) {
//          var container = stack.getItem.getContainerItem(stack)
//          if (container != null) {
//            if (container.isItemStackDamageable && (container.getItemDamage > container.getMaxDamage)) {
//              container = ItemStack.EMPTY
//            }
//            crafting.setInventorySlotContents(i, container)
//          }
//        }
//      }
//    }
//  }
//
//  def updateInventories(items: Array[RecipeInvStack], result: ItemStack, crafting: InventoryCrafting): Unit = {
//    for (invstack <- items) {
//      if (invstack != null) {
//        val stack = invstack.access.inv.getStackInSlot(invstack.slot)
//        stack.shrink(1)
//        if (stack.getCount < 1)
//          invstack.access.inv.setInventorySlotContents(invstack.slot, ItemStack.EMPTY)
//        else
//          invstack.access.inv.setInventorySlotContents(invstack.slot, stack)
//      }
//    }
//    for (i <- CRAFTING_INVENTORY_RANGE) {
//      val stack = items(i)
//      val container = crafting.getStackInSlot(i)
//      if (container != null && stack != null)
//        InventoryUtils.insertItem(stack.access.inv, container, false)
//    }
//    InventoryUtils.insertItem(this, result, false)
//  }
//
//  def craft(): Unit = {
//    val result = getStackInSlot(OUTPUT_SLOT_INDEX)
//    if (result == ItemStack.EMPTY)
//      return
//    val crafting = getCrafted()
//    val items = findRecipeItems(crafting, result)
//    if (items == null)
//      return
//    replaceCurrentCrafingRecipe(crafting, items)
//    craft(result, items, crafting)
//    if (!shouldMergeStacks(items, crafting, result))
//      return
//    updateInventories(items, result, crafting)
//    if (crafting_render_time <= 1) {
//      crafting_render_time = 20
//      markDirty()
//    }
//  }
//
//  def shouldMergeStacks(items: Array[RecipeInvStack], crafting: InventoryCrafting, out: ItemStack): Boolean = {
//    for (i <- CRAFTING_INVENTORY_RANGE) {
//      val stack = items(i)
//      val container = crafting.getStackInSlot(i)
//      if (stack != null && container != null && InventoryUtils.insertItem(stack.access, container, true) > 0)
//        return false
//    }
//    if (InventoryUtils.insertItem(new InventoryRange(this, 0), out, true) > 0)
//      return false
//    true
//  }
//
//  def replaceCurrentCrafingRecipe(inventoryCrafting: InventoryCrafting, stacks: Array[RecipeInvStack]): Unit = {
//    for (i <- CRAFTING_INVENTORY_RANGE)
//      if (stacks(i) != null)
//        inventoryCrafting.setInventorySlotContents(i, ItemUtils.copyStack(stacks(i).stack, 1))
//  }
//
//  def findRecipeItems(ingredients: InventoryCrafting, out: ItemStack): Array[RecipeInvStack] = {
//    val items = Array.fill[RecipeInvStack](9)(null)
//    val foundItems = Array.fill[Boolean](9)(true)
//    for (i <- CRAFTING_INVENTORY_RANGE)
//      foundItems(i) = ingredients.getStackInSlot(i).isEmpty
//    findRecipeItems(items, foundItems, new InventoryRange(this, 0), ingredients, out)
//    for (side <- EnumFacing.values()) {
//      val inv = InventoryUtils.getInventory(getWorld, getPos.offset(side))
//      if (inv != null) {
//        val access = new InventoryRange(inv, side)
//        findRecipeItems(items, foundItems, access, ingredients, out)
//      }
//    }
//    for (i <- CRAFTING_INVENTORY_RANGE)
//      if (!foundItems(i))
//        return null
//    items
//  }
//
//  def testCraft(ingredients: InventoryCrafting, out: ItemStack, stack: ItemStack, slot: Int): Boolean = {
//    val rstack = ingredients.getStackInSlot(slot)
//    ingredients.setInventorySlotContents(slot, stack)
//    var valid = false
//    if (currentRecipe.matches(ingredients, getWorld)) {
//      valid = InventoryUtils.areStacksIdentical(out, currentRecipe.getCraftingResult(ingredients))
//    }
//    ingredients.setInventorySlotContents(slot, rstack)
//    valid
//  }
//
//  def findRecipeItems(items: Array[RecipeInvStack], foundItems: Array[Boolean], access: InventoryRange, ingredients: InventoryCrafting, out: ItemStack): Unit = {
//    for (slot <- 0 to access.lastSlot()) {
//      var stack = InventoryUtils.getExtractableStack(access.inv, slot)
//      if (stack != ItemStack.EMPTY && stack.getCount >= 1) {
//        stack = stack.copy
//        breakable {
//          for (islot <- CRAFTING_INVENTORY_RANGE) {
//            if (!foundItems(islot)) {
//              if (testCraft(ingredients, out, stack, islot)) {
//                items(islot) = new RecipeInvStack(access, slot)
//                foundItems(islot) = true
//                stack.setCount(stack.getCount - 1)
//                if (stack.getCount == 0)
//                  break()
//              }
//            }
//          }
//        }
//      }
//    }
//  }
//
//  def loadRecipe(): Unit = {
//    currentRecipe = null
//    val crafted = getCrafted()
//    val list = CraftingManager.REGISTRY
//    val recipe = list.filter(p => p.matches(crafted, world))
//    if (recipe.isEmpty) {
//      setInventorySlotContents(9, ItemStack.EMPTY)
//      return
//    }
//    currentRecipe = recipe.head
//    setInventorySlotContents(9, recipe.head.getRecipeOutput)
//  }
//
//  def getCrafted(): InventoryCrafting = {
//    val inv = new InventoryCrafting(new FabricatorCraftingInventory, 3, 3)
//    for (i <- CRAFTING_INVENTORY_RANGE) inv.setInventorySlotContents(i, getStackInSlot(i))
//    inv
//  }
//
//  //Packets and NBT saving
//  override def saveNBT(compound: NBTTagCompound): Unit = {
//    compound.setInteger("mode", mode)
//    compound.setTag("items", InventoryUtils.writeItemStacksToTag(inv))
//  }
//
//  override def receivePacket(in: MCDataInput): Unit = {
//    mode = in.readInt()
//    crafting_render_time = in.readInt()
//    for (i <- CRAFTING_INVENTORY_RANGE)
//      inv(i) = in.readItemStack()
//  }
//
//  override def writeToPacket(out: MCDataOutput): Unit = {
//    out.writeInt(mode)
//    out.writeInt(crafting_render_time)
//    for (i <- CRAFTING_INVENTORY_RANGE)
//      out.writeItemStack(getStackInSlot(i))
//  }
//
//  override def getStackInSlot(slot: Int): ItemStack = inv(slot)
//
//  override def readNBT(compound: NBTTagCompound): Unit = {
//    mode = compound.getInteger("mode")
//    InventoryUtils.readItemStacksFromTag(inv, compound.getTagList("items", 10))
//  }
//
//  override def getSlotsForFace(side: EnumFacing): Array[Int] = if (side == EnumFacing.UP) Array.empty[Int] else (10 until INVENTORY_SIZE).toArray
//
//  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = if (index >= 10) true else false
//
//  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean = if (index >= 10) true else false
//
//  override def decrStackSize(index: Int, count: Int): ItemStack = InventoryUtils.decrStackSize(this, index, count)
//
//  override def closeInventory(player: EntityPlayer): Unit = {}
//
//  override def isUsableByPlayer(player: EntityPlayer): Boolean = ???
//
//  override def getSizeInventory: Int = INVENTORY_SIZE
//
//  override def getInventoryStackLimit: Int = 64
//
//  override def clear(): Unit = {}
//
//  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = true
//
//  override def openInventory(player: EntityPlayer): Unit = {}
//
//  override def getFieldCount: Int = 0
//
//  override def getField(id: Int): Int = 0
//
//  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
//    inv(index) = stack
//    onInventoryChanged()
//  }
//
//  override def removeStackFromSlot(index: Int): ItemStack = InventoryUtils.removeStackFromSlot(this, index)
//
//  override def setField(id: Int, value: Int): Unit = {}
//
//  override def isEmpty: Boolean = false
//
//  override def getName: String = "Fabricator"
//
//  override def hasCustomName: Boolean = false
//
//  //Inventory SHIT
//  private def onInventoryChanged() = inv_changed = true
//}
//
//class FabricatorCraftingInventory extends Container {
//  override def canInteractWith(player: EntityPlayer): Boolean = true
//}
//
//case class RecipeInvStack(access: InventoryRange, slot: Int) {
//  val stack = access.inv.getStackInSlot(slot)
//}
