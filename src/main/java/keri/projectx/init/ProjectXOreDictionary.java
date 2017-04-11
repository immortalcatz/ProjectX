package keri.projectx.init;

import keri.projectx.property.EnumXycroniumColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class ProjectXOreDictionary {

    public static void init(){
        IntStream.range(0, 5).forEach(meta -> OreDictionary.registerOre("oreXycronium" + StringUtils.capitalize(EnumXycroniumColor.values()[meta].getName()), new ItemStack(ProjectXContent.xycroniumOre, 1, meta)));
    }

}
