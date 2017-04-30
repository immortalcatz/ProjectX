package keri.projectx.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumBricks, 5, meta), new Object[]{"VXV", "XXX", "VXV", 'V', new ItemStack(ProjectXContent.xycroniumIngot, 1, meta), 'X', Blocks.STONEBRICK});
            GameRegistry.addShapelessRecipe(new ItemStack(ProjectXContent.xycroniumNugget, 9, meta), new Object[]{new ItemStack(ProjectXContent.xycroniumIngot, 1, meta)});
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumIngot, 1, meta), new Object[]{"XXX", "XXX", "XXX", 'X', new ItemStack(ProjectXContent.xycroniumNugget, 1, meta)});
        });

        IntStream.range(0, 5).forEach(metaResource -> {
            IntStream.range(0, 16).forEach(metaColor -> {
                GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumPlate, 4, metaColor), new Object[]{"VXV", "XCX", "VXV", 'V', new ItemStack(ProjectXContent.xycroniumIngot, 1, metaResource), 'X', Blocks.STONEBRICK, 'C', new ItemStack(Items.DYE, 1, metaColor)});
                GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumPlatform, 4, metaColor), new Object[]{"VXV", "XCX", "VXV", 'V', new ItemStack(ProjectXContent.xycroniumIngot, 1, metaResource), 'X', Blocks.GLASS, 'C', new ItemStack(Items.DYE, 1, metaColor)});
                GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumStructure, 4, metaColor), new Object[]{"VXV", "XCX", "VXV", 'V', new ItemStack(ProjectXContent.xycroniumCrystal, 1, metaResource), 'X', Blocks.STONEBRICK, 'C', new ItemStack(Items.DYE, 1, metaColor)});
            });
        });

        IntStream.range(0, 16).forEach(meta -> {
            GameRegistry.addRecipe(new ItemStack(ProjectXContent.xycroniumShield, 2, meta), new Object[]{"XVX", "VXV", "XVX", 'X', Items.IRON_INGOT, 'V', new ItemStack(ProjectXContent.xycroniumPlate, 1, meta)});
        });

        addToolsetRecipe(ProjectXContent.xycroniumToolsBlue, new ItemStack(ProjectXContent.xycroniumIngot, 1, 0));
        addToolsetRecipe(ProjectXContent.xycroniumToolsGreen, new ItemStack(ProjectXContent.xycroniumIngot, 1, 1));
        addToolsetRecipe(ProjectXContent.xycroniumToolsRed, new ItemStack(ProjectXContent.xycroniumIngot, 1, 2));
        addToolsetRecipe(ProjectXContent.xycroniumToolsDark, new ItemStack(ProjectXContent.xycroniumIngot, 1, 3));
        addToolsetRecipe(ProjectXContent.xycroniumToolsLight, new ItemStack(ProjectXContent.xycroniumIngot, 1, 4));
        addArmorsetRecipe(ProjectXContent.xycroniumArmorBlue, new ItemStack(ProjectXContent.xycroniumIngot, 1, 0));
        addArmorsetRecipe(ProjectXContent.xycroniumArmorGreen, new ItemStack(ProjectXContent.xycroniumIngot, 1, 1));
        addArmorsetRecipe(ProjectXContent.xycroniumArmorRed, new ItemStack(ProjectXContent.xycroniumIngot, 1, 2));
        addArmorsetRecipe(ProjectXContent.xycroniumArmorDark, new ItemStack(ProjectXContent.xycroniumIngot, 1, 3));
        addArmorsetRecipe(ProjectXContent.xycroniumArmorLight, new ItemStack(ProjectXContent.xycroniumIngot, 1, 4));
    }

    private static void addToolsetRecipe(Item[] toolset, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(toolset[0], 1, 0), new Object[]{" X ", " X ", " C ", 'X', material, 'C', Items.STICK});
        GameRegistry.addRecipe(new ItemStack(toolset[1], 1, 0), new Object[]{"XXX", " C ", " C ", 'X', material, 'C', Items.STICK});
        GameRegistry.addRecipe(new ItemStack(toolset[2], 1, 0), new Object[]{" X ", " C ", " C ", 'X', material, 'C', Items.STICK});
        GameRegistry.addRecipe(new ItemStack(toolset[3], 1, 0), new Object[]{" XX", " CX", " C ", 'X', material, 'C', Items.STICK});
        GameRegistry.addRecipe(new ItemStack(toolset[4], 1, 0), new Object[]{" XX", " C ", " C ", 'X', material, 'C', Items.STICK});
    }

    private static void addArmorsetRecipe(Item[] armorset, ItemStack material){
        GameRegistry.addRecipe(new ItemStack(armorset[0], 1, 0), new Object[]{"XXX", "X X", "   ", 'X', material});
        GameRegistry.addRecipe(new ItemStack(armorset[1], 1, 0), new Object[]{"X X", "XXX", "XXX", 'X', material});
        GameRegistry.addRecipe(new ItemStack(armorset[2], 1, 0), new Object[]{"XXX", "X X", "X X", 'X', material});
        GameRegistry.addRecipe(new ItemStack(armorset[3], 1, 0), new Object[]{"   ", "X X", "X X", 'X', material});
    }

}
