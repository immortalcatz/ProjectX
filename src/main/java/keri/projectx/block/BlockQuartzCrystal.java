package keri.projectx.block;

import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.projectx.ProjectX;
import keri.projectx.client.render.RenderQuartzCrystal;
import keri.projectx.tile.TileEntityQuartzCrystal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockQuartzCrystal extends BlockProjectX<TileEntityQuartzCrystal> {

    public BlockQuartzCrystal() {
        super("quartz_crystal", Material.GLASS);
        this.setHardness(1.3F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{CommonProperties.NBT_TAG_PROPERTY});
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos){
        TileEntityQuartzCrystal tile = (TileEntityQuartzCrystal)world.getTileEntity(pos);

        if(tile != null){
            NBTTagCompound tag = tile.writeToNBT(new NBTTagCompound());
            return ((IExtendedBlockState)state).withProperty(CommonProperties.NBT_TAG_PROPERTY, tag);
        }

        return state;
    }

    @Override
    public TileEntityQuartzCrystal createNewTileEntity(World world, int meta) {
        return new TileEntityQuartzCrystal();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderQuartzCrystal();
    }

}
