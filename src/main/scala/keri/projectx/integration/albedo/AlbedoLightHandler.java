package keri.projectx.integration.albedo;

import codechicken.lib.colour.Colour;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.Vector3;

public class AlbedoLightHandler {

    public static void addLight(Vector3 position, Colour color, float radius){
        PacketCustom packet = new PacketCustom(AlbedoPacketHandler.CHANNEL_KEY, 1);
        packet.writeDouble(position.x);
        packet.writeDouble(position.y);
        packet.writeDouble(position.z);
        packet.writeByte(color.r);
        packet.writeByte(color.g);
        packet.writeByte(color.b);
        packet.writeByte(color.a);
        packet.writeFloat(radius);
        packet.compress().sendToClients();
    }

    public static void removeLight(Vector3 position, Colour color, float radius){
        PacketCustom packet = new PacketCustom(AlbedoPacketHandler.CHANNEL_KEY, 2);
        packet.writeDouble(position.x);
        packet.writeDouble(position.y);
        packet.writeDouble(position.z);
        packet.writeByte(color.r);
        packet.writeByte(color.g);
        packet.writeByte(color.b);
        packet.writeByte(color.a);
        packet.writeFloat(radius);
        packet.compress().sendToClients();
    }

}
