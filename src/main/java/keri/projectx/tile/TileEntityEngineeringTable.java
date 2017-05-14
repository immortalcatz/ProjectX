package keri.projectx.tile;

import keri.ninetaillib.inventory.InternalInventory;
import keri.ninetaillib.tile.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityEngineeringTable extends TileEntityInventory {

    private InternalInventory internalInventory = new InternalInventory(this, 10);

    @Override
    public InternalInventory getInternalInventory() {
        return this.internalInventory;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

}
