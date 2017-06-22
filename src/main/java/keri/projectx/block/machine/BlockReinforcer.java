/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine;

import codechicken.lib.util.ItemNBTUtils;
import codechicken.lib.util.ItemUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.block.BlockAnimationHandler;
import keri.projectx.tile.TileEntityReinforcer;
import keri.projectx.util.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockReinforcer extends BlockAnimationHandler<TileEntityReinforcer> {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockReinforcer() {
        super("reinforcer", Material.IRON);
        this.setHardness(1.4F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityReinforcer.class, "tile." + ModPrefs.MODID + ".reinforcer");
    }

    @Nullable
    @Override
    public TileEntityReinforcer createNewTileEntity(World world, int meta) {
        return new TileEntityReinforcer();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            player.openGui(ProjectX.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int orientation = MathHelper.floor(placer.rotationYaw * 4D / 360D + 0.5D) & 3;
        BlockAccessUtils.setBlockMetadata(world, pos, orientation, 3);
        TileEntityReinforcer tile = (TileEntityReinforcer)world.getTileEntity(pos);

        if(tile != null && stack.getTagCompound() != null){
            tile.receiveEnergy(null, stack.getTagCompound().getInteger("energy_stored"), false);
            tile.markDirty();
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityReinforcer tile = (TileEntityReinforcer)world.getTileEntity(pos);

        if(tile != null){
            ItemStack stack = new ItemStack(this, 1, 0);
            ItemNBTUtils.validateTagExists(stack);
            stack.getTagCompound().setInteger("energy_stored", tile.getEnergyStored(null));

            if(!world.isRemote){
                ItemUtils.dropItem(world, pos, stack);
            }
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[4];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/reinforcer_top");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/reinforcer_bottom");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/reinforcer_side");
        this.texture[3] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/reinforcer_front");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(side){
            case 0: return this.texture[0];
            case 1: return this.texture[0];
            case 2: return this.texture[0];
            case 3: return this.texture[0];
            case 4: return this.texture[0];
            case 5: return this.texture[0];
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
