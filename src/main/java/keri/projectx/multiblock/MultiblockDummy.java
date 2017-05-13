package keri.projectx.multiblock;

import codechicken.lib.render.CCRenderState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Do this so we can have TileEntityEngineeringBricks as an
 * instance of TileEntityMultiblock
 */
public class MultiblockDummy implements IMultiblock {

    @Override
    public void readMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public void writeMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
        return false;
    }

    @Override
    public void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {

    }

    @Override
    public void invalidate(World world, BlockPos pos) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderMultiblock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        return false;
    }

}
