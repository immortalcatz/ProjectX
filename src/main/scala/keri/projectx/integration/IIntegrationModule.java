/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIntegrationModule {

    String getName();

    boolean isEnabled();

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void preInitClient(FMLPreInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void initClient(FMLInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void postInitClient(FMLPostInitializationEvent event);

}
