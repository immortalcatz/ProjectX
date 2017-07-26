/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.tile;

import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.Lists;
import keri.projectx.ProjectX;
import keri.projectx.api.energy.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.List;

public class TileEntityXynergyNode extends TileEntityProjectX implements IXynergyHandler, IXynergyConnector, ITickable {

    private XynergyBuffer xynergyBuffer = new XynergyBuffer();
    private EnumXynergyClass xynergyClass = EnumXynergyClass.LOW;
    private EnumXynergyType xynergyType = EnumXynergyType.STRAIGHT;
    private boolean hasCore = false;
    private List<BlockPos> connectedDevices = Lists.newArrayList();
    private boolean hasChanged = false;

    @Override
    public void update() {
        if(!world.isRemote){
            if(this.hasChanged){
                for(BlockPos connection : this.connectedDevices){
                    if(world.getBlockState(connection) == null || world.getTileEntity(connection) == null){
                        this.connectedDevices.remove(connection);
                    }
                }

                this.hasChanged = false;
            }
        }
    }

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

        this.hasChanged = tag.getBoolean("has_changed");
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
        tag.setBoolean("has_changed", this.hasChanged);
        return tag;
    }

    @Override
    public void extractEnergy(int amount) {
        this.xynergyBuffer.extractEnergy(amount);
    }

    @Override
    public void receiveEnergy(int amount) {
        this.xynergyBuffer.receiveEnergy(amount);
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
        return this.hasCore;
    }

    @Override
    public Collection<BlockPos> getConnectedDevices() {
        return this.connectedDevices;
    }

    @Override
    public void invalidate(){
        this.connectedDevices.forEach(this::removeDevice);
        super.invalidate();
    }

    public EnumXynergyClass getXynergyClass() {
        return this.xynergyClass;
    }

    public void setXynergyClass(EnumXynergyClass xynergyClass){
        this.xynergyClass = xynergyClass;
        this.xynergyBuffer.setXynergyClass(xynergyClass);
    }

    public EnumXynergyType getXynergyType(){
        return this.xynergyType;
    }

    public void setXynergyType(EnumXynergyType xynergyType) {
        this.xynergyType = xynergyType;
    }

    public boolean getHasCore(){
        return this.hasCore;
    }

    public void setHasCore(boolean hasCore) {
        this.hasCore = hasCore;
    }

    public void addDevice(BlockPos pos){
        this.connectedDevices.add(pos);
        this.sendAddDevicePacket(pos);
        this.hasChanged = true;
    }

    public void removeDevice(BlockPos pos){
        this.connectedDevices.remove(pos);
        this.sendRemoveDevicePacket(pos);
        this.hasChanged = true;
    }

    private void sendAddDevicePacket(BlockPos pos){
        PacketCustom packet = new PacketCustom(ProjectX.INSTANCE, 6);
        packet.writePos(this.pos);
        packet.writePos(pos);
        packet.compress().sendToClients();
    }

    private void sendRemoveDevicePacket(BlockPos pos){
        PacketCustom packet = new PacketCustom(ProjectX.INSTANCE, 7);
        packet.writePos(this.pos);
        packet.writePos(pos);
        packet.compress().sendToClients();
    }

}
