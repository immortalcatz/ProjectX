package keri.projectx.client;

import keri.ninetaillib.render.fms.FMSModel;
import keri.ninetaillib.render.fms.FMSModelLoader;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProjectXModels {

    private static final FMSModelLoader modelLoader = new FMSModelLoader();

    public static void preInit(FMLPreInitializationEvent event){
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(modelLoader);
        modelLoader.registerModel("tool_forge", new ResourceLocation(ModPrefs.MODID, "models/tool_forge"));
        modelLoader.registerModel("tank_valve", new ResourceLocation(ModPrefs.MODID, "models/tank_valve"));
        modelLoader.registerModel("engineering_table", new ResourceLocation(ModPrefs.MODID, "models/engineering_table"));
        modelLoader.loadModels(event);
    }

    public static FMSModel getModel(String name){
        return modelLoader.getModel(name);
    }

}
