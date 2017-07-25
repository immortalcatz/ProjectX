/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import keri.ninetaillib.lib.config.ConfigCategories;
import keri.ninetaillib.lib.config.ConfigProperties;
import keri.ninetaillib.lib.config.ModConfigHandler;
import keri.ninetaillib.lib.config.property.ConfigBoolean;
import keri.ninetaillib.lib.config.property.ConfigIntArray;
import keri.ninetaillib.lib.config.property.ConfigInteger;
import keri.projectx.util.ModPrefs;

@ModConfigHandler(modid = ModPrefs.MODID, fileName = "ProjectX")
public class ProjectXConfig {

    private static final String CATEGORY_CLIENT_N = "client";
    private static final String CATEGORY_CLIENT_D = "Config options related to the client side of ProjectX";
    private static final String CATEGORY_INTEGRATION_N = "integration";
    private static final String CATEGORY_INTEGRATION_D = "Config options related to mod integration modules from ProjectX";
    private static final String CATEGORY_WORLD_N = "world";
    private static final String CATEGORY_WORLD_D = "Config options related to world gen from ProjectX";
    private static final String ANIMATION_RESOLUTION_K = "animationResolution";
    private static final String ANIMATION_RESOLUTION_C = "Change the resolution of the animated texture";
    private static final String ANIMATED_ORES_K = "animatedOres";
    private static final String ANIMATED_ORES_C = "Enable or disable animated xycronium ore";
    private static final String COLORED_BRICKS_K = "coloredBricks";
    private static final String COLORED_BRICKS_C = "Enable or disable colored brick textures";
    private static final String INTEGRATION_WAILA_K = "integrationWaila";
    private static final String INTEGRATION_WAILA_C = "Enable or disable WAILA integration";
    private static final String INTEGRATION_TE_K = "integrationThermalExpansion";
    private static final String INTEGRATION_TE_D = "Enable or disable Thermal Expansion integration";
    private static final String INTEGRATION_TF_K = "integrationThermalFoundation";
    private static final String INTEGRATION_TF_D = "Enable or disable Thermal Foundation integration";
    private static final String INTEGRATION_CHISEL_K = "integrationChisel";
    private static final String INTEGRATION_CHISEL_D = "Enable or disable Chisel integration";
    private static final String INTEGRATION_TIC_K = "integrationTConstruct";
    private static final String INTEGRATION_TIC_D = "Enable or disable Tinkers Construct integration";
    //private static final String INTEGRATION_ALBEDO_K = "integrationAlbedo";
    //private static final String INTEGRATION_ALBEDO_D = "Enable or disable Albedo integration";
    private static final String ORE_GEN_WEIGHT_K = "oreGenerationWeight";
    private static final String ORE_GEN_WEIGHT_C = "Change the ore generation weight (only modify when you know what you're doing!)";
    private static final String XYCRONIUM_ORE_BLUE_K = "xycroniumOreBlue";
    private static final String XYCRONIUM_ORE_BLUE_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_ORE_GREEN_K = "xycroniumOreGreen";
    private static final String XYCRONIUM_ORE_GREEN_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_ORE_RED_K = "xycroniumOreRed";
    private static final String XYCRONIUM_ORE_RED_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_ORE_DARK_K = "xycroniumOreDark";
    private static final String XYCRONIUM_ORE_DARK_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_ORE_LIGHT_K = "xycroniumOreLight";
    private static final String XYCRONIUM_ORE_LIGHT_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_N_ORE_BLUE_K = "xycroniumNetherOreBlue";
    private static final String XYCRONIUM_N_ORE_BLUE_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_N_ORE_GREEN_K = "xycroniumNetherOreGreen";
    private static final String XYCRONIUM_N_ORE_GREEN_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_N_ORE_RED_K = "xycroniumNetherOreRed";
    private static final String XYCRONIUM_N_ORE_RED_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_N_ORE_DARK_K = "xycroniumNetherOreDark";
    private static final String XYCRONIUM_N_ORE_DARK_C = "Chance, min height, max height, min vein size, max vein size";
    private static final String XYCRONIUM_N_ORE_LIGHT_K = "xycroniumNetherOreLight";
    private static final String XYCRONIUM_N_ORE_LIGHT_C = "Chance, min height, max height, min vein size, max vein size";

    @ModConfigHandler.ConfigCategories
    public void addCategories(ConfigCategories categories){
        categories.addCategory(CATEGORY_CLIENT_N, CATEGORY_CLIENT_D);
        categories.addCategory(CATEGORY_INTEGRATION_N, CATEGORY_INTEGRATION_D);
        categories.addCategory(CATEGORY_WORLD_N, CATEGORY_WORLD_D);
    }

