package keri.projectx.integration.albedo;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;

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
        }
    }

    private void handleAddLightPacket(PacketCustom packet, WorldClient world){
        final double x = packet.readDouble();
        final double y = packet.readDouble();
        final double z = packet.readDouble();
        final byte r = packet.readByte();
        final byte g = packet.readByte();
        final byte b = packet.readByte();
        final byte a = packet.readByte();
        final float ra = packet.readFloat();
        AlbedoEventHandler.INSTANCE.addLight(new Vector3(x, y, z), new ColourRGBA(r, g, b, a), ra);
    }

    private void handleRemoveLightPacket(PacketCustom packet, WorldClient world){
        final double x = packet.readDouble();
        final double y = packet.readDouble();
        final double z = packet.readDouble();
        final byte r = packet.readByte();
        final byte g = packet.readByte();
        final byte b = packet.readByte();
        final byte a = packet.readByte();
        final float ra = packet.readFloat();
        AlbedoEventHandler.INSTANCE.removeLight(new Vector3(x, y, z), new ColourRGBA(r, g, b, a), ra);
    }

}
