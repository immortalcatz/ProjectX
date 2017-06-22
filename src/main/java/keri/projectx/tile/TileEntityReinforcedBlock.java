/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import keri.ninetaillib.lib.tile.TileEntityBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityReinforcedBlock extends TileEntityBase {

    private ItemStack block = null;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if(tag.hasKey("block")){
            NBTTagCompound blockTag = tag.getCompoundTag("block");
            this.block = new ItemStack(blockTag);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        if(this.block != null){
            NBTTagCompound blockTag = this.block.writeToNBT(new NBTTagCompound());
            tag.setTag("block", blockTag);
        }

        return tag;
    }

    public void setBlock(ItemStack stack){
        this.block = stack;
    }

    public ItemStack getBlock(){
        return this.block;
    }

}
