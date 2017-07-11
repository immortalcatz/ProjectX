/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import keri.projectx.data.ProjectXWorldExtensionInstantiator;
import keri.projectx.tile.TileEntityProjectX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ProjectXCPH implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTilePacket(packet, minecraft.world);
                break;
            case 2:
                this.handleWEDescriptionPacket(packet, minecraft.world);
                break;
            case 3:
                this.handleWERemoveMultiblockPacket(packet, minecraft.world);
                break;
            case 4:
                this.handleWEMultiblockUpdatePacket(packet, minecraft.world);
                break;
        }
    }

    private void handleTilePacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        final NBTTagCompound tag = packet.readNBTTagCompound();
        final boolean rerender = packet.readBoolean();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile != null && tile instanceof TileEntityProjectX){
            TileEntityProjectX tileUpdate = (TileEntityProjectX)tile;
            tileUpdate.readFromNBT(tag);
            tileUpdate.markDirty();

            if(rerender){
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    private void handleWEDescriptionPacket(PacketCustom packet, WorldClient world){
        ProjectXWorldExtensionInstantiator.getExtensionXy(world).handleDescriptionPacket(packet);
    }

    private void handleWERemoveMultiblockPacket(PacketCustom packet, WorldClient world){
        ProjectXWorldExtensionInstantiator.getExtensionXy(world).handleRemoveMultiBlockPacket(packet);
    }

    private void handleWEMultiblockUpdatePacket(PacketCustom packet, WorldClient world){
        ProjectXWorldExtensionInstantiator.getExtensionXy(world).handleMultiBlockUpdate(packet);
    }

}
