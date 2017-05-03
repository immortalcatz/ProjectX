package keri.projectx.block.decorative;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.property.EnumDyeColor;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.client.render.RenderXycroniumLadder;
import keri.projectx.tile.TileEntityXycroniumLadder;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockXycroniumLadder extends BlockProjectX<TileEntityXycroniumLadder> implements IMetaBlock, IAnimationSideHandler {

    //TODO fix the actual bounding box of the block...
    //TODO implement climbing on these ladders...

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;
    public static Cuboid6[] BLOCK_BOUNDS = new Cuboid6[]{
            new Cuboid6(0D, 0D, 0D, 2D, 16D, 2D),
            new Cuboid6(14D, 0D, 0D, 16D, 16D, 2D),
            new Cuboid6(2D, 1.5D, 0.5D, 16D, 2.5D, 1.5D),
            new Cuboid6(2D, 5.5D, 0.5D, 16D, 6.5D, 1.5D),
            new Cuboid6(2D, 9.5D, 0.5D, 16D, 10.5D, 1.5D),
            new Cuboid6(2D, 13.5D, 0.5D, 16D, 14.5D, 1.5D)
    };

    public BlockXycroniumLadder() {
        super("xycronium_ladder", Material.ROCK);
        this.setHardness(1.2F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CommonProperties.DYE_COLOR, EnumDyeColor.BLACK));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{CommonProperties.DYE_COLOR}, new IUnlistedProperty[]{CommonProperties.NBT_TAG_PROPERTY});
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(CommonProperties.DYE_COLOR, EnumDyeColor.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumDyeColor)state.getValue(CommonProperties.DYE_COLOR)).getID();
    }

    @Override
    public String[] getSubNames() {
        return EnumDyeColor.toStringArray();
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos){
        TileEntityXycroniumLadder tile = (TileEntityXycroniumLadder)world.getTileEntity(pos);

        if(tile != null && tile.getOrientation() != null){
            return ((IExtendedBlockState)state).withProperty(CommonProperties.NBT_TAG_PROPERTY, tile.writeToNBT(new NBTTagCompound()));
        }

        return state;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityXycroniumLadder tile = (TileEntityXycroniumLadder)world.getTileEntity(pos);
        int orientation = MathHelper.floor(placer.rotationYaw * 4D / 360D + 0.5D) & 3;

        if(tile != null){
            tile.setOrientation(EnumFacing.getHorizontal(orientation));
        }
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        TileEntityXycroniumLadder tile = (TileEntityXycroniumLadder)world.getTileEntity(pos);

        if(tile != null){
            List<IndexedCuboid6> cuboids = Lists.newArrayList();
            Vector3 axis = new Vector3(0D, 1D, 0D);
            double angle = 0D;

            switch(tile.getOrientation()){
                case NORTH:
                    angle = 0D;
                    break;
                case EAST:
                    angle = 270D;
                    break;
                case SOUTH:
                    angle = 180D;
                    break;
                case WEST:
                    angle = 90D;
                    break;
            }

            for(int index = 0; index < BLOCK_BOUNDS.length; index++){
                Cuboid6 cuboid = CommonUtils.devide(BLOCK_BOUNDS[index], 16D).apply(new Rotation(angle * MathHelper.torad, axis).at(Vector3.center));
                cuboids.add(new IndexedCuboid6(index, cuboid));
            }

            return RayTracer.rayTraceCuboidsClosest(start, end, cuboids, pos);
        }

        return super.collisionRayTrace(state, world, pos, start, end);
    }

    @Override
    public TileEntityXycroniumLadder createNewTileEntity(World world, int meta) {
        return new TileEntityXycroniumLadder();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(ModPrefs.MODID + ":blocks/xycronium_ladder");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(IBlockState state, int side) {
        return ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockState state, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ColourRGBA getAnimationColor(IBlockState state, int side) {
        return EnumDyeColor.values()[state.getBlock().getMetaFromState(state)].getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(ItemStack stack, int side) {
        return ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ColourRGBA getAnimationColor(ItemStack stack, int side) {
        return EnumDyeColor.values()[stack.getMetadata()].getColor();
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
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderXycroniumLadder();
    }

}
