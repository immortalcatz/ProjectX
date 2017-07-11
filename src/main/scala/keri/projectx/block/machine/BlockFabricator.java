/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine;

import codechicken.lib.util.ItemUtils;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import keri.projectx.block.BlockAnimationHandler;
import keri.projectx.network.ProjectXGuiHandler;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockFabricator extends BlockAnimationHandler<TileEntityFabricator> {

    //TODO: move over to the xynergy API once done

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockFabricator() {
        super("fabricator", Material.IRON);
        this.setHardness(1.4F);
    }

    @Nullable
    @Override
    public TileEntityFabricator createNewTileEntity(World world, int meta) {
        return new TileEntityFabricator();
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFabricator.class, ModPrefs.MODID + ".tileentity.machine");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.GUIID_FABRICATOR, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntityFabricator tile = (TileEntityFabricator)world.getTileEntity(pos);

        if(tile != null){
            for(int i = 0; i < 9; i++){
                ItemStack stack = tile.getStackInSlot(i + 10).copy();

                if(stack != null && !stack.isEmpty()){
                    if(!world.isRemote){
                        ItemUtils.dropItem(world, pos, stack);
                    }
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_side");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/fabricator_top");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/machine_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(side){
            case 0: return this.texture[2];
            case 1: return this.texture[1];
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
