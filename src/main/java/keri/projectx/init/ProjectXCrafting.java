package keri.projectx.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.stream.IntStream;

public class ProjectXCrafting {

    public static void init(){
        IntStream.range(0, 5).forEach(meta -> {
            GameRegistry.addSmelting(new ItemStack(ProjectXContent.xycroniumCrystal, 1, meta), new ItemStack(ProjectXContent.xycroniumIngot, 1, meta), 1F);
            GameRegistry.addSmelting(new ItemStack(ProjectXContent.xycroniumDust, 1, meta), new ItemStack(ProjectXContent.xycroniumIngot, 1, meta), 1F);
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumBlock, 1, meta), new Object[]{"XXX", "XXX", "XXX", 'X', new ItemStack(ProjectXContent.xycroniumCrystal, 1, meta)});
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumBlock, 1, meta), new Object[]{"XXX", "XXX", "XXX", 'X', new ItemStack(ProjectXContent.xycroniumIngot, 1, meta)});
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.xycroniumCrystal, 9, meta), new Object[]{new ItemStack(ProjectXContent.xycroniumBlock, 1, meta)});
        });
    }

}
