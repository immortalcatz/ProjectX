package keri.projectx.init;

import keri.projectx.property.EnumXycroniumColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class ProjectXOreDictionary {

    public static void init(){
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("oreXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumOre, 1, meta)));
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("blockXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumBlock, 1, meta)));
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("crystalXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumCrystal, 1, meta)));
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("ingotXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumIngot, 1, meta)));
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("nuggetXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumNugget, 1, meta)));
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("dustXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumDust, 1, meta)));
    }

}
