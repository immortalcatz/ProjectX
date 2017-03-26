package keri.projectx.tile;

import keri.ninetaillib.tile.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityQuartzCrystal extends TileEntityBase {

    private EnumFacing orientation = EnumFacing.DOWN;
    private int quantity = 1;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.orientation = EnumFacing.getFront(tag.getInteger("orientation"));
        this.quantity = tag.getInteger("quantity");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("orientation", this.orientation.getIndex());
        tag.setInteger("quantity", this.quantity);
        return tag;
    }

    public void setOrientation(EnumFacing orientation){
        this.orientation = orientation;
    }

    public EnumFacing getOrientation(){
        return this.orientation;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

}
