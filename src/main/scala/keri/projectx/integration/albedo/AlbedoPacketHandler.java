/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.albedo;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class AlbedoPacketHandler implements ICustomPacketHandler.IClientPacketHandler {

    public static final String CHANNEL_KEY = "projectx_albedo_channel";

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleAddLightPacket(packet, minecraft.world);
                break;
            case 2:
                this.handleRemoveLightPacket(packet, minecraft.world);
                break;
            case 3:
                this.handleChangeLightColorPacket(packet, minecraft.world);
        }
    }

    private void handleAddLightPacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        final double x = packet.readDouble();
        final double y = packet.readDouble();
        final double z = packet.readDouble();
        final byte r = packet.readByte();
        final byte g = packet.readByte();
        final byte b = packet.readByte();
        final byte a = packet.readByte();
        final float radius = packet.readFloat();
        AlbedoLightHandler.INSTANCE.addLight(pos, new AlbedoLight(
                new Vector3(x, y, z),
                new ColourRGBA(r, g, b, a),
                radius
        ));
    }

    private void handleRemoveLightPacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        AlbedoLightHandler.INSTANCE.removeLight(pos);
    }

    private void handleChangeLightColorPacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        final byte r = packet.readByte();
        final byte g = packet.readByte();
        final byte b = packet.readByte();
        final byte a = packet.readByte();
        AlbedoLightHandler.INSTANCE.changeLightColor(pos, new ColourRGBA(r, g, b, a));
    }

}
