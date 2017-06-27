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
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ProjectXCrafting {

    public static void init(){
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
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BLOCK, 1, meta),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_BRICKS, 5, meta),
                    "XVX",
                    "VXV",
                    "XVX",
                    'X', Blocks.STONEBRICK,
                    'V', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta),
                    "XXX",
                    "XXX",
                    "XXX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_NUGGET, 1, meta));
        }

        for(int meta = 0; meta < 16; meta++){
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_PLATE, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.STONEBRICK,
                    'B', new ItemStack(Items.DYE, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_PLATFORM, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.GLASS,
                    'B', new ItemStack(Items.DYE, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_SHIELD, 1, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', Items.IRON_INGOT,
                    'V', Blocks.OBSIDIAN,
                    'B', new ItemStack(ProjectXContent.XYCRONIUM_PLATE, 1, meta));
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.XYCRONIUM_STRUCTURE, 4, meta),
                    "XVX",
                    "VBV",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.STONEBRICK,
                    'B', new ItemStack(Items.DYE, 1, meta));
            registerColorRecipe(new ItemStack(ProjectXContent.XYCRONIUM_LIGHT, 1, 0), EnumDyeColor.VALUES[meta].getColor(),
                    "XVX",
                    "BNB",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Items.REDSTONE, 'B', Items.GLOWSTONE_DUST, 'N', new ItemStack(Items.DYE, 1, meta));
            registerColorRecipe(new ItemStack(ProjectXContent.XYCRONIUM_LIGHT_INVERTED, 1, 0), EnumDyeColor.VALUES[meta].getColor(),
                    "XVX",
                    "BNB",
                    "XVX",
                    'X', new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, OreDictionary.WILDCARD_VALUE),
                    'V', Blocks.REDSTONE_TORCH,
                    'B', Items.GLOWSTONE_DUST,
                    'N', new ItemStack(Items.DYE, 1, meta));
        }
    }

    private static void registerColorRecipe(ItemStack output, Colour color, Object... params){
        ItemNBTUtils.validateTagExists(output);
        output.getTagCompound().setInteger("color", color.rgba());
        GameRegistry.addRecipe(output, params);
    }

}
