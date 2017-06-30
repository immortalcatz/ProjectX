/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.event;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class CommonEventHandler {

    public static final CommonEventHandler INSTANCE = new CommonEventHandler();
    private static List<Block> BYPASSED_BLOCKS = Lists.newArrayList();

    @SubscribeEvent
    public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event){
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();

        if(player != null && world != null && this.isStackValid(stack) && pos != null){
            IBlockState state = world.getBlockState(pos);

            for(Block block : BYPASSED_BLOCKS){
                if(state != null && state.getBlock() == block){
                    if(player.isSneaking()){
                        event.setUseBlock(Event.Result.ALLOW);
                    }
                }
            }
        }
    }

    public void registerSneakBypass(Block block){
        BYPASSED_BLOCKS.add(block);
    }

    private boolean isStackValid(ItemStack stack){
        return stack != null && !stack.isEmpty();
    }

}
