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
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXycroniumWater extends BlockAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXycroniumWater() {
        super("xycronium_water", Material.IRON);
        this.setHardness(1.4F);
        this.setTickRandomly(true);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        for(EnumFacing side : EnumFacing.VALUES){
            BlockPos sidePos = pos.offset(side);
            IBlockState sideState = world.getBlockState(sidePos);

            if(sideState.getBlock() == Blocks.FLOWING_LAVA){
                if(sideState.getValue(BlockDynamicLiquid.LEVEL) == 0){
                    world.setBlockState(sidePos, Blocks.OBSIDIAN.getDefaultState(), 3);
                }
                else{
                    world.setBlockState(sidePos, Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        IBlockState sideState = world.getBlockState(fromPos);

        if(sideState.getBlock() == Blocks.FLOWING_LAVA){
            if(sideState.getValue(BlockDynamicLiquid.LEVEL) == 0){
                world.setBlockState(fromPos, Blocks.OBSIDIAN.getDefaultState(), 3);
            }
            else{
                world.setBlockState(fromPos, Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }
    }

    //TODO: needs ASM for BlockFarmland or some kind of event handler to prevent transformation into dirt/dry farmland

    /**
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        for(int x = -3; x < 3; x++){
            for(int z = -3; z < 3; z++){
                BlockPos offsetPos = pos.add(x, 0, z);
                IBlockState offsetState = world.getBlockState(offsetPos);

                if(offsetState == Blocks.FARMLAND.getDefaultState()){
                    IBlockState newState = offsetState.withProperty(BlockFarmland.MOISTURE, 7);
                    world.setBlockState(offsetPos, newState, 3);
                }
            }
        }
    }
     */

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[2];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_bottom");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xycronium_water_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(side){
            case 0: return this.texture[0];
            case 1: return this.texture[0];
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
        return EnumXycroniumColor.BLUE.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.BLUE.getColor().rgba();
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
