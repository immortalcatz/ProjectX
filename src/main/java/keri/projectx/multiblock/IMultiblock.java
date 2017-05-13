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

public interface IMultiblock {

    void readMultiblockNBT(NBTTagCompound tag);

    void writeMultiblockNBT(NBTTagCompound tag);

    boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

    void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

    void invalidate(World world, BlockPos pos);

    @SideOnly(Side.CLIENT)
    boolean renderMultiblock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer);

}
