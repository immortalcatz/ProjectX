package keri.projectx.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock {

    void readMultiblockNBT(NBTTagCompound tag);

    NBTTagCompound writeMultiblockNBT(NBTTagCompound tag);

    boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

    void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

}
