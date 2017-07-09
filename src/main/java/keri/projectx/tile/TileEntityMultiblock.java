/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import keri.ninetaillib.lib.tile.TileEntityInventoryBase;
import keri.projectx.multiblock.IMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityMultiblock extends TileEntityInventoryBase {

    public TileEntityMultiblock(int size) {
        super(size);
    }

    public TileEntityMultiblock(int size, int stackLimit) {
        super(size, stackLimit);
    }

    public TileEntityMultiblock(){
        super(1);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.getMultiblock().readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.getMultiblock().writeToNBT(tag);
        return tag;
    }

    public void validateMultiblock(World world, BlockPos pos, EnumFacing side, EntityPlayer player){
        if(this.getMultiblock().checkMultiblock(world, pos, side, player)){
            this.getMultiblock().onFormed(world, pos, side, player);
            this.markDirty();
        }
    }

    public void invalidateMultiblock(World world, BlockPos pos, EnumFacing side, EntityPlayer player){
        if(!this.getMultiblock().checkMultiblock(world, pos, side, player)){
            this.getMultiblock().onDestroyed(world, pos, side, player);
            this.markDirty();
        }
    }

    public abstract IMultiblock getMultiblock();

}
