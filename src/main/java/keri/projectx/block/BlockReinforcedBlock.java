/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import codechicken.lib.util.ItemNBTUtils;
import codechicken.lib.util.ItemUtils;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.client.render.RenderReinforcedBlock;
import keri.projectx.tile.TileEntityReinforcedBlock;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockReinforcedBlock extends BlockProjectX<TileEntityReinforcedBlock> {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public BlockReinforcedBlock() {
        super("reinforced_block", Material.ROCK);
        this.setHardness(1.4F);
    }

    @Override
    public void registerTileEntities(){
        GameRegistry.registerTileEntity(TileEntityReinforcedBlock.class, "tile." + ModPrefs.MODID + ".reinforced_block");
    }

    @Nullable
    @Override
    public TileEntityReinforcedBlock createNewTileEntity(World world, int meta) {
        return new TileEntityReinforcedBlock();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityReinforcedBlock tile = (TileEntityReinforcedBlock)world.getTileEntity(pos);

        if(tile != null && stack.getTagCompound() != null){
            ItemStack blockStack = new ItemStack(stack.getTagCompound().getCompoundTag("block"));
            tile.setBlock(blockStack);
            tile.markDirty();
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntityReinforcedBlock tile = (TileEntityReinforcedBlock)world.getTileEntity(pos);

        if(tile != null){
            if(!player.capabilities.isCreativeMode){
                ItemStack stack = new ItemStack(this, 1, 0);
                ItemNBTUtils.validateTagExists(stack);
                NBTTagCompound blockTag = tile.getBlock().writeToNBT(new NBTTagCompound());
                stack.getTagCompound().setTag("block", blockTag);

                if(!world.isRemote){
                    ItemUtils.dropItem(world, pos, stack);
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = register.registerIcon(ModPrefs.MODID + ":blocks/reinforced_block");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderReinforcedBlock.RENDER_TYPE;
    }

}
