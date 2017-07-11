/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.init;

import keri.projectx.api.color.EnumXycroniumColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

public class ProjectXOreDictionary {

    public static void init(){
        for(int meta = 0; meta < 5; meta++){
            String colorName = StringUtils.capitalize(EnumXycroniumColor.VALUES[meta].getName());
            OreDictionary.registerOre("oreXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_ORE, 1, meta));
            OreDictionary.registerOre("oreXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, meta));
            OreDictionary.registerOre("blockXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_BLOCK, 1, meta));
            OreDictionary.registerOre("crystalXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, meta));
            OreDictionary.registerOre("ingotXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 1, meta));
            OreDictionary.registerOre("nuggetXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_NUGGET, 1, meta));
            OreDictionary.registerOre("dustXycronium" + colorName, new ItemStack(ProjectXContent.XYCRONIUM_DUST, 1, meta));
        }
    }

}
