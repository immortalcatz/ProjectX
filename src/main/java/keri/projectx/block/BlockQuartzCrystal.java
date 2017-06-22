/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.client.render.IAnimationHandler;
import keri.projectx.client.render.RenderQuartzCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuartzCrystal extends BlockProjectX implements IAnimationHandler {

    //TODO: Implement crystal quantity and rendering!

    public BlockQuartzCrystal() {
        super("quartz_crystal", Material.GLASS);
        this.setHardness(0.8F);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollidedWithBlock(world, pos, state, entity);
        entity.attackEntityFrom(new DamageSource("quartz_crystal"), 2F);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return this.canExist(world, pos);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        EnumFacing fside = side;
        EnumFacing iside = fside.getOpposite();
        return world.isSideSolid(pos.offset(iside), fside);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if(!this.canPlaceOnSide(world, pos, EnumFacing.getFront(BlockAccessUtils.getBlockMetadata(world, pos)))) {
            this.dropBlockAsItem((World)world, pos, BlockAccessUtils.getMetaState(this, 0), 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing fside = facing;

        if(this.canPlaceOnSide(world, pos, fside)) {
            return BlockAccessUtils.getMetaState(this, fside.getIndex());
        }
        else {
            EnumFacing[] validDirections = EnumFacing.VALUES;

            for (int i = 0; i < validDirections.length; ++i) {
                EnumFacing fside2 = validDirections[i];

                if (this.canPlaceOnSide(world, pos, fside2)) {
                    return BlockAccessUtils.getMetaState(this, fside2.getIndex());
                }
            }

            return BlockAccessUtils.getMetaState(this, 0);
        }
    }

    private boolean canExist(World world, BlockPos pos) {
        boolean rechecking = world.getBlockState(pos).getBlock() == this;

        if (!rechecking) {
            EnumFacing[] validDirections = EnumFacing.VALUES;

            for(int i = 0; i < validDirections.length; ++i) {
                EnumFacing fside = validDirections[i];

                if (this.canPlaceOnSide(world, pos, fside)) {
                    return true;
                }
            }

            return false;
        }
        else {
            int meta = BlockAccessUtils.getBlockMetadata(world, pos);
            return meta < 6 && this.canPlaceOnSide(world, pos, EnumFacing.getFront(meta));
        }
    }

    private boolean canPlaceOnSide(IBlockAccess world, BlockPos pos, EnumFacing fside) {
        EnumFacing iside = fside.getOpposite();
        return world.isSideSolid(pos.offset(fside), fside, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos){
        int quantity = 1;
        int meta = BlockAccessUtils.getBlockMetadata(state);
        float hMin = 0F;
        float hMax = 0.6F;
        float wMin = 0.3F;
        float wMax = 0.7F;
        wMin -= quantity == 1 ? 0F : 0.1F;
        wMax += quantity == 1 ? 0F : 0.1F;

        switch(EnumFacing.getFront(meta).getOpposite().getIndex()){
            case 0: return new AxisAlignedBB(wMin, 1F - hMax, wMin, wMax, 1F, wMax);
            case 1: return new AxisAlignedBB(wMin, hMin, wMin, wMax, hMax, wMax);
            case 2: return new AxisAlignedBB(wMin, wMin, 1F - hMax, wMax, wMax, 1F);
            case 3: return new AxisAlignedBB(wMin, wMin, 0F, wMax, wMax, hMax);
            case 4: return new AxisAlignedBB(1F - hMax, wMin, wMin, 1F, wMax, wMax);
            case 5: return new AxisAlignedBB(hMin, wMin, wMin, hMax, wMax, wMax);
            default: return new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
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
        return new ColourRGBA(0, 200, 200, 255).rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return new ColourRGBA(0, 200, 200, 255).rgba();
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

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderQuartzCrystal.RENDER_TYPE;
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

}
