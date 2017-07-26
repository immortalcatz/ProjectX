/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.albedo;

import codechicken.lib.colour.Colour;
import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class AlbedoLightHandler {

    public static final AlbedoLightHandler INSTANCE = new AlbedoLightHandler();
    private static Map<BlockPos, AlbedoLight> LIGHTS = Maps.newHashMap();

    public void addLight(BlockPos pos, AlbedoLight light){
        LIGHTS.put(pos, light);
    }

    public void removeLight(BlockPos pos){
        LIGHTS.remove(pos);
    }

    public void changeLightColor(BlockPos pos, Colour color){
        LIGHTS.get(pos).setColor(color);
    }

    public ImmutableMap<BlockPos, AlbedoLight> getLights(){
        return ImmutableMap.copyOf(LIGHTS);
    }

    public void sendAddLightPacket(BlockPos pos, AlbedoLight light){
        PacketCustom packet = new PacketCustom(AlbedoPacketHandler.CHANNEL_KEY, 1);
        packet.writePos(pos);
        packet.writeDouble(light.getPosition().x);
        packet.writeDouble(light.getPosition().y);
        packet.writeDouble(light.getPosition().z);
        packet.writeByte(light.getColor().r);
        packet.writeByte(light.getColor().g);
        packet.writeByte(light.getColor().b);
        packet.writeByte(light.getColor().a);
        packet.writeFloat(light.getRadius());
        packet.compress().sendToClients();
    }

    public void sendRemoveLightPacket(BlockPos pos){
        PacketCustom packet = new PacketCustom(AlbedoPacketHandler.CHANNEL_KEY, 2);
        packet.writePos(pos);
        packet.compress().sendToClients();
    }

    public void sendChangeLightColorPacket(BlockPos pos, Colour color){
        PacketCustom packet = new PacketCustom(AlbedoPacketHandler.CHANNEL_KEY, 3);
        packet.writePos(pos);
        packet.writeByte(color.r);
        packet.writeByte(color.g);
        packet.writeByte(color.b);
        packet.writeByte(color.a);
        packet.compress().sendToClients();
    }

}
