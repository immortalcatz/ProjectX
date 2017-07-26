/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.albedo;

import codechicken.lib.packet.PacketCustom;
import keri.projectx.ProjectX;
import keri.projectx.integration.IIntegrationModule;
import keri.projectx.integration.ProjectXIntegrations;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IntegrationAlbedo implements IIntegrationModule {

    public static boolean IS_AVAILABLE = Loader.isModLoaded(ProjectXIntegrations.MODID_ALBEDO);

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
        MinecraftForge.EVENT_BUS.register(new AlbedoEventHandler());
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
