/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine;

import codechicken.lib.util.ItemUtils;
import codechicken.lib.vec.Cuboid6;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.VectorUtils;
import keri.projectx.api.block.IWrenchable;
import keri.projectx.api.energy.EnumCoreType;
import keri.projectx.block.BlockProjectX;
import keri.projectx.client.render.RenderXynergyNode;
import keri.projectx.init.ProjectXContent;
import keri.projectx.tile.TileEntityXynergyNode;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockXynergyNode extends BlockProjectX implements IWrenchable {

    //TODO: implement orientation

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXynergyNode() {
        super("xynergy_node", Material.IRON);
        this.setHardness(1.2F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityXynergyNode.class, "tile." + ModPrefs.MODID + ".xynergy_node");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityXynergyNode();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            ItemStack heldItem = player.getHeldItem(hand);

            if(!tile.getHasCore()){
                if(heldItem != null && !heldItem.isEmpty()){
                    if(heldItem.getItem() == ProjectXContent.POWER_CORE){
                        tile.setXynergyClass(EnumCoreType.VALUES[heldItem.getMetadata()].getXynergyClass());
                        tile.setXynergyType(EnumCoreType.VALUES[heldItem.getMetadata()].getXynergyType());
                        tile.setHasCore(true);
                        tile.markDirty();
                        tile.sendUpdatePacket(pos);

                        if(!player.capabilities.isCreativeMode){
                            heldItem.setCount(heldItem.getCount() - 1);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            if(tile.getHasCore()){
                EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());

                if(!world.isRemote){
                    ItemUtils.dropItem(world, pos, stack);
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canWrench(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
        return true;
    }

    @Override
    public void onWrenchUsed(World world, BlockPos pos, EntityPlayer player, EnumFacing side) {
        if(player.isSneaking()){
            TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);
            List<ItemStack> drops = Lists.newArrayList();
            drops.add(new ItemStack(this, 1, 0));

            if(tile != null && tile.getHasCore()){
                EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());
                drops.add(stack);
            }

            if(!world.isRemote){
                drops.forEach(stack -> ItemUtils.dropItem(world, pos, stack));
            }

            world.setBlockToAir(pos);
        }
        else{
            TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

            if(tile != null && tile.getHasCore()){
                EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());

                if(!world.isRemote){
                    ItemUtils.dropItem(world, pos, stack);
                    tile.setHasCore(false);
                    tile.markDirty();
                    tile.sendUpdatePacket(pos);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            if(tile.getHasCore()){
                return VectorUtils.divide(new Cuboid6(2D, 0D, 2D, 14D, 14D, 14D), 16D).aabb();
            }
            else{
                return VectorUtils.divide(new Cuboid6(3D, 0D, 3D, 13D, 2D, 13D), 16D).aabb();
            }
        }

        return new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_top");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_bottom");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_side");
    }

    @Override
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderXynergyNode.RENDER_TYPE;
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
