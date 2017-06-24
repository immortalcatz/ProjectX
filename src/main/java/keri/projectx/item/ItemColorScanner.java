/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ItemNBTUtils;
import keri.ninetaillib.lib.util.IShiftDescription;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemColorScanner extends ItemProjectX implements IShiftDescription {

    public ItemColorScanner() {
        super("color_scanner");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(player.isSneaking()){
            if(heldItem.getTagCompound() != null){
                heldItem.getTagCompound().setInteger("color", new ColourRGBA(255, 255, 255, 255).rgba());
            }
            else{
                ItemNBTUtils.validateTagExists(heldItem);
                heldItem.getTagCompound().setInteger("color", new ColourRGBA(255, 255, 255, 255).rgba());
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
        }

        return ActionResult.newResult(EnumActionResult.PASS, heldItem);
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

}
