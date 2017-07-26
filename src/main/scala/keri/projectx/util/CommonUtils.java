/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.util;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class CommonUtils {

    public static Colour modifyColor(Colour color, EntityPlayer player, EnumHand hand, byte modify){
        int r = color.r & 0xFF;
        int g = color.g & 0xFF;
        int b = color.b & 0xFF;
        int a = color.a & 0xFF;
        ItemStack heldItem = player.getHeldItem(hand);

        switch(modify){
            case 0:
                if(player.isSneaking()){
                    if(r > 0){
                        r--;
                    }
                }
                else{
                    if(r < 255){
                        r++;
                    }
                }
                break;
            case 1:
                if(player.isSneaking()){
                    if(g > 0){
                        g--;
                    }
                }
                else{
                    if(g < 255){
                        g++;
                    }
                }
                break;
            case 2:
                if(player.isSneaking()){
                    if(b > 0){
                        b--;
                    }
                }
                else{
                    if(b < 255){
                        b++;
                    }
                }
                break;
            case 3:
                if(player.isSneaking()){
                    if(a > 0){
                        a--;
                    }
                }
                else{
                    if(a < 255){
                        a++;
                    }
                }
                break;
        }

        return new ColourRGBA(r, g, b, a);
    }

}