    @ModConfigHandler.ConfigProperties
    public void addProperties(ConfigProperties properties){
        properties.addProperty(ANIMATION_RESOLUTION_K, new ConfigInteger(ANIMATION_RESOLUTION_K, ANIMATION_RESOLUTION_C, CATEGORY_CLIENT_N, 32, 16, 128));
        properties.addProperty(ANIMATED_ORES_K, new ConfigBoolean(ANIMATED_ORES_K, ANIMATED_ORES_C, CATEGORY_CLIENT_N, false));
        properties.addProperty(COLORED_BRICKS_K, new ConfigBoolean(COLORED_BRICKS_K, COLORED_BRICKS_C, CATEGORY_CLIENT_N, true));
        properties.addProperty(INTEGRATION_WAILA_K, new ConfigBoolean(INTEGRATION_WAILA_K, INTEGRATION_WAILA_C, CATEGORY_INTEGRATION_N, true));
        properties.addProperty(INTEGRATION_TE_K, new ConfigBoolean(INTEGRATION_TE_K, INTEGRATION_TE_D, CATEGORY_INTEGRATION_N, true));
        properties.addProperty(INTEGRATION_TF_K, new ConfigBoolean(INTEGRATION_TF_K, INTEGRATION_TF_D, CATEGORY_INTEGRATION_N, true));
        properties.addProperty(INTEGRATION_CHISEL_K, new ConfigBoolean(INTEGRATION_CHISEL_K, INTEGRATION_CHISEL_D, CATEGORY_INTEGRATION_N, true));
        properties.addProperty(INTEGRATION_TIC_K, new ConfigBoolean(INTEGRATION_TIC_K, INTEGRATION_TIC_D, CATEGORY_INTEGRATION_N, true));
        //properties.addProperty(INTEGRATION_ALBEDO_K, new ConfigBoolean(INTEGRATION_ALBEDO_K, INTEGRATION_ALBEDO_D, CATEGORY_INTEGRATION_N, true));
        properties.addProperty(ORE_GEN_WEIGHT_K, new ConfigInteger(ORE_GEN_WEIGHT_K, ORE_GEN_WEIGHT_C, CATEGORY_WORLD_N, 1, 0, 10));
        properties.addProperty(XYCRONIUM_ORE_BLUE_K, new ConfigIntArray(XYCRONIUM_ORE_BLUE_K, CATEGORY_WORLD_N, XYCRONIUM_ORE_BLUE_C, new int[]{3, 20, 52, 4, 8}));
        properties.addProperty(XYCRONIUM_ORE_GREEN_K, new ConfigIntArray(XYCRONIUM_ORE_GREEN_K, CATEGORY_WORLD_N, XYCRONIUM_ORE_GREEN_C, new int[]{3, 20, 52, 4, 8}));
        properties.addProperty(XYCRONIUM_ORE_RED_K, new ConfigIntArray(XYCRONIUM_ORE_RED_K, CATEGORY_WORLD_N, XYCRONIUM_ORE_RED_C, new int[]{3, 20, 52, 4, 8}));
        properties.addProperty(XYCRONIUM_ORE_DARK_K, new ConfigIntArray(XYCRONIUM_ORE_DARK_K, CATEGORY_WORLD_N, XYCRONIUM_ORE_DARK_C, new int[]{3, 20, 52, 4, 8}));
        properties.addProperty(XYCRONIUM_ORE_LIGHT_K, new ConfigIntArray(XYCRONIUM_ORE_LIGHT_K, CATEGORY_WORLD_N, XYCRONIUM_ORE_LIGHT_C, new int[]{3, 20, 52, 4, 8}));
        properties.addProperty(XYCRONIUM_N_ORE_BLUE_K, new ConfigIntArray(XYCRONIUM_N_ORE_BLUE_K, CATEGORY_WORLD_N, XYCRONIUM_N_ORE_BLUE_C, new int[]{2, 10, 122, 4, 7}));
        properties.addProperty(XYCRONIUM_N_ORE_GREEN_K, new ConfigIntArray(XYCRONIUM_N_ORE_GREEN_K, CATEGORY_WORLD_N, XYCRONIUM_N_ORE_GREEN_C, new int[]{2, 10, 122, 4, 7}));
        properties.addProperty(XYCRONIUM_N_ORE_RED_K, new ConfigIntArray(XYCRONIUM_N_ORE_RED_K, CATEGORY_WORLD_N, XYCRONIUM_N_ORE_RED_C, new int[]{2, 10, 122, 4, 7}));
        properties.addProperty(XYCRONIUM_N_ORE_DARK_K, new ConfigIntArray(XYCRONIUM_N_ORE_DARK_K, CATEGORY_WORLD_N, XYCRONIUM_N_ORE_DARK_C, new int[]{2, 10, 122, 4, 7}));
        properties.addProperty(XYCRONIUM_N_ORE_LIGHT_K, new ConfigIntArray(XYCRONIUM_N_ORE_LIGHT_K, CATEGORY_WORLD_N, XYCRONIUM_N_ORE_LIGHT_C, new int[]{2, 10, 122, 4, 7}));
    }

}
