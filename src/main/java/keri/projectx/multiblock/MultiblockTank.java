package keri.projectx.multiblock;

import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.multiblock.MultiblockPattern;
import keri.projectx.tile.TileEntityMultiblock;
import keri.projectx.tile.TileEntityTankValve;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MultiblockTank implements IMultiblock {

    @Override
    public void readMultiblockNBT(NBTTagCompound tag) {

    }

    @Override
    public void writeMultiblockNBT(NBTTagCompound tag){

    }

    @Override
    public boolean isValid(World world, BlockPos pos, EntityPlayer player, EnumFacing side){
        MultiblockPattern patternTier1 = ProjectXMultiblocks.getMultiblock("tank_tier_1");
        MultiblockPattern patternTier2 = ProjectXMultiblocks.getMultiblock("tank_tier_2");
        return patternTier1.isValid(world, pos, side) || patternTier2.isValid(world, pos, side);
    }

    @Override
    public void onFormed(World world, BlockPos pos, EntityPlayer player, EnumFacing side){
        MultiblockPattern patternTier1 = ProjectXMultiblocks.getMultiblock("tank_tier_1");
        MultiblockPattern patternTier2 = ProjectXMultiblocks.getMultiblock("tank_tier_2");

        if(patternTier1.isValid(world, pos, side)){
            TileEntityTankValve masterTile = (TileEntityTankValve)world.getTileEntity(pos);
            List<BlockPos> blocks = patternTier1.compilePositions(pos, side);

            for(BlockPos partPos : blocks){
                if(partPos != pos && !world.isAirBlock(partPos)){
                    masterTile.addSlaveBlock(partPos);
                    TileEntity tile = (TileEntity)world.getTileEntity(partPos);

                    if(tile != null && tile instanceof TileEntityMultiblock){
                        TileEntityMultiblock multiblockTile = (TileEntityMultiblock)tile;
                        multiblockTile.setIsSlave(true);
                        multiblockTile.setMasterBlock(pos);
                        multiblockTile.markDirty();
                    }
                }
            }
        }
        else if(patternTier2.isValid(world, pos, side)){
            TileEntityTankValve masterTile = (TileEntityTankValve)world.getTileEntity(pos);
            List<BlockPos> blocks = patternTier2.compilePositions(pos, side);

            for(BlockPos partPos : blocks){
                if(partPos != pos && !world.isAirBlock(partPos)){
                    masterTile.addSlaveBlock(partPos);
                    TileEntity tile = (TileEntity)world.getTileEntity(partPos);

                    if(tile != null && tile instanceof TileEntityMultiblock){
                        TileEntityMultiblock multiblockTile = (TileEntityMultiblock)tile;
                        multiblockTile.setIsSlave(true);
                        multiblockTile.setMasterBlock(pos);
                        multiblockTile.markDirty();
                    }
                }
            }
        }
    }

    @Override
    public void invalidate(World world, BlockPos pos) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderMultiblock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer){
        return false;
    }

}
