package keri.projectx.block.machine;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.client.renderold.RenderSimpleGlow;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.property.ProjectXProperties;
import keri.projectx.tile.TileEntityEngineeringFrame;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEngineeringFrame extends BlockProjectX<TileEntityEngineeringFrame> implements IMetaBlock, IAnimationSideHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockEngineeringFrame(){
        super("engineering_frame", Material.IRON);
        this.setHardness(1.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ProjectXProperties.XYCRONIUM_COLOR, EnumXycroniumColor.BLUE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{ProjectXProperties.XYCRONIUM_COLOR}, new IUnlistedProperty[]{CommonProperties.NBT_TAG_PROPERTY});
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
    public TileEntityEngineeringFrame createNewTileEntity(World world, int meta) {
        return new TileEntityEngineeringFrame();
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityEngineeringFrame tile = (TileEntityEngineeringFrame)world.getTileEntity(pos);

        if(tile != null){
            return ((IExtendedBlockState)state).withProperty(CommonProperties.NBT_TAG_PROPERTY, tile.writeToNBT(new NBTTagCompound()));
        }

        return state;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[2];
        this.texture[0] = registrar.registerIcon(ModPrefs.MODID + ":blocks/machine/engineering_frame_frame");
        this.texture[1] = registrar.registerIcon(ModPrefs.MODID + ":blocks/machine/engineering_frame_base");
    }

    @Override
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
        return new RenderSimpleGlow();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

}
