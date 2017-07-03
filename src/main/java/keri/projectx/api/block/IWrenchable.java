/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {

    boolean canWrench(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

    void onWrenchUsed(World world, BlockPos pos, EntityPlayer player, EnumFacing side);

}
