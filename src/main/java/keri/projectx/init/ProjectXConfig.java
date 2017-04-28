package keri.projectx.init;

import com.google.common.collect.Lists;
import keri.ninetaillib.config.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ProjectXConfig extends ConfigManagerBase {

    private static final String categoryIntegration = "mod_integration";
    private static final String categoryRendering = "rendering";
    private static final String categoryWorldgen = "worldgen_ores";
    private static final String commentCategoryIntegration = "enable/disable mod integration modules";
    private static final String commentCategoryRendering = "rendering tweaks that (may) improve performance";
    private static final String commentCategoryWorldgen = "tweak the worldgen of ores etc.";
    private static final String commentWorldgenOre = "chance, min height, max height, min veinsize, max veinsize";
    public ConfigBoolean integrationTinkers;
    public ConfigBoolean integrationThermalExpansion;
    public ConfigBoolean integrationIC2;
    public ConfigBoolean integrationEnderIO;
    public ConfigBoolean fastItemRendering;
    public ConfigBoolean fancyBoundingBoxes;
    public ConfigInteger animationResolution;
    public ConfigIntArray worldgenOreXycroniumBlue;
    public ConfigIntArray worldgenOreXycroniumGreen;
    public ConfigIntArray worldgenOreXycroniumRed;
    public ConfigIntArray worldgenOreXycroniumDark;
    public ConfigIntArray worldgenOreXycroniumLight;

    public ProjectXConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public List<Pair<String, String>> getCategories() {
        List<Pair<String, String>> list = Lists.newArrayList();
        list.add(Pair.of(this.categoryIntegration, this.commentCategoryIntegration));
        list.add(Pair.of(this.categoryRendering, this.commentCategoryRendering));
        list.add(Pair.of(this.categoryWorldgen, this.commentCategoryWorldgen));
        return list;
    }

    @Override
    public void addConfigComponents(List<IConfigComponent<?>> components) {
        components.add(this.integrationTinkers = new ConfigBoolean(categoryIntegration, "integrationTinkersConstruct", true));
        components.add(this.integrationThermalExpansion = new ConfigBoolean(categoryIntegration, "integrationThermalExpansion", true));
        components.add(this.integrationIC2 = new ConfigBoolean(categoryIntegration, "integrationIC2", true));
        components.add(this.integrationEnderIO = new ConfigBoolean(categoryIntegration, "integrationEnderIO", true));
        components.add(this.fastItemRendering = new ConfigBoolean(categoryRendering, "fastItemRendering", false));
        components.add(this.fancyBoundingBoxes = new ConfigBoolean(categoryRendering, "fancyBoundingBoxes", true));
        components.add(this.animationResolution = new ConfigInteger(categoryRendering, "animationResolution", 32, 16, 128));
        components.add(this.worldgenOreXycroniumBlue = new ConfigIntArray(categoryWorldgen, "xycroniumOreBlue", commentWorldgenOre, new int[]{14, 12,80, 4, 7}));
        components.add(this.worldgenOreXycroniumGreen = new ConfigIntArray(categoryWorldgen, "xycroniumOreGreen", commentWorldgenOre, new int[]{14, 12,80, 4, 7}));
        components.add(this.worldgenOreXycroniumRed = new ConfigIntArray(categoryWorldgen, "xycroniumOreRed", commentWorldgenOre, new int[]{14, 12,80, 4, 7}));
        components.add(this.worldgenOreXycroniumDark = new ConfigIntArray(categoryWorldgen, "xycroniumOreDark", commentWorldgenOre, new int[]{14, 12,80, 4, 7}));
        components.add(this.worldgenOreXycroniumLight = new ConfigIntArray(categoryWorldgen, "xycroniumOreLight", commentWorldgenOre, new int[]{14, 12,80, 4, 7}));
    }

}
