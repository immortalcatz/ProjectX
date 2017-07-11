/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration.waila;

import keri.projectx.ProjectX;
import keri.projectx.block.BlockXycroniumLight;
import keri.projectx.block.BlockXycroniumLightInverted;
import keri.projectx.integration.IIntegrationModule;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class IntegrationWaila implements IIntegrationModule {

    public static void callbackRegister(IWailaRegistrar registrar) {
        WailaDataProvider dataProvider = new WailaDataProvider();
        registrar.registerStackProvider(dataProvider, BlockXycroniumLight.class);
        registrar.registerBodyProvider(dataProvider, BlockXycroniumLight.class);
        registrar.registerStackProvider(dataProvider, BlockXycroniumLightInverted.class);
        registrar.registerBodyProvider(dataProvider, BlockXycroniumLightInverted.class);
    }

    @Override
    public String getName() {
        return "Waila";
    }

    @Override
    public boolean isEnabled() {
        return (Boolean)ProjectX.CONFIG.getProperty("integrationWaila").getValue();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        this.registerCallback();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void preInitClient(FMLPreInitializationEvent event) {

    }

    @Override
    public void initClient(FMLInitializationEvent event) {

    }

    @Override
    public void postInitClient(FMLPostInitializationEvent event) {

    }

    private void registerCallback(){
        FMLInterModComms.sendMessage("waila", "register", "keri.projectx.integration.waila.IntegrationWaila.callbackRegister");
    }

}
