package keri.projectx.block.machine;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.client.renderold.RenderXynergyNode;
import keri.projectx.tile.TileEntityXynergyNode;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXynergyNode extends BlockProjectX<TileEntityXynergyNode> implements IAnimationSideHandler {

    private static final Cuboid6 BLOCK_BOUNDS = new Cuboid6(0.25D, 0D, 0.25D, 0.75D, 0.1875D, 0.75D);
    private static Cuboid6[] ORIENTED_BLOCK_BOUNDS;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    static{
        ORIENTED_BLOCK_BOUNDS = new Cuboid6[6];
        Cuboid6 boundsDown = BLOCK_BOUNDS.copy();
        ORIENTED_BLOCK_BOUNDS[0] = boundsDown;
        Cuboid6 boundsUp = BLOCK_BOUNDS.copy();
        boundsUp.apply(new Rotation(180D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
        ORIENTED_BLOCK_BOUNDS[1] = boundsUp;
        Cuboid6 boundsNorth = BLOCK_BOUNDS.copy();
        boundsNorth.apply(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
        ORIENTED_BLOCK_BOUNDS[2] = boundsNorth;
        Cuboid6 boundsEast = BLOCK_BOUNDS.copy();
        boundsEast.apply(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
        boundsEast.apply(new Rotation(270D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
        ORIENTED_BLOCK_BOUNDS[3] = boundsEast;
        Cuboid6 boundsSouth = BLOCK_BOUNDS.copy();
        boundsSouth.apply(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
        boundsSouth.apply(new Rotation(180D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
        ORIENTED_BLOCK_BOUNDS[4] = boundsSouth;
        Cuboid6 boundsWest = BLOCK_BOUNDS.copy();
        boundsWest.apply(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
        boundsWest.apply(new Rotation(90D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
        ORIENTED_BLOCK_BOUNDS[5] = boundsWest;
    }

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
            int pitch = MathHelper.roundAway(placer.rotationPitch);

            if(pitch >= 10){
                tile.setOrientation(EnumFacing.DOWN);
            }
            else if(pitch <= -10){
                tile.setOrientation(EnumFacing.UP);
            }
            else{
                tile.setOrientation(EnumFacing.fromAngle(placer.rotationYaw));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(ModPrefs.MODID + ":blocks/xynergy_node");
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
        return new ColourRGBA(0, 255, 100, 255);
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
        return new ColourRGBA(0, 255, 100, 255);
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
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderXynergyNode();
    }

}
