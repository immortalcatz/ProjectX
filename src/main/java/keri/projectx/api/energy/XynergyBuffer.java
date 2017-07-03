/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

import keri.projectx.api.nbt.INBTHandler;
import net.minecraft.nbt.NBTTagCompound;

public class XynergyBuffer implements INBTHandler {

    private EnumXynergyClass xynergyClass;
    private int energyStored;

    public XynergyBuffer(){
        this.xynergyClass = EnumXynergyClass.LOW;
        this.energyStored = 0;
    }

    public XynergyBuffer(EnumXynergyClass xynergyClass){
        this.xynergyClass = xynergyClass;
        this.energyStored = 0;
    }

    public void setXynergyClass(EnumXynergyClass xynergyClass){
        this.xynergyClass = xynergyClass;
    }

    public EnumXynergyClass getXynergyClass(){
        return this.xynergyClass;
    }

    public int getEnergyStored(){
        return this.energyStored;
    }

    public void setEnergyStored(int energyStored){
        if(energyStored <= this.xynergyClass.getCapacity()){
            this.energyStored = energyStored;
        }
        else{
            this.energyStored = this.xynergyClass.getCapacity();
        }
    }

    public void receiveEnergy(int amount){
        int left = this.xynergyClass.getCapacity() - this.energyStored;

        if(left >= amount){
            this.energyStored += amount;
        }
        else{
            this.energyStored = this.xynergyClass.getCapacity();
        }
    }

    public void extractEnergy(int amount){
        if(amount <= this.energyStored){
            this.energyStored -= amount;
        }
        else{
            this.energyStored = 0;
        }
    }

    public void setEmpty(){
        this.energyStored = 0;
    }

    public void setFull(){
        this.energyStored = this.xynergyClass.getCapacity();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.xynergyClass = EnumXynergyClass.readFromNBT(tag);
        this.energyStored = tag.getInteger("energy_stored");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        EnumXynergyClass.writeToNBT(tag, this.xynergyClass);
        tag.setInteger("energy_stored", this.energyStored);
        return tag;
    }

}
