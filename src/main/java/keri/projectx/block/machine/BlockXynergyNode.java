package keri.projectx.block.machine;

import keri.ninetaillib.property.CommonProperties;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.tile.TileEntityXynergyNode;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockXynergyNode extends BlockProjectX<TileEntityXynergyNode> {

    public BlockXynergyNode() {
        super("xynergy_node", Material.IRON);
        this.setHardness(1.4F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{CommonProperties.NBT_TAG_PROPERTY});
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            return ((IExtendedBlockState)state).withProperty(CommonProperties.NBT_TAG_PROPERTY, tile.writeToNBT(new NBTTagCompound()));
        }

        return state;
    }

    @Override
    public TileEntityXynergyNode createNewTileEntity(World world, int meta) {
        return new TileEntityXynergyNode();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            if(world.isAirBlock(pos.offset(EnumFacing.DOWN)) && world.isAirBlock(pos.offset(EnumFacing.UP))){
                int direction = MathHelper.floor_double(placer.rotationYaw * 4D / 360D + 0.5D) & 3;
                tile.setOrientation(EnumFacing.getHorizontal(direction));
                tile.markDirty();
            }
            else{
                if(!world.isAirBlock(pos.offset(EnumFacing.DOWN)) && world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock().isFullCube(state)){
                    tile.setOrientation(EnumFacing.DOWN);
                    tile.markDirty();
                }
                else if(!world.isAirBlock(pos.offset(EnumFacing.UP)) && world.getBlockState(pos.offset(EnumFacing.UP)).getBlock().isFullCube(state)){
                    tile.setOrientation(EnumFacing.UP);
                    tile.markDirty();
                }
            }
        }
    }

}
