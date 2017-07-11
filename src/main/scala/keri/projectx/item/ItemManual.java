/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.projectx.ProjectX;
import keri.projectx.network.ProjectXGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemManual extends ItemProjectX {

    public ItemManual() {
        super("manual");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(world.isRemote){
            player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.GUIID_MANUAL, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
    }

}
