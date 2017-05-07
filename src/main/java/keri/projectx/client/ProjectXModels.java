package keri.projectx.client;

import keri.ninetaillib.render.fms.FMSModel;
import keri.ninetaillib.render.fms.FMSModelLoader;
import keri.projectx.util.ModPrefs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProjectXModels {

    private static final FMSModelLoader modelLoader = new FMSModelLoader();

    public static void preInit(FMLPreInitializationEvent event){
        modelLoader.registerModel("tank_valve", new ResourceLocation(ModPrefs.MODID, "models/tank_valve"));
        modelLoader.loadModels(event);
    }

    public static FMSModel getModel(String name){
        return modelLoader.getModel(name);
    }

}
