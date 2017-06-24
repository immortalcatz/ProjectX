/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration;

import keri.projectx.integration.thermalexpansion.IntegrationThermalExpansion;
import keri.projectx.integration.waila.IntegrationWaila;
import net.minecraftforge.fml.common.Loader;

public class ProjectXIntegrations {

    private static final String MODID_WAILA = "waila";
    private static final String MODID_THERMAL_EXPANSION = "thermalexpansion";

    public static void preInit(){
        if(Loader.isModLoaded(MODID_WAILA)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationWaila());
        }

        if(Loader.isModLoaded(MODID_THERMAL_EXPANSION)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationThermalExpansion());
        }
    }

}
