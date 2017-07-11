/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import keri.ninetaillib.lib.item.ItemArmorHelper;
import keri.ninetaillib.lib.item.ItemToolHelper;
import keri.ninetaillib.lib.mod.ContentLoader;
import keri.projectx.block.*;
import keri.projectx.block.machine.*;
import keri.projectx.client.ProjectXTab;
import keri.projectx.item.*;
import keri.projectx.machine.multiblock.block.BlockMultiShadow;
import keri.projectx.machine.multiblock.block.BlockValve;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

@ContentLoader(modid = ModPrefs.MODID)
public class ProjectXContent {

    private static final ItemToolHelper ITEM_TOOL_HELPER = new ItemToolHelper(ProjectXTab.PROJECTX);
    private static final ItemArmorHelper ITEM_ARMOR_HELPER = new ItemArmorHelper(ProjectXTab.PROJECTX);
    private static Item.ToolMaterial TM_XYCRONIUM_BLUE = EnumHelper.addToolMaterial("XYCRONIUM_BLUE", 3, 1100, 3.6F, 3.2F, 30);
    private static Item.ToolMaterial TM_XYCRONIUM_GREEN = EnumHelper.addToolMaterial("XYCRONIUM_GREEN", 3, 1100, 3.6F, 3.2F, 30);
    private static Item.ToolMaterial TM_XYCRONIUM_RED = EnumHelper.addToolMaterial("XYCRONIUM_RED", 3, 1100, 3.6F, 3.2F, 30);
    private static Item.ToolMaterial TM_XYCRONIUM_DARK = EnumHelper.addToolMaterial("XYCRONIUM_DARK", 3, 1100, 3.6F, 3.2F, 30);
    private static Item.ToolMaterial TM_XYCRONIUM_LIGHT = EnumHelper.addToolMaterial("XYCRONIUM_LIGHT", 3, 1100, 3.6F, 3.2F, 30);
    private static ItemArmor.ArmorMaterial AM_XYCRONIUM_BLUE = EnumHelper.addArmorMaterial("XYCRONIUM_BLUE", ModPrefs.MODID + ":xycronium_blue", 14, new int[]{2, 5, 6, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);
    private static ItemArmor.ArmorMaterial AM_XYCRONIUM_GREEN = EnumHelper.addArmorMaterial("XYCRONIUM_GREEN", ModPrefs.MODID + ":xycronium_green", 14, new int[]{2, 5, 6, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);
    private static ItemArmor.ArmorMaterial AM_XYCRONIUM_RED = EnumHelper.addArmorMaterial("XYCRONIUM_RED", ModPrefs.MODID + ":xycronium_red", 14, new int[]{2, 5, 6, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);
    private static ItemArmor.ArmorMaterial AM_XYCRONIUM_DARK = EnumHelper.addArmorMaterial("XYCRONIUM_DARK", ModPrefs.MODID + ":xycronium_dark", 14, new int[]{2, 5, 6, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);
    private static ItemArmor.ArmorMaterial AM_XYCRONIUM_LIGHT = EnumHelper.addArmorMaterial("XYCRONIUM_LIGHT", ModPrefs.MODID + ":xycronium_light", 14, new int[]{2, 5, 6, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);

    public static Block XYCRONIUM_ORE = new BlockXycroniumOre();
    public static Block XYCRONIUM_NETHER_ORE = new BlockXycroniumNetherOre();
    public static Block XYCRONIUM_BLOCK = new BlockXycroniumStorage();
    public static Block XYCRONIUM_BRICKS = new BlockXycroniumBricks();
    public static Block XYCRONIUM_BRICKS_SMALL = new BlockXycroniumBricksSmall();
    public static Block XYCRONIUM_BRICKS_CHISELED = new BlockXycroniumBricksChiseled();
    public static Block XYCRONIUM_STRUCTURE = new BlockXycroniumStructure();
    public static Block XYCRONIUM_PLATE = new BlockXycroniumPlate();
    public static Block XYCRONIUM_PLATFORM = new BlockXycroniumPlatform();
    public static Block XYCRONIUM_SHIELD = new BlockXycroniumShield();
    public static Block QUARTZ_CRYSTAL = new BlockQuartzCrystal();
    public static Block GLASS_VIEWER = new BlockGlassViewer();
    public static Block XYCRONIUM_LIGHT = new BlockXycroniumLight();
    public static Block XYCRONIUM_LIGHT_INVERTED = new BlockXycroniumLightInverted();
    public static Block ENGINEERING_BRICKS = new BlockEngineeringBricks();
    public static Block XYCRONIUM_WATER = new BlockXycroniumWater();
    public static Block XYCRONIUM_SOIL = new BlockXycroniumSoil();
    public static Block FIRE_BASIN = new BlockFireBasin();
    public static Block LIQUID_VOID = new BlockLiquidVoid();
    public static Block XYCRONIUM_ICE = new BlockXycroniumIce();
    public static Block FABRICATOR = new BlockFabricator();
    public static Block XYNERGY_NODE = new BlockXynergyNode();
    public static Block blockShadowRock = new BlockMultiShadow(Material.ROCK, "Rock");
    public static Block blockShadowAir = new BlockMultiShadow(Material.AIR, "Air");
    public static Block blockShadowGlass = new BlockMultiShadow(Material.GLASS, "Glass");
    public static Block blockShadowWood = new BlockMultiShadow(Material.WOOD, "Wood");
    public static Block blockShadowGrass = new BlockMultiShadow(Material.GROUND, "Grass");
    public static Block blockMultiBlock = new BlockValve();

    public static Item XYCRONIUM_CRYSTAL = new ItemXycroniumCrystal();
    public static Item XYCRONIUM_INGOT = new ItemXycroniumIngot();
    public static Item XYCRONIUM_NUGGET = new ItemXycroniumNugget();
    public static Item XYCRONIUM_DUST = new ItemXycroniumDust();
    public static Item XYCRONIUM_SWORD_BLUE = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_PICKAXE_BLUE = ITEM_TOOL_HELPER.createPickaxe(TM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_SHOVEL_BLUE = ITEM_TOOL_HELPER.createShovel(TM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_AXE_BLUE = ITEM_TOOL_HELPER.createAxe(TM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_HOE_BLUE = ITEM_TOOL_HELPER.createHoe(TM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_SWORD_GREEN = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_PICKAXE_GREEN = ITEM_TOOL_HELPER.createPickaxe(TM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_SHOVEL_GREEN = ITEM_TOOL_HELPER.createShovel(TM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_AXE_GREEN = ITEM_TOOL_HELPER.createAxe(TM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_HOE_GREEN = ITEM_TOOL_HELPER.createHoe(TM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_SWORD_RED = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM_RED);
    public static Item XYCRONIUM_PICKAXE_RED = ITEM_TOOL_HELPER.createPickaxe(TM_XYCRONIUM_RED);
    public static Item XYCRONIUM_SHOVEL_RED = ITEM_TOOL_HELPER.createShovel(TM_XYCRONIUM_RED);
    public static Item XYCRONIUM_AXE_RED = ITEM_TOOL_HELPER.createAxe(TM_XYCRONIUM_RED);
    public static Item XYCRONIUM_HOE_RED = ITEM_TOOL_HELPER.createHoe(TM_XYCRONIUM_RED);
    public static Item XYCRONIUM_SWORD_DARK = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_PICKAXE_DARK = ITEM_TOOL_HELPER.createPickaxe(TM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_SHOVEL_DARK = ITEM_TOOL_HELPER.createShovel(TM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_AXE_DARK = ITEM_TOOL_HELPER.createAxe(TM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_HOE_DARK = ITEM_TOOL_HELPER.createHoe(TM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_SWORD_LIGHT = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_PICKAXE_LIGHT = ITEM_TOOL_HELPER.createPickaxe(TM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_SHOVEL_LIGHT = ITEM_TOOL_HELPER.createShovel(TM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_AXE_LIGHT = ITEM_TOOL_HELPER.createAxe(TM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_HOE_LIGHT = ITEM_TOOL_HELPER.createHoe(TM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_HELMET_BLUE = ITEM_ARMOR_HELPER.createHelmet(AM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_CHESTPLATE_BLUE = ITEM_ARMOR_HELPER.createChestplate(AM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_LEGGINGS_BLUE = ITEM_ARMOR_HELPER.createLeggings(AM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_BOOTS_BLUE = ITEM_ARMOR_HELPER.createBoots(AM_XYCRONIUM_BLUE);
    public static Item XYCRONIUM_HELMET_GREEN = ITEM_ARMOR_HELPER.createHelmet(AM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_CHESTPLATE_GREEN = ITEM_ARMOR_HELPER.createChestplate(AM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_LEGGINGS_GREEN = ITEM_ARMOR_HELPER.createLeggings(AM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_BOOTS_GREEN = ITEM_ARMOR_HELPER.createBoots(AM_XYCRONIUM_GREEN);
    public static Item XYCRONIUM_HELMET_RED = ITEM_ARMOR_HELPER.createHelmet(AM_XYCRONIUM_RED);
    public static Item XYCRONIUM_CHESTPLATE_RED = ITEM_ARMOR_HELPER.createChestplate(AM_XYCRONIUM_RED);
    public static Item XYCRONIUM_LEGGINGS_RED = ITEM_ARMOR_HELPER.createLeggings(AM_XYCRONIUM_RED);
    public static Item XYCRONIUM_BOOTS_RED = ITEM_ARMOR_HELPER.createBoots(AM_XYCRONIUM_RED);
    public static Item XYCRONIUM_HELMET_DARK = ITEM_ARMOR_HELPER.createHelmet(AM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_CHESTPLATE_DARK = ITEM_ARMOR_HELPER.createChestplate(AM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_LEGGINGS_DARK = ITEM_ARMOR_HELPER.createLeggings(AM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_BOOTS_DARK = ITEM_ARMOR_HELPER.createBoots(AM_XYCRONIUM_DARK);
    public static Item XYCRONIUM_HELMET_LIGHT = ITEM_ARMOR_HELPER.createHelmet(AM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_CHESTPLATE_LIGHT = ITEM_ARMOR_HELPER.createChestplate(AM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_LEGGINGS_LIGHT = ITEM_ARMOR_HELPER.createLeggings(AM_XYCRONIUM_LIGHT);
    public static Item XYCRONIUM_BOOTS_LIGHT = ITEM_ARMOR_HELPER.createBoots(AM_XYCRONIUM_LIGHT);
    public static Item UPGRADE_CARD = new ItemUpgradeCard();
    public static Item COLOR_SCANNER = new ItemColorScanner();
    public static Item POWER_CORE = new ItemPowerCore();
    public static Item XYNERGY_TOOL = new ItemXynergyTool();
    public static Item MANUAL = new ItemManual();

}
