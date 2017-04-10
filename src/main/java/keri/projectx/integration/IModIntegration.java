package keri.projectx.integration;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public interface IModIntegration {

    String getModId();

    String getName();

    void preInit(FMLPreInitializationEvent event, Side side);

    void init(FMLInitializationEvent event, Side side);

    void postInit(FMLPostInitializationEvent event, Side side);

}
