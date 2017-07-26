/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.event;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class XynergyRenderingHandler {

    public static final XynergyRenderingHandler INSTANCE = new XynergyRenderingHandler();
    private static Map<BlockPos, List<BlockPos>> DEVICES = Maps.newHashMap();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event){
        //do stuff here :D
    }

    public void addDevice(BlockPos master, BlockPos slave){
        if(DEVICES.containsKey(master)){
            DEVICES.get(master).add(slave);
        }
        else{
            DEVICES.put(master, Lists.newArrayList(slave));
        }
    }

    public void removeDevice(BlockPos master, BlockPos slave){
        DEVICES.get(master).remove(slave);
    }

}
