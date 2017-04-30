package keri.projectx.block.decorative;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.block.base.BlockSimpleGlow;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.property.ProjectXProperties;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXycroniumBricks extends BlockSimpleGlow implements IMetaBlock {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public BlockXycroniumBricks() {
        super("xycronium_bricks", Material.ROCK);
        this.setHardness(1.4F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ProjectXProperties.XYCRONIUM_COLOR, EnumXycroniumColor.BLUE));
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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(ModPrefs.MODID + ":blocks/xycronium_bricks");
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
    public ColourRGBA getColorMultiplier(IBlockState state, int side) {
        return new ColourRGBA(EnumXycroniumColor.values()[state.getBlock().getMetaFromState(state)].getColor().copy().multiplyC(0.7D).rgba());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ColourRGBA getColorMultiplier(ItemStack stack, int side) {
        return new ColourRGBA(EnumXycroniumColor.values()[stack.getMetadata()].getColor().copy().multiplyC(0.7D).rgba());
    }

}
