/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import keri.ninetaillib.lib.config.ConfigCategories;
import keri.ninetaillib.lib.config.ConfigProperties;
import keri.ninetaillib.lib.config.ModConfigHandler;
import keri.ninetaillib.lib.config.property.ConfigBoolean;
import keri.ninetaillib.lib.config.property.ConfigInteger;
import keri.projectx.util.ModPrefs;

@ModConfigHandler(modid = ModPrefs.MODID)
public class ProjectXConfig {

    private static final String CATEGORY_CLIENT_N = "client";
    private static final String CATEGORY_CLIENT_D = "config options related to the client side of ProjectX";
    private static final String ANIMATION_RESOLUTION_K = "animationResolution";
    private static final String ANIMATION_RESOLUTION_C = "change the resolution of the animated texture";
    private static final String ANIMATED_ORES_K = "animatedOres";
    private static final String ANIMATED_ORES_C = "enable or disable animated xycronium ore";

    @ModConfigHandler.ConfigCategories
    public void addCategories(ConfigCategories categories){
        categories.addCategory(CATEGORY_CLIENT_N, CATEGORY_CLIENT_D);
    }

    @ModConfigHandler.ConfigProperties
    public void addProperties(ConfigProperties properties){
        properties.addProperty(ANIMATION_RESOLUTION_K, new ConfigInteger(ANIMATION_RESOLUTION_K, ANIMATION_RESOLUTION_C, CATEGORY_CLIENT_N, 32, 16, 128));
        properties.addProperty(ANIMATED_ORES_K, new ConfigBoolean(ANIMATED_ORES_K, ANIMATED_ORES_C, CATEGORY_CLIENT_N, false));
    }

}
