package keri.projectx.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Do this so we can have TileEntityEngineeringFrame as an
 * instance of TileEntityMultiblock
 */
public class MultiblockDummy implements IMultiblock {

    @Override
    public void readMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public NBTTagCompound writeMultiblockNBT(NBTTagCompound tag) {
        return tag;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
        return false;
    }

    @Override
    public void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {

    }

}
