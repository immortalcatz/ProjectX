package keri.projectx.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

    protected void addPlayerInventory(InventoryPlayer inventoryPlayer, int offsetX, int offsetY){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18 + offsetX, offsetY + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18 + offsetX, 58 + offsetY));
        }
    }

    protected ItemStack transferStack(EntityPlayer player, int index) {
        int inventoryStart = 9;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()){
            ItemStack newStack = slot.getStack();
            ItemStack currentStack = newStack.copy();

            if(index >= inventoryStart){
                if(!this.mergeItemStack(newStack, 0, 9, false)){
                    if(index >= inventoryStart && index <= inventoryEnd){
                        if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)){
                            return ItemStack.EMPTY;
                        }
                    }
                    else if(index >= inventoryEnd + 1 && index < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)){
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)){
                return ItemStack.EMPTY;
            }

            if(!this.isValid(newStack)){
                slot.putStack(ItemStack.EMPTY);
            }
            else{
                slot.onSlotChanged();
            }

            if(newStack.getCount() == currentStack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(player, newStack);
            return currentStack;
        }

        return ItemStack.EMPTY;
    }

    private boolean isValid(ItemStack stack){
        return stack != null && !stack.isEmpty();
    }

}
