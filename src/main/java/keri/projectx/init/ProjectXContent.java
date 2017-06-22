/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import keri.ninetaillib.lib.mod.ContentLoader;
import keri.projectx.block.*;
import keri.projectx.block.machine.BlockFabricator;
import keri.projectx.block.machine.BlockReinforcer;
import keri.projectx.item.*;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@ContentLoader(modid = ModPrefs.MODID)
public class ProjectXContent {

    public static Block xycroniumOre = new BlockXycroniumOre();
    public static Block xycroniumNetherOre = new BlockXycroniumNetherOre();
    public static Block xycroniumStorage = new BlockXycroniumStorage();
    public static Block xycroniumBricks = new BlockXycroniumBricks();
    public static Block xycroniumStructure = new BlockXycroniumStructure();
    public static Block xycroniumPlate = new BlockXycroniumPlate();
    public static Block xycroniumPlatform = new BlockXycroniumPlatform();
    public static Block xycroniumShield = new BlockXycroniumShield();
    public static Block quartzCrystal = new BlockQuartzCrystal();
    public static Block xycroniumLight = new BlockXycroniumLight();
    public static Block xycroniumLightInverted = new BlockXycroniumLightInverted();
    public static Block fabricator = new BlockFabricator();
    public static Block reinforcer = new BlockReinforcer();
    public static Block reinforcedBlock = new BlockReinforcedBlock();

    public static Item xycroniumCrystal = new ItemXycroniumCrystal();
    public static Item xycroniumIngot = new ItemXycroniumIngot();
    public static Item xycroniumNugget = new ItemXycroniumNugget();
    public static Item xycroniumDust = new ItemXycroniumDust();
    public static Item upgradeCard = new ItemUpgradeCard();

}
