/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

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
