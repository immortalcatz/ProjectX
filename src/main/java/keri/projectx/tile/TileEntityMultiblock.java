package keri.projectx.tile;

import keri.ninetaillib.inventory.InternalInventory;
import keri.ninetaillib.tile.TileEntityInventory;
import keri.projectx.multiblock.IMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityMultiblock extends TileEntityInventory {

    private boolean isFormed = false;
    private boolean isMaster = false;

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        NBTTagCompound multiblockTag = tag.getCompoundTag("multiblock_data");
        this.isFormed = multiblockTag.getBoolean("is_formed");
        this.isMaster = multiblockTag.getBoolean("is_master");
        this.getMultiblock().readMultiblockNBT(multiblockTag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagCompound multiblockTag = this.getMultiblock().writeMultiblockNBT(new NBTTagCompound());
        multiblockTag.setBoolean("is_formed", this.isFormed);
        multiblockTag.setBoolean("is_master", this.isMaster);
        tag.setTag("multiblock_data", multiblockTag);
        return tag;
    }

    @Override
    public InternalInventory getInternalInventory() {
        return new InternalInventory(this, 1);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    public boolean getIsFormed(){
        return this.isFormed;
    }

    public void setIsMaster(boolean isMaster){
        this.isMaster = isMaster;
    }

    public boolean getIsMaster(){
        return this.isMaster;
    }

    public void checkMultiblock(EntityPlayer player, EnumFacing side){
        if(this.getMultiblock().isValid(this.worldObj, this.pos, player, side)){
            this.getMultiblock().onFormed(this.worldObj, this.pos, player, side);
            this.isFormed = true;
        }
        else{
            this.isFormed = false;
        }
    }

    public abstract IMultiblock getMultiblock();

}
