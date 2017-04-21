package keri.projectx.init;

import com.google.common.collect.Lists;
import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class ProjectXConfig extends ConfigManagerBase {

    //categories
    private static final String categoryIntegration = "mod_integration";
    private static final String categoryRendering = "rendering";
    private static final String categoryWorldgen = "worldgen_ores";
    //category comments
    private static final String commentCategoryIntegration = "enable/disable mod integration modules";
    private static final String commentCategoryRendering = "rendering tweaks that (may) improve performance";
    private static final String commentCategoryWorldgen = "tweak the worldgen of ores etc.";
    //comments
    private static final String commentWorldgenOre = "chance, min height, max height, min veinsize, max veinsize";
    //default values
    private static final boolean integrationTinkersDefault = true;
    private static final boolean integrationThermalExpansionDefault = true;
    private static final boolean integrationIC2Default = true;
    private static final boolean integrationEnderIODefault = true;
    private static final boolean fastItemRenderingDefault = false;
    private static final boolean fancyBoundingBoxesDefault = true;
    private static final int[] worldgenOreXycroniumBlueDefault = new int[]{60, 14, 60, 3, 6};
    private static final int[] worldgenOreXycroniumGreenDefault = new int[]{60, 14, 60, 3, 6};
    private static final int[] worldgenOreXycroniumRedDefault = new int[]{60, 14, 60, 3, 6};
    private static final int[] worldgenOreXycroniumDarkDefault = new int[]{60, 14, 60, 3, 6};
    private static final int[] worldgenOreXycroniumLightDefault = new int[]{60, 14, 60, 3, 6};
    //values
    public boolean integrationTinkers;
    public boolean integrationThermalExpansion;
    public boolean integrationIC2;
    public boolean integrationEnderIO;
    public boolean fastItemRendering;
    public boolean fancyBoundingBoxes;
    public int[] worldgenOreXycroniumBlue;
    public int[] worldgenOreXycroniumGreen;
    public int[] worldgenOreXycroniumRed;
    public int[] worldgenOreXycroniumDark;
    public int[] worldgenOreXycroniumLight;

    public ProjectXConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public ArrayList<Pair<String, String>> getCategories() {
        ArrayList<Pair<String, String>> list = Lists.newArrayList();
        list.add(Pair.of(this.categoryIntegration, this.commentCategoryIntegration));
        list.add(Pair.of(this.categoryRendering, this.commentCategoryRendering));
        list.add(Pair.of(this.categoryWorldgen, this.commentCategoryWorldgen));
        return list;
    }

    @Override
    public void getConfigFlags(Configuration config) {
        this.integrationTinkers = config.get(this.categoryIntegration, "tinkersConstruct", this.integrationTinkersDefault).getBoolean();
        this.integrationThermalExpansion = config.get(this.categoryIntegration, "thermalExpansion", this.integrationThermalExpansionDefault).getBoolean();
        this.integrationIC2 = config.get(this.categoryIntegration, "industrialCraft", this.integrationIC2Default).getBoolean();
        this.integrationEnderIO = config.get(this.categoryIntegration, "enderIO", this.integrationEnderIODefault).getBoolean();
        this.fastItemRendering = config.get(this.categoryRendering, "fastItemRendering", this.fastItemRenderingDefault).getBoolean();
        this.fancyBoundingBoxes = config.get(this.categoryRendering, "fancyBoundingBoxes", this.fancyBoundingBoxesDefault).getBoolean();
        this.worldgenOreXycroniumBlue = config.get(this.categoryWorldgen, "xycroniumOreBlue", this.worldgenOreXycroniumBlueDefault, this.commentWorldgenOre).getIntList();
        this.worldgenOreXycroniumGreen = config.get(this.categoryWorldgen, "xycroniumOreGreen", this.worldgenOreXycroniumGreenDefault, this.commentWorldgenOre).getIntList();
        this.worldgenOreXycroniumRed = config.get(this.categoryWorldgen, "xycroniumOreRed", this.worldgenOreXycroniumRedDefault, this.commentWorldgenOre).getIntList();
        this.worldgenOreXycroniumDark = config.get(this.categoryWorldgen, "xycroniumOreDark", this.worldgenOreXycroniumDarkDefault, this.commentWorldgenOre).getIntList();
        this.worldgenOreXycroniumLight = config.get(this.categoryWorldgen, "xycroniumOreLight", this.worldgenOreXycroniumLightDefault, this.commentWorldgenOre).getIntList();
    }

}
