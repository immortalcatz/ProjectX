/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.util;

import codechicken.lib.util.ClientUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by Adam on 7/11/2017.
 */
public class PlayerStareTracker {

    BlockPos staringPos = BlockPos.ORIGIN;
    int lastTime = 0;

    public void update(RayTraceResult result) {
        int time = (int) ClientUtils.getRenderTime();
        //Make sure we are staring at a block
        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            if (!result.getBlockPos().equals(staringPos)) {
                lastTime = time;
                staringPos = result.getBlockPos();
            }
        } else {
            staringPos = BlockPos.ORIGIN;
        }
    }

    public int getStareTime() {
        return (int) ClientUtils.getRenderTime() - lastTime;
    }

}
