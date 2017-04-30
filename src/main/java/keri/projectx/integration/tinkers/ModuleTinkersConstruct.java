package keri.projectx.integration.tinkers;

import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockFluidProjectX;
import keri.projectx.fluid.FluidProjectX;
import keri.projectx.init.ProjectXContent;
import keri.projectx.integration.IModIntegration;
import keri.projectx.integration.tinkers.block.BlockXycroniumToolForge;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.stream.IntStream;

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
        fluidMoltenXycroniumBlue = FluidProjectX.makeFluid("molten_xycronium_blue");
        fluidMoltenXycroniumGreen = FluidProjectX.makeFluid("molten_xycronium_green");
        fluidMoltenXycroniumRed = FluidProjectX.makeFluid("molten_xycronium_red");
        fluidMoltenXycroniumDark = FluidProjectX.makeFluid("molten_xycronium_dark");
        fluidMoltenXycroniumLight = FluidProjectX.makeFluid("molten_xycronium_light");
        moltenXycroniumBlue = new BlockFluidProjectX(fluidMoltenXycroniumBlue);
        moltenXycroniumGreen = new BlockFluidProjectX(fluidMoltenXycroniumGreen);
        moltenXycroniumRed = new BlockFluidProjectX(fluidMoltenXycroniumRed);
        moltenXycroniumDark = new BlockFluidProjectX(fluidMoltenXycroniumDark);
        moltenXycroniumLight = new BlockFluidProjectX(fluidMoltenXycroniumLight);
        xycroniumToolForge = new BlockXycroniumToolForge();
    }

    @Override
    public void init(FMLInitializationEvent event, Side side) {
        IntStream.range(0, 5).forEach(meta -> {
            GameRegistry.addRecipe(new ItemStack(xycroniumToolForge, 1, meta), new Object[]{"XXX", "VCV", "V V", 'V', new ItemStack(ProjectXContent.xycroniumBlock, 1, meta), 'C', new ItemStack(TinkerTools.toolTables, 1, 3), 'X', TinkerSmeltery.searedBlock});
        });
    }

    @Override
    public void postInit(FMLPostInitializationEvent event, Side side) {

    }

}
