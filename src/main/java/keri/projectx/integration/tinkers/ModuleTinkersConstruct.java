package keri.projectx.integration.tinkers;

import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockFluidProjectX;
import keri.projectx.fluid.FluidProjectX;
import keri.projectx.integration.IModIntegration;
import keri.projectx.integration.tinkers.block.BlockXycroniumToolForge;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ModuleTinkersConstruct implements IModIntegration {

    public static Fluid fluidMoltenXycroniumBlue;
    public static Fluid fluidMoltenXycroniumGreen;
    public static Fluid fluidMoltenXycroniumRed;
    public static Fluid fluidMoltenXycroniumDark;
    public static Fluid fluidMoltenXycroniumLight;

    public static Block xycroniumToolForge;
    public static Block moltenXycroniumBlue;
    public static Block moltenXycroniumGreen;
    public static Block moltenXycroniumRed;
    public static Block moltenXycroniumDark;
    public static Block moltenXycroniumLight;

    @Override
    public String getModId() {
        return "tconstruct";
    }

    @Override
    public String getName() {
        return "Tinkers Construct";
    }

    @Override
    public boolean isEnabled() {
        return ProjectX.CONFIG.integrationTinkers.getValue();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event, Side side) {
        fluidMoltenXycroniumBlue = FluidProjectX.makeFluid("moltenXycroniumBlue");
        fluidMoltenXycroniumGreen = FluidProjectX.makeFluid("moltenXycroniumGreen");
        fluidMoltenXycroniumRed = FluidProjectX.makeFluid("moltenXycroniumRed");
        fluidMoltenXycroniumDark = FluidProjectX.makeFluid("moltenXycroniumDark");
        fluidMoltenXycroniumLight = FluidProjectX.makeFluid("moltenXycroniumLight");
        moltenXycroniumBlue = new BlockFluidProjectX(fluidMoltenXycroniumBlue);
        moltenXycroniumGreen = new BlockFluidProjectX(fluidMoltenXycroniumGreen);
        moltenXycroniumRed = new BlockFluidProjectX(fluidMoltenXycroniumRed);
        moltenXycroniumDark = new BlockFluidProjectX(fluidMoltenXycroniumDark);
        moltenXycroniumLight = new BlockFluidProjectX(fluidMoltenXycroniumLight);
        xycroniumToolForge = new BlockXycroniumToolForge();
    }

    @Override
    public void init(FMLInitializationEvent event, Side side) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event, Side side) {

    }

}
