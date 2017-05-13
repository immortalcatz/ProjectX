package keri.projectx.multiblock;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.multiblock.MultiblockPattern;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.client.ProjectXModels;
import keri.projectx.tile.TileEntityMultiblock;
import keri.projectx.tile.TileEntityTankValve;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
        CCModel[] model = ProjectXModels.getModel("multiblock_tank_tier_1").getModel();
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        TileEntityTankValve tile = (TileEntityTankValve)world.getTileEntity(pos);
        TextureAtlasSprite textureSide = iconProvider.getIcon(0, 0);
        TextureAtlasSprite textureBottom = iconProvider.getIcon(0, 1);
        TextureAtlasSprite textureTop = iconProvider.getIcon(0, 2);

        if(tile != null){
            if(tile.getIsMaster()){
                for(int part = 0; part < model.length; part++){
                    model[part].apply(new Translation(Vector3.fromBlockPos(pos)));

                    switch(tile.getMultiblockOrientation()){
                        case NORTH:
                            model[part].apply(new Translation(new Vector3(-1D, 0D, 0D)));
                            break;
                        case EAST:
                            model[part].apply(new Translation(new Vector3(-2D, 0D, -1D)));
                            break;
                        case SOUTH:
                            model[part].apply(new Translation(new Vector3(-1D, 0D, -2D)));
                            break;
                        case WEST:
                            model[part].apply(new Translation(new Vector3(0D, 0D, -1D)));
                            break;
                    }
                }
            }
        }

        return false;
    }

}
