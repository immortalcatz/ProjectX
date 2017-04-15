package keri.projectx.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock {

    void readMultiblockNBT(NBTTagCompound tag);

    NBTTagCompound writeMultiblockNBT(NBTTagCompound tag);

    boolean isValid(World world, BlockPos pos);

    void onFormed(World world, BlockPos pos);

}
