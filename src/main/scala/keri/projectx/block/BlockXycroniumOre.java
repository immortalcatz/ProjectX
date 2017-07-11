/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import keri.projectx.init.ProjectXContent;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockXycroniumOre extends BlockAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXycroniumOre() {
        super("xycronium_ore", Material.ROCK, EnumXycroniumColor.toStringArray());
        this.setHardness(1.4F);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 4 + fortune, this.getMetaFromState(state)));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[6];

        for(int i = 0; i < this.getSubNames().length; i++){
            this.texture[i] = register.registerIcon(ModPrefs.MODID + ":blocks/xycronium_ore/xycronium_ore_" + this.getSubNames()[i]);
        }

        this.texture[5] = register.registerIcon(ModPrefs.MODID + ":blocks/xycronium_ore/xycronium_ore_effect");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(ItemStack stack, int side) {
        boolean animatedOres = (Boolean)ProjectX.CONFIG.getProperty("animatedOres").getValue();
        return animatedOres ? ProjectX.PROXY.getAnimatedTexture() : this.texture[5];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(IBlockAccess world, BlockPos pos, int side) {
        boolean animatedOres = (Boolean)ProjectX.CONFIG.getProperty("animatedOres").getValue();
        return animatedOres ? ProjectX.PROXY.getAnimatedTexture() : this.texture[5];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(ItemStack stack, int side) {
        return EnumXycroniumColor.VALUES[stack.getMetadata()].getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.VALUES[BlockAccessUtils.getBlockMetadata(world, pos)].getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side) {
        return 0x00F000F0;
    }

}
