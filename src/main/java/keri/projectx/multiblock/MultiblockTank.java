package keri.projectx.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiblockTank implements IMultiblock {

    @Override
    public void readMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public NBTTagCompound writeMultiblockNBT(NBTTagCompound tag){
        return tag;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side){
        return ProjectXMultiblocks.getMultiblock("tank_tier_1").isValid(world, pos, side);
    }

    @Override
    public void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side){

    }

}
