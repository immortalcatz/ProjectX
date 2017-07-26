/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.util;

import keri.projectx.init.ProjectXContent;
import keri.projectx.item.ItemXynergyTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class XynergyToolHelper {

    public static void prepareToStore(EntityPlayer player, EnumHand hand){
        ItemStack heldItem = player.getHeldItem(hand);

        if(!heldItem.isEmpty() && heldItem.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey("mode")){
                int currentMode = heldItem.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_SWAP){
                    if(!heldItem.getTagCompound().hasKey("contains_item") || !heldItem.getTagCompound().hasKey("stored_item")){
                        ItemStack stack = ItemStack.EMPTY;
                        heldItem.getTagCompound().setBoolean("contains_item", false);
                        heldItem.getTagCompound().setTag("stored_item", stack.writeToNBT(new NBTTagCompound()));
                    }
                }
            }
        }
    }

    public static void storeItem(EntityPlayer player, EnumHand hand, ItemStack stack){
        ItemStack heldItem = player.getHeldItem(hand);

        if(!heldItem.isEmpty() && heldItem.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey("mode")){
                int currentMode = heldItem.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_SWAP && heldItem.getTagCompound().hasKey("contains_item")){
                    boolean containsItem = heldItem.getTagCompound().getBoolean("contains_item");

                    if(!containsItem && stack != null){
                        heldItem.getTagCompound().setTag("stored_item", stack.writeToNBT(new NBTTagCompound()));
                        heldItem.getTagCompound().setBoolean("contains_item", true);
                    }
                }
            }
        }
    }

    public static ItemStack disposeStoredItem(EntityPlayer player, EnumHand hand){
        ItemStack heldItem = player.getHeldItem(hand);
        ItemStack stack = ItemStack.EMPTY;

        if(!heldItem.isEmpty() && heldItem.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey("mode")){
                int currentMode = heldItem.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_SWAP && heldItem.getTagCompound().hasKey("contains_item")){
                    boolean containsItem = heldItem.getTagCompound().getBoolean("contains_item");

                    if(containsItem && heldItem.getTagCompound().hasKey("stored_item")){
                        stack = new ItemStack(heldItem.getTagCompound().getCompoundTag("stored_item"));
                        heldItem.getTagCompound().setBoolean("contains_item", false);
                    }
                }
            }
        }

        return stack;
    }

    public static ItemStack getStoredItem(EntityPlayer player, EnumHand hand){
        ItemStack heldItem = player.getHeldItem(hand);
        ItemStack stack = ItemStack.EMPTY;

        if(!heldItem.isEmpty() && heldItem.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey("mode")){
                int currentMode = heldItem.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_SWAP && heldItem.getTagCompound().hasKey("contains_item")){
                    boolean containsItem = heldItem.getTagCompound().getBoolean("contains_item");

                    if(containsItem && heldItem.getTagCompound().hasKey("stored_item")){
                        stack = new ItemStack(heldItem.getTagCompound().getCompoundTag("stored_item"));
                    }
                }
            }
        }

        return stack;
    }

}
