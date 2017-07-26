/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration;

import keri.projectx.integration.albedo.IntegrationAlbedo;
import keri.projectx.integration.chisel.IntegrationChisel;
import keri.projectx.integration.thermalexpansion.IntegrationThermalExpansion;
import keri.projectx.integration.thermalfoundation.IntegrationThermalFoundation;
import keri.projectx.integration.waila.IntegrationWaila;
import net.minecraftforge.fml.common.Loader;

public class ProjectXIntegrations {

    public static final String MODID_WAILA = "waila";
    public static final String MODID_THERMAL_EXPANSION = "thermalexpansion";
    public static final String MODID_THERMAL_FOUNDATION = "thermalfoundation";
    public static final String MODID_CHISEL = "chisel";
    public static final String MODID_ALBEDO = "albedo";

    public static void preInit(){
        if(Loader.isModLoaded(MODID_WAILA)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationWaila());
        }

        if(Loader.isModLoaded(MODID_THERMAL_EXPANSION)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationThermalExpansion());
        }

        if(Loader.isModLoaded(MODID_THERMAL_FOUNDATION)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationThermalFoundation());
        }

        if(Loader.isModLoaded(MODID_CHISEL)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationChisel());
        }

        if(Loader.isModLoaded(MODID_ALBEDO)){
            IntegrationHandler.INSTANCE.registerModule(new IntegrationAlbedo());
        }
    }

}
