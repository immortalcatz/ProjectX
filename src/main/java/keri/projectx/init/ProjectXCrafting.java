/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import codechicken.lib.colour.Colour;
import codechicken.lib.util.ItemNBTUtils;
import keri.ninetaillib.lib.util.EnumDyeColor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ProjectXCrafting {

    public static void init(){
        addSwordRecipe(ProjectXContent.XYCRONIUM_SWORD_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addPickaxeRecipe(ProjectXContent.XYCRONIUM_PICKAXE_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addShovelRecipe(ProjectXContent.XYCRONIUM_SHOVEL_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addAxeRecipe(ProjectXContent.XYCRONIUM_AXE_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addHoeRecipe(ProjectXContent.XYCRONIUM_HOE_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addSwordRecipe(ProjectXContent.XYCRONIUM_SWORD_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addPickaxeRecipe(ProjectXContent.XYCRONIUM_PICKAXE_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addShovelRecipe(ProjectXContent.XYCRONIUM_SHOVEL_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addAxeRecipe(ProjectXContent.XYCRONIUM_AXE_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addHoeRecipe(ProjectXContent.XYCRONIUM_HOE_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addSwordRecipe(ProjectXContent.XYCRONIUM_SWORD_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addPickaxeRecipe(ProjectXContent.XYCRONIUM_PICKAXE_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addShovelRecipe(ProjectXContent.XYCRONIUM_SHOVEL_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addAxeRecipe(ProjectXContent.XYCRONIUM_AXE_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addHoeRecipe(ProjectXContent.XYCRONIUM_HOE_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addSwordRecipe(ProjectXContent.XYCRONIUM_SWORD_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addPickaxeRecipe(ProjectXContent.XYCRONIUM_PICKAXE_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addShovelRecipe(ProjectXContent.XYCRONIUM_SHOVEL_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addAxeRecipe(ProjectXContent.XYCRONIUM_AXE_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addHoeRecipe(ProjectXContent.XYCRONIUM_HOE_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addSwordRecipe(ProjectXContent.XYCRONIUM_SWORD_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addPickaxeRecipe(ProjectXContent.XYCRONIUM_PICKAXE_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addShovelRecipe(ProjectXContent.XYCRONIUM_SHOVEL_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addAxeRecipe(ProjectXContent.XYCRONIUM_AXE_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addHoeRecipe(ProjectXContent.XYCRONIUM_HOE_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addHelmetRecipe(ProjectXContent.XYCRONIUM_HELMET_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addChestplateRecipe(ProjectXContent.XYCRONIUM_CHESTPLATE_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addLeggingsRecipe(ProjectXContent.XYCRONIUM_LEGGINGS_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addBootsRecipe(ProjectXContent.XYCRONIUM_BOOTS_BLUE, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 0));
        addHelmetRecipe(ProjectXContent.XYCRONIUM_HELMET_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addChestplateRecipe(ProjectXContent.XYCRONIUM_CHESTPLATE_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addLeggingsRecipe(ProjectXContent.XYCRONIUM_LEGGINGS_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addBootsRecipe(ProjectXContent.XYCRONIUM_BOOTS_GREEN, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 1));
        addHelmetRecipe(ProjectXContent.XYCRONIUM_HELMET_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addChestplateRecipe(ProjectXContent.XYCRONIUM_CHESTPLATE_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addLeggingsRecipe(ProjectXContent.XYCRONIUM_LEGGINGS_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addBootsRecipe(ProjectXContent.XYCRONIUM_BOOTS_RED, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 2));
        addHelmetRecipe(ProjectXContent.XYCRONIUM_HELMET_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addChestplateRecipe(ProjectXContent.XYCRONIUM_CHESTPLATE_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addLeggingsRecipe(ProjectXContent.XYCRONIUM_LEGGINGS_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addBootsRecipe(ProjectXContent.XYCRONIUM_BOOTS_DARK, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 3));
        addHelmetRecipe(ProjectXContent.XYCRONIUM_HELMET_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addChestplateRecipe(ProjectXContent.XYCRONIUM_CHESTPLATE_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addLeggingsRecipe(ProjectXContent.XYCRONIUM_LEGGINGS_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));
        addBootsRecipe(ProjectXContent.XYCRONIUM_BOOTS_LIGHT, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, 4));

        GameRegistry.addShapelessRecipe(new ItemStack(Items.QUARTZ, 1, 3), ProjectXContent.QUARTZ_CRYSTAL);
        GameRegistry.addRecipe(new ItemStack(ProjectXContent.QUARTZ_CRYSTAL, 1, 0),
                " X ",
                " X ",
                " X ",
                'X', Items.QUARTZ
        );
        GameRegistry.addRecipe(new ItemStack(ProjectXContent.COLOR_SCANNER, 1, 0),
                "RGB",
                "XVX",
                "XXX",
                'R', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, 2),
                'G', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, 1),
                'B', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, 0),
                'X', Items.IRON_INGOT,
                'V', Items.REDSTONE
        );
        GameRegistry.addRecipe(new ItemStack(ProjectXContent.GLASS_VIEWER, 5, 0),
                "XCX",
                "CXC",
                "XCX",
                'X', Blocks.GLASS,
                'C', Items.IRON_INGOT
        );

        //TODO: add missing machine recipes you lazy twat

        for(int meta = 0; meta < 5; meta++){
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 9, meta), new ItemStack(ProjectXContent.XYCRONIUM_BLOCK, 1, meta));
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.XYCRONIUM_NUGGET, 9, meta), new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta));
            GameRegistry.addSmelting(new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, meta), new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta), 1F);
            GameRegistry.addSmelting(new ItemStack(ProjectXContent.XYCRONIUM_DUST, 1, meta), new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta), 1F);
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BRICKS_SMALL, 1, meta), new ItemStack(ProjectXContent.XYCRONIUM_BRICKS, 1, meta));
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BRICKS_CHISELED, 1, meta), new ItemStack(ProjectXContent.XYCRONIUM_BRICKS_SMALL, 1, meta));
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BRICKS, 1, meta), new ItemStack(ProjectXContent.XYCRONIUM_BRICKS_CHISELED, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BLOCK, 1, meta),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BLOCK, 1, meta),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BRICKS, 5, meta),
                    "XVX",
                    "VXV",
                    "XVX",
                    'X', Blocks.STONEBRICK,
                    'V', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_NUGGET, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.ENGINEERING_BRICKS, 1, meta),
                    "XCX",
                    "CVC",
                    "XCX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta),
                    'C', Blocks.STONEBRICK,
                    'V', Items.REDSTONE
            );
        }

        for(int meta = 0; meta < 16; meta++){
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_PLATE, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.STONEBRICK,
                    'B', new ItemStack(Items.DYE, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_PLATFORM, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.GLASS,
                    'B', new ItemStack(Items.DYE, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_SHIELD, 1, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', Items.IRON_INGOT,
                    'V', Blocks.OBSIDIAN,
                    'B', new ItemStack(ProjectXContent.XYCRONIUM_PLATE, 1, meta)
            );
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_STRUCTURE, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.STONEBRICK,
                    'B', new ItemStack(Items.DYE, 1, meta)
            );
            addColorRecipe(new ItemStack(ProjectXContent.XYCRONIUM_LIGHT, 1, 0), EnumDyeColor.VALUES[meta].getColor(),
                    "XVX",
                    "BNB",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Items.REDSTONE, 'B', Items.GLOWSTONE_DUST, 'N', new ItemStack(Items.DYE, 1, meta)
            );
            addColorRecipe(new ItemStack(ProjectXContent.XYCRONIUM_LIGHT_INVERTED, 1, 0), EnumDyeColor.VALUES[meta].getColor(),
                    "XVX",
                    "BNB",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.REDSTONE_TORCH,
                    'B', Items.GLOWSTONE_DUST,
                    'N', new ItemStack(Items.DYE, 1, meta)
            );
        }
    }

    private static void addColorRecipe(ItemStack output, Colour color, Object... params){
        ItemNBTUtils.validateTagExists(output);
        output.getTagCompound().setInteger("color", color.rgba());
        GameRegistry.addRecipe(output, params);
    }

    private static void addSwordRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), " X ", " X ", " C ", 'X', material, 'C', Items.STICK);
    }

    private static void addPickaxeRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "XXX", " C ", " C ", 'X', material, 'C', Items.STICK);
    }

    private static void addShovelRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), " X ", " C ", " C ", 'X', material, 'C', Items.STICK);
    }

    private static void addAxeRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "XX ", "XC ", " C ", 'X', material, 'C', Items.STICK);
    }

    private static void addHoeRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "XX ", " C ", " C ", 'X', material, 'C', Items.STICK);
    }

    private static void addHelmetRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "XXX", "X X", "   ", 'X', material);
    }

    private static void addChestplateRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "X X", "XXX", "XXX", 'X', material);
    }

    private static void addLeggingsRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "XXX", "X X", "X X", 'X', material);
    }

    private static void addBootsRecipe(Item output, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(output, 1, 0), "   ", "X X", "X X", 'X', material);
    }

}
