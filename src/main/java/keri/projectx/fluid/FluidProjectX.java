package keri.projectx.fluid;

import keri.ninetaillib.fluid.FluidBase;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.projectx.ProjectX;
import keri.projectx.util.ModPrefs;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidProjectX extends FluidBase {

    private FluidProjectX(String modid, String fluidName) {
        super(modid, fluidName);
    }

    public static Fluid makeFluid(String fluidName){
        FluidProjectX fluid = new FluidProjectX(ModPrefs.MODID, fluidName);
        return fluid;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderingRegistry getRenderingRegistry() {
        return ProjectX.PROXY.getRenderingRegistry();
    }

}
