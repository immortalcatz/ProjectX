/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import keri.projectx.data.ProjectXWorldExtensionInstantiator;
import keri.projectx.tile.TTilePacketHandler;
import keri.projectx.tile.TileEntityInventoryProjectX;
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
        switch (packet.getType()) {
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
            case 5:
                this.handleTTilePacketHandler(packet, minecraft.world);
        }
    }

    private void handleTTilePacketHandler(PacketCustom packet, WorldClient world) {
        TileEntity tileEntity = world.getTileEntity(packet.readPos());
        if (tileEntity != null && tileEntity instanceof TTilePacketHandler) {
            ((TTilePacketHandler) tileEntity).handlePacket(packet);
        }
    }

    private void handleTilePacket(PacketCustom packet, WorldClient world) {
        final BlockPos pos = packet.readPos();
        final NBTTagCompound tag = packet.readNBTTagCompound();
        final boolean rerender = packet.readBoolean();
        TileEntity tile = (TileEntity) world.getTileEntity(pos);

        if (tile != null) {
            if(tile instanceof TileEntityProjectX){
                TileEntityProjectX tileUpdate = (TileEntityProjectX) tile;
                tileUpdate.readFromNBT(tag);
                tileUpdate.markDirty();
            }
            else if(tile instanceof TileEntityInventoryProjectX){
                TileEntityInventoryProjectX tileUpdate = (TileEntityInventoryProjectX) tile;
                tileUpdate.readFromNBT(tag);
                tileUpdate.markDirty();
            }

            if (rerender) {
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    private void handleWEDescriptionPacket(PacketCustom packet, WorldClient world) {
        ProjectXWorldExtensionInstantiator.getExtensionX(world).handleDescriptionPacket(packet);
    }

    private void handleWERemoveMultiblockPacket(PacketCustom packet, WorldClient world) {
        ProjectXWorldExtensionInstantiator.getExtensionX(world).handleRemoveMultiBlockPacket(packet);
    }

    private void handleWEMultiblockUpdatePacket(PacketCustom packet, WorldClient world) {
        ProjectXWorldExtensionInstantiator.getExtensionX(world).handleMultiBlockUpdate(packet);
    }

}
