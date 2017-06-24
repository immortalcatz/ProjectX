/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ItemNBTUtils;
import codechicken.lib.util.ItemUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.EnumDyeColor;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.projectx.ProjectX;
import keri.projectx.event.CommonEventHandler;
import keri.projectx.init.ProjectXContent;
import keri.projectx.tile.TileEntityXycroniumLight;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class BlockXycroniumLightInverted extends BlockAnimationHandler<TileEntityXycroniumLight> implements IShiftDescription {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public BlockXycroniumLightInverted() {
        super("xycronium_light_inverted", Material.GLASS);
        this.setHardness(1.2F);
        CommonEventHandler.INSTANCE.registerSneakBypass(this);
    }

    @Nullable
    @Override
    public TileEntityXycroniumLight createNewTileEntity(World world, int meta) {
        return new TileEntityXycroniumLight();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);
        ItemStack heldItem = player.getHeldItem(hand);

        if(!heldItem.isEmpty()){
            if(heldItem.getItem() == Items.DYE){
                if(tile != null){
                    tile.setColor(EnumDyeColor.VALUES[heldItem.getMetadata()].getColor());
                    tile.markDirty();

                    if(!player.capabilities.isCreativeMode){
                        heldItem.setCount(heldItem.getCount() - 1);
                    }

                    return true;
                }
            }
            else if(heldItem.getItem() == ProjectXContent.XYCRONIUM_CRYSTAL){
                if(tile != null){
                    int r = tile.getColor().r & 0xFF;
                    int g = tile.getColor().g & 0xFF;
                    int b = tile.getColor().b & 0xFF;

                    if(heldItem.getMetadata() == 0){
                        if(player.isSneaking()){
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(b > 4){
                                    b -= 5;
                                }
                            }
                            else{
                                if(b > 0){
                                    b--;
                                }
                            }
                        }
                        else{
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(b < 255){
                                    b += 5;
                                }
                            }
                            else{
                                if(b < 255){
                                    b++;
                                }
                            }
                        }
                    }
                    else if(heldItem.getMetadata() == 1){
                        if(player.isSneaking()){
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(g > 4){
                                    g -= 5;
                                }
                            }
                            else{
                                if(g > 0){
                                    g--;
                                }
                            }
                        }
                        else{
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(g < 255){
                                    g += 5;
                                }
                            }
                            else{
                                if(g < 255){
                                    g++;
                                }
                            }
                        }
                    }
                    else if(heldItem.getMetadata() == 2){
                        if(player.isSneaking()){
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(r > 4){
                                    r -= 5;
                                }
                            }
                            else{
                                if(r > 0){
                                    r--;
                                }
                            }
                        }
                        else{
                            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                                if(r < 255){
                                    r += 5;
                                }
                            }
                            else{
                                if(r < 255){
                                    r++;
                                }
                            }
                        }
                    }

                    tile.setColor(new ColourRGBA(r, g, b, 255));
                    tile.markDirty();
                }

                return true;
            }
            else if(heldItem.getItem() == ProjectXContent.COLOR_SCANNER){
                if(tile != null){
                    if(player.isSneaking()){
                        ItemNBTUtils.validateTagExists(heldItem);
                        heldItem.getTagCompound().setInteger("color", tile.getColor().rgba());
                    }
                    else{
                        if(heldItem.getTagCompound() != null){
                            tile.setColor(new ColourRGBA(heldItem.getTagCompound().getInteger("color")));
                            tile.markDirty();
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if(this.getMetaFromState(state) == 1){
            if(world.isBlockPowered(pos)){
                BlockAccessUtils.setBlockMetadata(world, pos, 0, 3);
            }
        }
        else{
            if(!world.isBlockPowered(pos)){
                BlockAccessUtils.setBlockMetadata(world, pos, 1, 3);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);

        if(tile != null && stack.getTagCompound() != null){
            tile.setColor(new ColourRGBA(stack.getTagCompound().getInteger("color")));
            tile.markDirty();
        }

        if(this.getMetaFromState(state) == 1){
            if(world.isBlockPowered(pos)){
                BlockAccessUtils.setBlockMetadata(world, pos, 0, 3);
            }
        }
        else{
            if(!world.isBlockPowered(pos)){
                BlockAccessUtils.setBlockMetadata(world, pos, 1, 3);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if(!player.capabilities.isCreativeMode){
            TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);

            if(tile != null){
                ItemStack stack = new ItemStack(this, 1, 0);
                ItemNBTUtils.validateTagExists(stack);
                stack.getTagCompound().setInteger("color", tile.getColor().rgba());

                if(!world.isRemote){
                    ItemUtils.dropItem(world, pos, stack);
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.getMetaFromState(state) == 1 ? 255 : 0;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);
        ItemStack stack = new ItemStack(this, 1, 0);
        ItemNBTUtils.validateTagExists(stack);

        if(tile != null){
            stack.getTagCompound().setInteger("color", tile.getColor().rgba());
        }
        else{
            stack.getTagCompound().setInteger("color", new ColourRGBA(255, 255, 255, 255).rgba());
        }

        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        for(EnumDyeColor color : EnumDyeColor.VALUES){
            ItemStack stack = new ItemStack(this, 1, 0);
            ItemNBTUtils.validateTagExists(stack);
            stack.getTagCompound().setInteger("color", color.getColor().rgba());
            list.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldAddDescription(ItemStack stack, EntityPlayer player) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addDescription(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        if(stack.getTagCompound() != null){
            Colour color = new ColourRGBA(stack.getTagCompound().getInteger("color"));
            tooltip.add(TextFormatting.RED + "R: " + (color.r & 0xFF));
            tooltip.add(TextFormatting.GREEN + "G: " + (color.g & 0xFF));
            tooltip.add(TextFormatting.BLUE + "B: " + (color.b & 0xFF));
        }
        else{
            tooltip.add(TextFormatting.RED + "R: 0");
            tooltip.add(TextFormatting.GREEN + "G: 0");
            tooltip.add(TextFormatting.BLUE + "B: 0");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        this.texture = register.registerIcon(ModPrefs.MODID + ":blocks/xycronium_light");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
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
        if(stack.getTagCompound() != null){
            return stack.getTagCompound().getInteger("color");
        }
        else{
            return new ColourRGBA(255, 255, 255, 255).rgba();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side){
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);

        if(tile != null){
            return tile.getColor().rgba();
        }
        else{
            return new ColourRGBA(255, 255, 255, 255).rgba();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side){
        return BlockAccessUtils.getBlockMetadata(world, pos) == 1 ? 0x00F000F0 : 0x00B0000F;
    }

}
