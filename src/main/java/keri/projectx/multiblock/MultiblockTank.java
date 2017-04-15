package keri.projectx.multiblock;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MultiblockTank implements IMultiblock {

    @Override
    public void readMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public NBTTagCompound writeMultiblockNBT(NBTTagCompound tag){
        return tag;
    }

    @Override
    public boolean isValid(World world, BlockPos pos) {
        return true;
    }

    @Override
    public void onFormed(World world, BlockPos pos){
        List<BlockPos> subBlocks = Lists.newArrayList();

        for(EnumFacing side : EnumFacing.VALUES){
            if(world.getBlockState(pos.offset(side)) != null && !world.isAirBlock(pos.offset(side))){
                subBlocks.add(pos.offset(side));
            }
        }

        MultiblockTankHandler.INSTANCE.addMultiblockTank(pos, subBlocks);
    }

}
