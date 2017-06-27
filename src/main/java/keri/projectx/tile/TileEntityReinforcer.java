/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import codechicken.lib.util.ItemNBTUtils;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import keri.ninetaillib.lib.tile.TileEntityInventoryBase;
import keri.projectx.init.ProjectXContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityReinforcer extends TileEntityInventoryBase implements IEnergyReceiver, ITickable {

    private EnergyStorage energyStorage = new EnergyStorage(10000);
    private int progress = 0;

    public TileEntityReinforcer() {
        super(3);
    }

    @Override
    public void update() {
        if(!this.world.isRemote){
            if(this.isReinforcingMaterial(this.getStackInSlot(0)) && this.isReinforceable(this.getStackInSlot(1))){
                if(this.getStackInSlot(2).getCount() < this.getStackInSlot(2).getMaxStackSize()){
                    if(this.progress < 10){
                        this.progress++;
                    }
                    else{
                        ItemStack blockStack = this.getStackInSlot(1).copy();
                        blockStack.setCount(1);
                        this.decrStackSize(0, 1);
                        this.decrStackSize(1, 1);
                        ItemStack stack = new ItemStack(ProjectXContent.REINFORCED_BLOCK, 1, 0);
                        ItemNBTUtils.validateTagExists(stack);
                        stack.getTagCompound().setTag("block", blockStack.writeToNBT(new NBTTagCompound()));

                        if(this.getStackInSlot(2) == null || this.getStackInSlot(2).isEmpty()){
                            this.setInventorySlotContents(2, stack);
                        }
                        else{
                            ItemStack newStack = stack.copy();
                            newStack.setCount(this.getStackInSlot(2).getCount() + 1);
                            this.setInventorySlotContents(2, newStack);
                        }

                        this.progress = 0;
                    }
                }
            }
        }
    }

    private boolean isReinforcingMaterial(ItemStack stack){
        if(stack != null && !stack.isEmpty()){
            if(Block.getBlockFromItem(stack.getItem()) != null){
                Block block = Block.getBlockFromItem(stack.getItem());

                if(block == Blocks.OBSIDIAN){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isReinforceable(ItemStack stack){
        if(stack != null && !stack.isEmpty()){
            if(Block.getBlockFromItem(stack.getItem()) != null){
                Block block = Block.getBlockFromItem(stack.getItem());

                if(block.isFullBlock(block.getStateFromMeta(stack.getMetadata()))){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.energyStorage.readFromNBT(tag);
        this.progress = tag.getInteger("pogress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.energyStorage.writeToNBT(tag);
        tag.setInteger("progress", this.progress);
        return tag;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return this.energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return this.energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

}
