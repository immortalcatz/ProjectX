/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ItemNBTUtils;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.projectx.client.render.item.RenderColorScanner;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":items/color_scanner/color_scanner_back");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":items/color_scanner/color_scanner_front");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":items/color_scanner/color_scanner_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderColorScanner.RENDER_TYPE;
    }

}
