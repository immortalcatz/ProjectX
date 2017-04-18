package keri.projectx.multiblock;

import keri.ninetaillib.multiblock.MultiblockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

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
        MultiblockPattern patternTier1 = ProjectXMultiblocks.getMultiblock("tank_tier_1");
        MultiblockPattern patternTier2 = ProjectXMultiblocks.getMultiblock("tank_tier_2");
        return patternTier1.isValid(world, pos, side) || patternTier2.isValid(world, pos, side);
    }

    @Override
    public void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side){
        FMLLog.info("HELLO WORLD");
    }

}
