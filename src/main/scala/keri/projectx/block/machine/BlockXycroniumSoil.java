/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import keri.projectx.block.BlockAnimationHandler;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockXycroniumSoil extends BlockAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXycroniumSoil() {
        super("xycronium_soil", Material.IRON);
        this.setHardness(1.4F);
        this.setTickRandomly(true);
    }

    @Override
    public int tickRate(World world) {
        return 100;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return true;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if(rand.nextInt(4) == 0){
            BlockPos offsetPos = pos.offset(EnumFacing.UP);
            IBlockState offsetState = world.getBlockState(offsetPos);

            if(offsetState.getBlock() instanceof IGrowable){
                ((IGrowable)offsetState.getBlock()).grow(world, rand, offsetPos, offsetState);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_bottom");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xycronium_soil_side");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xycronium_soil_top");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(side){
            case 0: return this.texture[0];
            case 1: return this.texture[2];
            case 2: return this.texture[1];
            case 3: return this.texture[1];
            case 4: return this.texture[1];
            case 5: return this.texture[1];
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(ItemStack stack, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(IBlockAccess world, BlockPos pos, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(ItemStack stack, int side) {
        return EnumXycroniumColor.GREEN.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.GREEN.getColor().rgba();
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
