package keri.projectx;

import keri.projectx.init.ProjectXConfig;
import keri.projectx.init.ProjectXContent;
import keri.projectx.init.ProjectXCrafting;
import keri.projectx.proxy.IProjectXProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static keri.projectx.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class ProjectX {

    @Mod.Instance(value = MODID)
    public static ProjectX INSTANCE = new ProjectX();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static IProjectXProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger(NAME);
    public static ProjectXConfig CONFIG;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        CONFIG = new ProjectXConfig(event);
        ProjectXContent.preInit();
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        ProjectXContent.init();
        ProjectXCrafting.init();
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        PROXY.postInit(event);
    }

}
