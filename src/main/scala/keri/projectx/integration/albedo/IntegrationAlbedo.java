package keri.projectx.integration.albedo;

import codechicken.lib.packet.PacketCustom;
import keri.projectx.ProjectX;
import keri.projectx.integration.IIntegrationModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IntegrationAlbedo implements IIntegrationModule {

    @Override
    public String getName() {
        return "Albedo";
    }

    @Override
    public boolean isEnabled() {
        return (Boolean)ProjectX.CONFIG.getProperty("integrationAlbedo").getValue();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void preInitClient(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(AlbedoEventHandler.INSTANCE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initClient(FMLInitializationEvent event) {
        PacketCustom.assignHandler(AlbedoPacketHandler.CHANNEL_KEY, new AlbedoPacketHandler());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void postInitClient(FMLPostInitializationEvent event) {

    }

}
