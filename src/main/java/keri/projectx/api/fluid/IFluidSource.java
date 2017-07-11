package keri.projectx.api.fluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;

/**
 * Get a fluid stored in a block. You can use tile interaction or just send a fluid stack to have unlimited water
 */
public interface IFluidSource {
    public FluidStack getFluid(IBlockAccess world, BlockPos pos, IBlockState state);
}
