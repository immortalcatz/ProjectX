package keri.projectx.block;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.property.ProjectXProperties;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXycroniumBlock extends BlockCustomRenderer implements IMetaBlock {

    public BlockXycroniumBlock() {
        super("xycronium_block", Material.ROCK);
        this.setHardness(1.6F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{ProjectXProperties.XY_COLOR});
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ProjectXProperties.XY_COLOR, EnumXycroniumColor.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumXycroniumColor type = (EnumXycroniumColor)state.getValue(ProjectXProperties.XY_COLOR);
        return type.getID();
    }

    @Override
    public String[] getSubNames() {
        return EnumXycroniumColor.toStringArray();
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(ModPrefs.MODID + ":blocks/xycronium_block");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(int meta, int side) {
        return ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(int meta, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ColourRGBA getAnimationColor(int meta, int side) {
        return EnumXycroniumColor.values()[meta].getColor();
    }

}
