package keri.projectx.tile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import keri.ninetaillib.inventory.InternalInventory;
import keri.ninetaillib.tile.TileEntityInventory;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.multiblock.IMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public abstract class TileEntityMultiblock extends TileEntityInventory {

    private EnumFacing multiblockOrientation = EnumFacing.NORTH;
    private boolean isMaster = false;
    private boolean isSlave = false;
    private BlockPos masterBlock = null;
    private List<BlockPos> slaveBlocks = Lists.newArrayList();

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.multiblockOrientation = EnumFacing.getFront(tag.getInteger("multiblock_orientation"));
        this.isMaster = tag.getBoolean("multiblock_is_master");
        this.isSlave = tag.getBoolean("multiblock_is_slave");

        if(tag.hasKey("multiblock_master_block")){
            this.masterBlock = this.readBlockPos("multiblock_master_block", tag);
        }

        this.slaveBlocks = CommonUtils.readPosList("multiblock_slave_blocks", tag);
        this.getMultiblock().readMultiblockNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("multiblock_orientation", this.multiblockOrientation.getIndex());
        tag.setBoolean("multiblock_is_master", this.isMaster);
        tag.setBoolean("multiblock_is_slave", this.isSlave);

        if(this.masterBlock != null){
            this.writeBlockPos("multiblock_master_block", tag, this.masterBlock);
        }

        CommonUtils.writePosList("multiblock_slave_blocks", tag, this.slaveBlocks);
        this.getMultiblock().writeMultiblockNBT(tag);
        return tag;
    }

    @Override
    public void invalidate() {
        if(this.getIsMaster()){
            for(BlockPos partPos : this.getSlaveBlocks()){
                if(this.worldObj.getTileEntity(partPos) != null && !this.worldObj.isAirBlock(partPos)){
                    TileEntityMultiblock partTile = (TileEntityMultiblock)this.worldObj.getTileEntity(partPos);
                    partTile.invalidateMultiblock();
                }
            }
        }
        else if(this.getIsSlave()){
            TileEntityMultiblock masterTile = (TileEntityMultiblock)this.worldObj.getTileEntity(this.getMasterBlock());

            for(BlockPos partPos : masterTile.getSlaveBlocks()){
                if(this.worldObj.getTileEntity(partPos) != null && !this.worldObj.isAirBlock(partPos)){
                    TileEntityMultiblock partTile = (TileEntityMultiblock)this.worldObj.getTileEntity(partPos);
                    partTile.invalidateMultiblock();
                }
            }

            masterTile.invalidateMultiblock();
        }

        super.invalidate();
    }

    private BlockPos readBlockPos(String name, NBTTagCompound tag){
        return new BlockPos(
                tag.getInteger(name + "_x"),
                tag.getInteger(name + "_y"),
                tag.getInteger(name + "_z")
        );
    }

    private void writeBlockPos(String name, NBTTagCompound tag, BlockPos pos){
        tag.setInteger(name + "_x", pos.getX());
        tag.setInteger(name + "_y", pos.getY());
        tag.setInteger(name + "_z", pos.getZ());
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

    public void setMultiblockOrientation(EnumFacing orientation){
        this.multiblockOrientation = orientation;
    }

    public EnumFacing getMultiblockOrientation(){
        return this.multiblockOrientation;
    }

    public void setIsMaster(boolean isMaster){
        this.isMaster = isMaster;
    }

    public boolean getIsMaster(){
        return this.isMaster;
    }

    public void setIsSlave(boolean isSlave){
        this.isSlave = isSlave;
    }

    public boolean getIsSlave(){
        return this.isSlave;
    }

    public void setMasterBlock(BlockPos pos){
        this.masterBlock = pos;
    }

    public BlockPos getMasterBlock(){
        return this.masterBlock;
    }

    public void addSlaveBlock(BlockPos pos){
        this.slaveBlocks.add(pos);
    }

    public ImmutableList<BlockPos> getSlaveBlocks(){
        return ImmutableList.copyOf(this.slaveBlocks);
    }

    public void clearSlaveBlocks(){
        this.slaveBlocks.clear();
    }

    public void checkMultiblock(EntityPlayer player, EnumFacing side){
        if(this.getMultiblock().isValid(this.worldObj, this.pos, player, side)){
            this.getMultiblock().onFormed(this.worldObj, this.pos, player, side);
            this.setMultiblockOrientation(side);
            this.setIsMaster(true);
            this.setIsSlave(false);
            this.setMasterBlock(this.pos);
            this.markDirty();
        }
        else{
            this.setMultiblockOrientation(EnumFacing.NORTH);
            this.setIsMaster(false);
            this.setIsSlave(false);
            this.setMasterBlock(null);
            this.markDirty();
        }
    }

    public void invalidateMultiblock(){
        this.getMultiblock().invalidate(this.worldObj, this.pos);
        this.setMultiblockOrientation(EnumFacing.NORTH);
        this.setIsMaster(false);
        this.setIsSlave(false);
        this.setMasterBlock(null);
        this.markDirty();
    }

    public abstract IMultiblock getMultiblock();

}
