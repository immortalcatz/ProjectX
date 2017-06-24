/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx;

import keri.ninetaillib.lib.config.IModConfig;
import keri.ninetaillib.lib.config.ModConfig;
import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.mod.ModHandler;
import keri.projectx.event.CommonEventHandler;
import keri.projectx.init.ProjectXCrafting;
import keri.projectx.init.ProjectXOreDictionary;
import keri.projectx.init.ProjectXWorldGen;
import keri.projectx.integration.IntegrationHandler;
import keri.projectx.network.ProjectXGuiHandler;
import keri.projectx.proxy.IProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static keri.projectx.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class ProjectX {

    @Mod.Instance(value = MODID)
    public static ProjectX INSTANCE = new ProjectX();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static IProxy PROXY;
    public static ModHandler MOD_HANDLER = new ModHandler(INSTANCE);
    @ModLogger
    public static IModLogger LOGGER;
    @ModConfig
    public static IModConfig CONFIG;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        MOD_HANDLER.handlePreInit(event);
        PROXY.preInit(event);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.INSTANCE);
        IntegrationHandler.INSTANCE.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        MOD_HANDLER.handleInit(event);
        PROXY.init(event);
        ProjectXCrafting.init();
        ProjectXWorldGen.init();
        ProjectXOreDictionary.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new ProjectXGuiHandler());
        IntegrationHandler.INSTANCE.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        MOD_HANDLER.handlePostInit(event);
        PROXY.postInit(event);
        IntegrationHandler.INSTANCE.postInit(event);
    }

}
