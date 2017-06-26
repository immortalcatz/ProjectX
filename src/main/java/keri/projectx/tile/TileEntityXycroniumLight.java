/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.network.INetworkTile;
import keri.ninetaillib.lib.tile.TileEntityBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityXycroniumLight extends TileEntityBase implements INetworkTile {

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
    public void onUpdatePacket(PacketCustom packet, Side side) {
        if(side == Side.CLIENT){
            if(packet.getType() == 1){
                this.markDirty();
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public void setColor(Colour color){
        this.color = color;
    }

    public Colour getColor(){
        return this.color;
    }

}
