/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.tile.TileEntityBase;
import keri.projectx.ProjectX;
import keri.projectx.api.energy.EnumXynergyClass;
import keri.projectx.api.energy.EnumXynergyType;
import keri.projectx.api.energy.IXynergyHandler;
import keri.projectx.api.energy.XynergyBuffer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class TileEntityXynergyNode extends TileEntityBase implements IXynergyHandler {

    private XynergyBuffer xynergyBuffer = new XynergyBuffer();
    private EnumXynergyClass xynergyClass = EnumXynergyClass.LOW;
    private EnumXynergyType xynergyType = EnumXynergyType.STRAIGHT;
    private boolean hasCore = false;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.xynergyBuffer.readFromNBT(tag);
        this.xynergyClass = EnumXynergyClass.readFromNBT(tag);
        this.xynergyType = EnumXynergyType.readFromNBT(tag);
        this.hasCore = tag.getBoolean("has_core");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.xynergyBuffer.writeToNBT(tag);
        EnumXynergyClass.writeToNBT(tag, this.xynergyClass);
        EnumXynergyType.writeToNBT(tag, this.xynergyType);
        tag.setBoolean("has_core", this.hasCore);
        return tag;
    }

    @Override
    public void extractEnergy(int amount) {
        if(this.hasCore){
            this.xynergyBuffer.extractEnergy(amount);
        }
    }

    @Override
    public void receiveEnergy(int amount) {
        if(this.hasCore){
            this.xynergyBuffer.receiveEnergy(amount);
        }
    }

    @Override
    public int getEnergyStored() {
        return this.xynergyBuffer.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.xynergyBuffer.getXynergyClass().getCapacity();
    }

    public void setXynergyClass(EnumXynergyClass xynergyClass){
        this.xynergyClass = xynergyClass;
    }

    public void setXynergyType(EnumXynergyType xynergyType){
        this.xynergyType = xynergyType;
    }

    public void setHasCore(boolean hasCore){
        this.hasCore = hasCore;
    }

    public EnumXynergyClass getXynergyClass(){
        return this.xynergyClass;
    }

    public EnumXynergyType getXynergyType(){
        return this.xynergyType;
    }

    public boolean getHasCore(){
        return this.hasCore;
    }

    public void onUpdatePacket(PacketCustom packet){
        this.markDirty();
    }

    public void sendUpdatePacket(BlockPos pos){
        PacketCustom packet = new PacketCustom(ProjectX.INSTANCE, 2);
        packet.writePos(this.pos);
        packet.sendToClients();
    }

}
