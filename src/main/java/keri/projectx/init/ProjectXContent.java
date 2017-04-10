package keri.projectx.init;

import keri.projectx.item.*;
import keri.projectx.util.ModPrefs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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

    private static ItemArmorHelperProjectX armorHelper = new ItemArmorHelperProjectX();
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

        xycroniumToolsBlue = toolHelper.createToolset("xycronium_blue", toolMaterialXycronium);
        xycroniumToolsGreen = toolHelper.createToolset("xycronium_green", toolMaterialXycronium);
        xycroniumToolsRed = toolHelper.createToolset("xycronium_red", toolMaterialXycronium);
        xycroniumToolsDark = toolHelper.createToolset("xycronium_dark", toolMaterialXycronium);
        xycroniumToolsLight = toolHelper.createToolset("xycronium_light", toolMaterialXycronium);

        xycroniumArmorBlue = armorHelper.createArmorSet("xycronium_blue", EnumHelper.addArmorMaterial("xycronium_blue", ModPrefs.MODID + ":xycronium_blue", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F));
        xycroniumArmorGreen = armorHelper.createArmorSet("xycronium_green", EnumHelper.addArmorMaterial("xycronium_green", ModPrefs.MODID + ":xycronium_green", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F));
        xycroniumArmorRed = armorHelper.createArmorSet("xycronium_red", EnumHelper.addArmorMaterial("xycronium_red", ModPrefs.MODID + ":xycronium_red", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F));
        xycroniumArmorDark = armorHelper.createArmorSet("xycronium_dark", EnumHelper.addArmorMaterial("xycronium_dark", ModPrefs.MODID + ":xycronium_dark", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F));
        xycroniumArmorLight = armorHelper.createArmorSet("xycronium_light", EnumHelper.addArmorMaterial("xycronium_light", ModPrefs.MODID + ":xycronium_light", 16, new int[]{2, 5, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F));
    }

    public static void init(){

    }

}
