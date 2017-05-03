package keri.projectx.integration.tinkers.block;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.raytracer.RayTracer;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.integration.tinkers.client.render.RenderXycroniumToolForge;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.property.ProjectXProperties;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.common.tileentity.TileToolForge;

import javax.annotation.Nullable;

public class BlockXycroniumToolForge extends BlockProjectX<TileToolForge> implements IMetaBlock, IAnimationSideHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;
    public static AxisAlignedBB[] BLOCK_BOUNDS = new AxisAlignedBB[]{
            new AxisAlignedBB(0D, 0.75D, 0D, 1D, 1D, 1D),
            new AxisAlignedBB(0D, 0D, 0D, 0.25D, 0.75D, 0.25D),
            new AxisAlignedBB(0.75D, 0D, 0D, 1D, 0.75D, 0.25D),
            new AxisAlignedBB(0.75D, 0D, 0.75D, 1D, 0.75D, 1D),
            new AxisAlignedBB(0D, 0D, 0.75D, 0.25D, 0.75D, 1D)
    };

    public BlockXycroniumToolForge(){
        super("tool_forge", Material.IRON);
        this.setResistance(10F);
        this.setHardness(2F);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{ProjectXProperties.XYCRONIUM_COLOR});
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ProjectXProperties.XYCRONIUM_COLOR, EnumXycroniumColor.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumXycroniumColor)state.getValue(ProjectXProperties.XYCRONIUM_COLOR)).getID();
    }

    @Override
    public String[] getSubNames() {
        return EnumXycroniumColor.toStringArray();
    }

    @Override
    public TileToolForge createNewTileEntity(World world, int meta) {
        return new TileToolForge();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote && !player.isSneaking()){
            player.openGui(TConstruct.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());

            if(player.openContainer instanceof BaseContainer){
                ((BaseContainer)player.openContainer).syncOnOpen((EntityPlayerMP)player);
            }
        }

        return true;
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        return RayTracer.rayTraceCuboidsClosest(start, end, pos, BLOCK_BOUNDS);
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
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = registrar.registerIcon(ModPrefs.MODID + ":blocks/tinkers/tool_forge_top");
        this.texture[1] = registrar.registerIcon(ModPrefs.MODID + ":blocks/tinkers/tool_forge_side");
        this.texture[2] = registrar.registerIcon(ModPrefs.MODID + ":blocks/tinkers/tool_forge_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[side];
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
        return EnumXycroniumColor.values()[state.getBlock().getMetaFromState(state)].getColor();
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
        return EnumXycroniumColor.values()[stack.getMetadata()].getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderXycroniumToolForge();
    }

}
