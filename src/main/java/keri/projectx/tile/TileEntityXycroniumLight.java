/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.tile.TileEntityBase;
import keri.projectx.ProjectX;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityXycroniumLight extends TileEntityBase {

    private Colour color = new ColourRGBA(255, 255, 255, 255);

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.color = new ColourRGBA(tag.getInteger("color"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("color", this.color.rgba());
        return tag;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public void sendUpdatePacket(BlockPos pos){
        PacketCustom packet = new PacketCustom(ProjectX.INSTANCE, 1);
        packet.writePos(pos);
        packet.sendToClients();
    }

    public void setColor(Colour color){
        this.color = color;
    }

    public Colour getColor(){
        return this.color;
    }

}
