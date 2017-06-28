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
import keri.projectx.block.machine.BlockFabricator;
import keri.projectx.block.machine.BlockReinforcer;
import keri.projectx.client.ProjectXTab;
import keri.projectx.item.*;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

@ContentLoader(modid = ModPrefs.MODID)
public class ProjectXContent {

    private static final ItemToolHelper ITEM_TOOL_HELPER = new ItemToolHelper(ProjectXTab.PROJECTX);
    private static final ItemArmorHelper ITEM_ARMOR_HELPER = new ItemArmorHelper(ProjectXTab.PROJECTX);
    private static Item.ToolMaterial TM_XYCRONIUM = EnumHelper.addToolMaterial("XYCRONIUM", 3, 1100, 4.2F, 4F, 30);

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
    public static Block XYCRONIUM_LIGHT = new BlockXycroniumLight();
    public static Block XYCRONIUM_LIGHT_INVERTED = new BlockXycroniumLightInverted();
    public static Block FABRICATOR = new BlockFabricator();
    public static Block REINFORCER = new BlockReinforcer();
    public static Block REINFORCED_BLOCK = new BlockReinforcedBlock();

    public static Item XYCRONIUM_CRYSTAL = new ItemXycroniumCrystal();
    public static Item XYCRONIUM_INGOT = new ItemXycroniumIngot();
    public static Item XYCRONIUM_NUGGET = new ItemXycroniumNugget();
    public static Item XYCRONIUM_DUST = new ItemXycroniumDust();

    public static Item XYCRONIUM_SWORD_BLUE = ITEM_TOOL_HELPER.createSword(TM_XYCRONIUM);

    public static Item UPGRADE_CARD = new ItemUpgradeCard();
    public static Item COLOR_SCANNER = new ItemColorScanner();
    public static Item POWER_CORE = new ItemPowerCore();

}
