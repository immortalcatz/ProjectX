package keri.projectx.init;

import keri.ninetaillib.item.ItemArmorHelper;
import keri.projectx.item.*;
import keri.projectx.util.ModPrefs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ProjectXContent {

    public static Item xycroniumCrystal;
    public static Item xycroniumIngot;
    public static Item xycroniumNugget;
    public static Item xycroniumDust;

    private static Item.ToolMaterial toolMaterialXycronium = EnumHelper.addToolMaterial("xycronium", 2, 700, 6.1F, 2.1F, 15);
    private static ItemToolHelperProjectX toolHelper = new ItemToolHelperProjectX();
    public static Item[] xycroniumToolsBlue;
    public static Item[] xycroniumToolsGreen;
    public static Item[] xycroniumToolsRed;
    public static Item[] xycroniumToolsDark;
    public static Item[] xycroniumToolsLight;

    private static ItemArmor.ArmorMaterial armorMaterialXycronium = EnumHelper.addArmorMaterial("xycronium", ModPrefs.MODID + ":xycronium", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F);
    private static ItemArmorHelper armorHelper = new ItemArmorHelperProjectX();
    public static Item[] xycroniumArmorBlue;
    public static Item[] xycroniumArmorGreen;
    public static Item[] xycroniumArmorRed;
    public static Item[] xycroniumArmorDark;
    public static Item[] xycroniumArmorLight;

    public static void preInit(){
        xycroniumCrystal = new ItemXycroniumCrystal();
        xycroniumIngot = new ItemXycroniumIngot();
        xycroniumNugget = new ItemXycroniumNugget();
        xycroniumDust = new ItemXycroniumDust();

        xycroniumToolsBlue = toolHelper.createToolset(toolMaterialXycronium);
        xycroniumToolsGreen = toolHelper.createToolset(toolMaterialXycronium);
        xycroniumToolsRed = toolHelper.createToolset(toolMaterialXycronium);
        xycroniumToolsDark = toolHelper.createToolset(toolMaterialXycronium);
        xycroniumToolsLight = toolHelper.createToolset(toolMaterialXycronium);

        xycroniumArmorBlue = armorHelper.createArmorSet("xycronium_blue", armorMaterialXycronium);
        xycroniumArmorGreen = armorHelper.createArmorSet("xycronium_green", armorMaterialXycronium);
        xycroniumArmorRed = armorHelper.createArmorSet("xycronium_red", armorMaterialXycronium);
        xycroniumArmorDark = armorHelper.createArmorSet("xycronium_dark", armorMaterialXycronium);
        xycroniumArmorLight = armorHelper.createArmorSet("xycronium_light", armorMaterialXycronium);
    }

    public static void init(){

    }

}
