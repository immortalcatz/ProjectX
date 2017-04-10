package keri.projectx.integration.tinkers;

import keri.projectx.integration.IModIntegration;
import keri.projectx.integration.tinkers.block.BlockXycroniumToolForge;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ModuleTinkersConstruct implements IModIntegration {

    public static Block xycroniumToolForge;

    @Override
    public String getModId() {
        return "tconstruct";
    }

    @Override
    public String getName() {
        return "Tinkers Construct";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event, Side side) {
        xycroniumToolForge = new BlockXycroniumToolForge();
    }

    @Override
    public void init(FMLInitializationEvent event, Side side) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event, Side side) {

    }

}
