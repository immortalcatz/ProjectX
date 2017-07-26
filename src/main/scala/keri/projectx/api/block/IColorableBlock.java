/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.api.block;

import codechicken.lib.colour.Colour;
import codechicken.lib.vec.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IColorableBlock {

    Colour getStoredColor(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand, Vector3 hit);

    void setStoredColor(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand, Vector3 hit, Colour color);

}
