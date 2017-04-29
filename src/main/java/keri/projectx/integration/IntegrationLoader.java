package keri.projectx.integration;

import com.google.common.collect.Lists;
import keri.projectx.ProjectX;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class IntegrationLoader {

    public static final IntegrationLoader INSTANCE = new IntegrationLoader();
    private List<IModIntegration> integrationModules = Lists.newArrayList();

    public void preInit(FMLPreInitializationEvent event, Side side){
        ProjectX.LOGGER.info("[Integration] Loading integration modules...");

        this.integrationModules.forEach(module -> {
            if(Loader.isModLoaded(module.getModId()) && module.isEnabled()){
                module.preInit(event, side);
            }
        });

        ProjectX.LOGGER.info("[Integration] All integration modules loaded!");
    }

    public void init(FMLInitializationEvent event, Side side){
        this.integrationModules.forEach(module -> {
            if(Loader.isModLoaded(module.getModId()) && module.isEnabled()){
                module.init(event, side);
                ProjectX.LOGGER.info("[Integration] " + module.getName() + " integration initialized!");
            }
        });
    }

    public void postInit(FMLPostInitializationEvent event, Side side){
        this.integrationModules.forEach(module -> {
            if(Loader.isModLoaded(module.getModId()) && module.isEnabled()){
                module.postInit(event, side);
            }
        });
    }

    public void registerModule(IModIntegration module){
        if(module != null){
            this.integrationModules.add(module);
        }
        else{
            throw new IllegalArgumentException("Integration module can't be null!");
        }
    }

}
