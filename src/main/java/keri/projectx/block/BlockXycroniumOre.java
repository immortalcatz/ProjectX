package keri.projectx.block;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.texture.IIconRegistrar;
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

import java.util.stream.IntStream;

public class BlockXycroniumOre extends BlockCustomRenderer implements IMetaBlock {

    public BlockXycroniumOre() {
        super("xycronium_ore", Material.ROCK);
        this.setHardness(1.8F);
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
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[6];

        IntStream.range(0, 5).forEach(index -> {
            this.texture[index] = registrar.registerIcon(ModPrefs.MODID + ":blocks/ore/ore_" + EnumXycroniumColor.values()[index].getName());
        });

        this.texture[5] = registrar.registerIcon(ModPrefs.MODID + ":blocks/ore/ore_effect");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(int meta, int side) {
        return this.texture[5];
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
