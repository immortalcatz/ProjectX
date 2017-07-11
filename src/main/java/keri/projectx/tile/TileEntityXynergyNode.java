/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile;

import com.google.common.collect.Lists;
import keri.projectx.api.energy.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.List;

public class TileEntityXynergyNode extends TileEntityProjectX implements IXynergyHandler, IXynergyConnector {

    private XynergyBuffer xynergyBuffer = new XynergyBuffer();
    private EnumXynergyClass xynergyClass = EnumXynergyClass.LOW;
    private EnumXynergyType xynergyType = EnumXynergyType.STRAIGHT;
    private boolean hasCore = false;
    private List<BlockPos> connectedDevices = Lists.newArrayList();

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.xynergyBuffer.readFromNBT(tag);
        this.xynergyClass = EnumXynergyClass.readFromNBT(tag);
        this.xynergyType = EnumXynergyType.readFromNBT(tag);
        this.hasCore = tag.getBoolean("has_core");
        int[] connectedDevicesX = tag.getIntArray("connected_devices_x");
        int[] connectedDevicesY = tag.getIntArray("connected_devices_y");
        int[] connectedDevicesZ = tag.getIntArray("connected_devices_z");

        for(int index = 0; index < connectedDevicesX.length; index++){
            int posX = connectedDevicesX[index];
            int posY = connectedDevicesY[index];
            int posZ = connectedDevicesZ[index];
            this.connectedDevices.add(new BlockPos(posX, posY, posZ));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.xynergyBuffer.writeToNBT(tag);
        EnumXynergyClass.writeToNBT(tag, this.xynergyClass);
        EnumXynergyType.writeToNBT(tag, this.xynergyType);
        tag.setBoolean("has_core", this.hasCore);
        int[] connectedDevicesX = new int[this.connectedDevices.size()];
        int[] connectedDevicesY = new int[this.connectedDevices.size()];
        int[] connectedDevicesZ = new int[this.connectedDevices.size()];

        for(int index = 0; index < this.connectedDevices.size(); index++){
            BlockPos pos = this.connectedDevices.get(index);
            connectedDevicesX[index] = pos.getX();
            connectedDevicesY[index] = pos.getY();
            connectedDevicesZ[index] = pos.getZ();
        }

        tag.setIntArray("connected_devices_x", connectedDevicesX);
        tag.setIntArray("connected_devices_y", connectedDevicesY);
        tag.setIntArray("connected_devices_z", connectedDevicesZ);
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

    @Override
    public boolean canConnectEnergy() {
        return true;
    }

    @Override
    public Collection<BlockPos> getConnectedDevices() {
        return this.connectedDevices;
    }

    @Override
    public void addConnection(BlockPos pos) {
        this.connectedDevices.add(pos);
    }

    @Override
    public void removeConnection(BlockPos pos) {
        this.connectedDevices.remove(pos);
    }

    public void setXynergyClass(EnumXynergyClass xynergyClass){
        this.xynergyClass = xynergyClass;
        this.xynergyBuffer.setXynergyClass(xynergyClass);
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

}
