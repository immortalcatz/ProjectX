/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block.machine;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import keri.projectx.block.BlockAnimationHandler;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;
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

public class BlockFireBasin extends BlockAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockFireBasin() {
        super("fire_basin", Material.IRON);
        this.setHardness(1.4F);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        BlockPos offsetPos = pos.offset(EnumFacing.UP);
        IBlockState offsetState = world.getBlockState(offsetPos);

        if(world.isBlockPowered(pos)){
            if(offsetState != Blocks.FIRE.getDefaultState()){
                world.setBlockState(offsetPos, Blocks.FIRE.getDefaultState());
                BlockAccessUtils.setBlockMetadata(world, pos, 1, 3);
            }
        }
        else{
            if(offsetState == Blocks.FIRE.getDefaultState()){
                world.setBlockToAir(offsetPos);
                BlockAccessUtils.setBlockMetadata(world, pos, 0, 3);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        BlockPos offsetPos = pos.offset(EnumFacing.UP);
        IBlockState offsetState = world.getBlockState(offsetPos);

        if(world.isBlockPowered(pos)){
            if(offsetState != Blocks.FIRE.getDefaultState()){
                world.setBlockState(offsetPos, Blocks.FIRE.getDefaultState());
                BlockAccessUtils.setBlockMetadata(world, pos, 1, 3);
            }
        }
        else{
            if(offsetState == Blocks.FIRE.getDefaultState()){
                world.setBlockToAir(offsetPos);
                BlockAccessUtils.setBlockMetadata(world, pos, 0, 3);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[4];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_bottom");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/fire_basin_side");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/fire_basin_top_off");
        this.texture[3] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/fire_basin_top_on");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, EnumFacing side) {
        switch (side.getIndex()) {
            case 0: return this.texture[0];
            case 1: return meta == 0 ? this.texture[2] : this.texture[3];
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
        return EnumXycroniumColor.RED.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.RED.getColor().rgba();
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
