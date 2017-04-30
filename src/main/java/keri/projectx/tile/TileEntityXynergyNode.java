package keri.projectx.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import com.google.common.collect.Lists;
import keri.ninetaillib.network.INetworkTile;
import keri.ninetaillib.tile.TileEntityBase;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public class TileEntityXynergyNode extends TileEntityBase implements IEnergyHandler, INetworkTile, ITickable {

    private EnumFacing orientation = EnumFacing.NORTH;
    private EnergyStorage energyStorage = new EnergyStorage(256, 128);
    private ArrayList<BlockPos> connections = Lists.newArrayList();

    @Override
    public void update() {

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.orientation = EnumFacing.getFront(tag.getInteger("orientation"));
        this.energyStorage.readFromNBT(tag);
        this.connections = CommonUtils.readPosList("connections", tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("orientation", this.orientation.getIndex());
        this.energyStorage.writeToNBT(tag);
        CommonUtils.writePosList("connections", tag, this.connections);
        return tag;
    }

    @Override
    public void onUpdatePacket(Side side, Object value, int valueId) {

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
        return false;
    }

    public void setOrientation(EnumFacing orientation){
        this.orientation = orientation;
    }

    public EnumFacing getOrientation(){
        return this.orientation;
    }

}
