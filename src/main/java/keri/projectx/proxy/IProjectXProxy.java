package keri.projectx.proxy;

import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProjectXProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    IRenderingRegistry getRenderingRegistry();

    IIconRegistrar getIconRegistrar();

}
