package keri.projectx.block;

import codechicken.lib.colour.ColourRGBA;
import com.google.common.collect.Lists;
import keri.ninetaillib.block.IMetaBlock;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.init.ProjectXContent;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.property.ProjectXProperties;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockXycroniumOre extends BlockSimpleGlow implements IMetaBlock {

    public BlockXycroniumOre() {
        super("xycronium_ore", Material.ROCK);
        this.setHardness(1.6F);
        this.setHarvestLevel("pickaxe", 2);
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
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(ProjectXContent.xycroniumCrystal, 4 + fortune, this.getMetaFromState(state)));
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[6];

        for(int i = 0; i < EnumXycroniumColor.values().length; i++){
            this.texture[i] = registrar.registerIcon(ModPrefs.MODID + ":blocks/ore/xycronium_ore_" + EnumXycroniumColor.values()[i].getName());
        }

        this.texture[5] = registrar.registerIcon(ModPrefs.MODID + ":blocks/ore/xycronium_ore_effect");
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
