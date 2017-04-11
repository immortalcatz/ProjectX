package keri.projectx.block;

import keri.ninetaillib.block.BlockFluidBase;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidProjectX extends BlockFluidBase {

    public BlockFluidProjectX(Fluid fluid) {
        super(ModPrefs.MODID, fluid);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

}
