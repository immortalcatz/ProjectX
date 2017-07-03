/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import keri.projectx.tile.TileEntityXycroniumLight;
import keri.projectx.tile.TileEntityXynergyNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ProjectXCPH implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleLampPacket(packet, minecraft.world);
                break;
            case 2:
                this.handleXynergyNodePacket(packet, minecraft.world);
                break;
        }
    }

    private void handleLampPacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = world.getTileEntity(pos);

        if(tile != null && tile instanceof TileEntityXycroniumLight){
            ((TileEntityXycroniumLight)tile).onUpdatePacket(packet);
        }
    }

    private void handleXynergyNodePacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = world.getTileEntity(pos);

        if(tile != null && tile instanceof TileEntityXynergyNode){
            ((TileEntityXynergyNode)tile).onUpdatePacket(packet);
        }
    }

}
