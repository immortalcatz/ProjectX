/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration;

import com.google.common.collect.Lists;
import keri.projectx.ProjectX;
import keri.projectx.integration.waila.IntegrationWaila;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class IntegrationHandler {

    public static final IntegrationHandler INSTANCE = new IntegrationHandler();
    private static List<IIntegrationModule> MODULES = Lists.newArrayList();

    public void preInit(FMLPreInitializationEvent event){
        this.registerModule(new IntegrationWaila());

        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.preInit(event);
                ProjectX.LOGGER.logInfo(String.format("Loading integration for [%s]", module.getName()));
            }
        }
    }

    public void init(FMLInitializationEvent event){
        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.init(event);
            }
        }
    }

    public void postInit(FMLPostInitializationEvent event){
        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.postInit(event);
                ProjectX.LOGGER.logInfo(String.format("Done loading integration for [%s]", module.getName()));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void preInitClient(FMLPreInitializationEvent event){
        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.preInitClient(event);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void initClient(FMLInitializationEvent event){
        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.initClient(event);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void postInitClient(FMLPostInitializationEvent event){
        for(IIntegrationModule module : MODULES){
            if(Loader.isModLoaded(module.getModid()) && module.isEnabled()){
                module.postInitClient(event);
            }
        }
    }

    public void registerModule(IIntegrationModule module){
        MODULES.add(module);
    }

}