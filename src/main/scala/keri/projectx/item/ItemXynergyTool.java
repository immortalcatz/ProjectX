/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import codechicken.lib.util.ItemNBTUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.ProjectX;
import keri.projectx.api.block.IDiagnoseableBlock;
import keri.projectx.api.block.ILinkableBlock;
import keri.projectx.api.block.ISwappableBlock;
import keri.projectx.api.block.IWrenchableBlock;
import keri.projectx.client.render.item.RenderXynergyTool;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXynergyTool extends ItemProjectX {

    public static final int MODE_WRENCH = 0;
    public static final int MODE_LINK = 1;
    public static final int MODE_DIAGNOSTIC = 2;
    public static final int MODE_SWAP = 3;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public ItemXynergyTool() {
        super("xynergy_tool");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey("mode")){
            int currentMode = heldItem.getTagCompound().getInteger("mode");

            if(currentMode == MODE_WRENCH){
                return this.wrench(world, pos, player, facing, hand);
            }
            else if(currentMode == MODE_LINK){
                return this.link(world, pos, player, facing, hand);
            }
            else if(currentMode == MODE_DIAGNOSTIC){
                return this.diagnose(world, pos, player, facing, hand);
            }
            else if(currentMode == MODE_SWAP){
                return this.swap(world, pos, player, facing, hand);
            }
        }
        else{
            return this.wrench(world, pos, player, facing, hand);
        }

        return EnumActionResult.PASS;
    }

    private EnumActionResult wrench(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand){
        IBlockState state = world.getBlockState(pos);

        if(state != null && state.getBlock() instanceof IWrenchableBlock){
            IWrenchableBlock block = (IWrenchableBlock)state.getBlock();

            if(block.canWrench(world, pos, player, side, hand)){
                block.onWrenchUsed(world, pos, player, side, hand);
                return EnumActionResult.SUCCESS;
            }
            else{
                return EnumActionResult.PASS;
            }
        }
        else{
            return EnumActionResult.PASS;
        }
    }

    private EnumActionResult link(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand){
        IBlockState state = world.getBlockState(pos);

        if(state != null && state.getBlock() instanceof ILinkableBlock){
            ILinkableBlock block = (ILinkableBlock)state.getBlock();

            if(block.canLink(world, pos, player, side, hand)){
                block.onLinked(world, pos, player, side, hand);
                return EnumActionResult.SUCCESS;
            }
            else{
                return EnumActionResult.PASS;
            }
        }
        else{
            return EnumActionResult.PASS;
        }
    }

    private EnumActionResult diagnose(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand){
        IBlockState state = world.getBlockState(pos);

        if(state != null && state.getBlock() instanceof IDiagnoseableBlock){
            IDiagnoseableBlock block = (IDiagnoseableBlock)state.getBlock();

            if(block.canDiagnose(world, pos, player, side, hand)){
                block.onDiagnosed(world, pos, player, side, hand);
                List<String> tooltip = Lists.newArrayList();
                block.addDiagnosticInformation(world, pos, player, tooltip);
                ProjectX.PROXY.resetTooltip(ModPrefs.TOOLTIP_DIAGNOSTIC, tooltip);
                return EnumActionResult.SUCCESS;
            }
            else{
                return EnumActionResult.PASS;
            }
        }
        else{
            return EnumActionResult.PASS;
        }
    }

    private EnumActionResult swap(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand){
        IBlockState state = world.getBlockState(pos);

        if(state != null && state.getBlock() instanceof ISwappableBlock){
            ISwappableBlock block = (ISwappableBlock)state.getBlock();

            if(block.canSwap(world, pos, player, side, hand)){
                block.onSwapped(world, pos, player, side, hand);
                return EnumActionResult.SUCCESS;
            }
            else{
                return EnumActionResult.PASS;
            }
        }
        else{
            return EnumActionResult.PASS;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(player.isSneaking()){
            ItemNBTUtils.validateTagExists(heldItem);

            if(heldItem.getTagCompound().hasKey("mode")){
                int currentMode = heldItem.getTagCompound().getInteger("mode");

                if(currentMode == MODE_WRENCH){
                    heldItem.getTagCompound().setInteger("mode", MODE_LINK);
                }
                else if(currentMode == MODE_LINK){
                    heldItem.getTagCompound().setInteger("mode", MODE_DIAGNOSTIC);
                }
                else if(currentMode == MODE_DIAGNOSTIC){
                    heldItem.getTagCompound().setInteger("mode", MODE_SWAP);
                }
                else if(currentMode == MODE_SWAP){
                    heldItem.getTagCompound().setInteger("mode", MODE_WRENCH);
                }
            }
            else{
                heldItem.getTagCompound().setInteger("mode", MODE_WRENCH);
            }

            ProjectX.PROXY.resetTooltip(ModPrefs.TOOLTIP_XYNERGY_TOOL);
            return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
        }

        return ActionResult.newResult(EnumActionResult.PASS, heldItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[2];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":items/xynergy_tool_side");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":items/xynergy_tool_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderXynergyTool.RENDER_TYPE;
    }

}
