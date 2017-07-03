/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.projectx.api.block.IWrenchable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemXynergyTool extends ItemProjectX {

    public ItemXynergyTool() {
        super("xynergy_tool");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);

        if(state != null && state.getBlock() instanceof IWrenchable){
            IWrenchable block = (IWrenchable)state.getBlock();

            if(block.canWrench(world, pos, player, facing)){
                block.onWrenchUsed(world, pos, player, facing);
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

}
