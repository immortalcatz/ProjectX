package keri.projectx.tile;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.tile.TileEntityInventoryBase;
import keri.projectx.ProjectX;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInventoryProjectX extends TileEntityInventoryBase {

    public TileEntityInventoryProjectX(int size) {
        super(size);
    }

    public TileEntityInventoryProjectX(int size, int stackLimit) {
        super(size, stackLimit);
    }

    public void sendUpdatePacket(boolean causeRenderUpdate) {
        PacketCustom packet = new PacketCustom(ProjectX.INSTANCE, 1);
        packet.writePos(getPos());
        packet.writeNBTTagCompound(this.writeToNBT(new NBTTagCompound()));
        packet.writeBoolean(causeRenderUpdate);
        packet.sendToClients();
    }

}
