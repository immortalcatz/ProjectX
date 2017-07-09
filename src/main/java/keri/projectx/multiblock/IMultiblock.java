/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMultiblock {

    boolean checkMultiblock(World world, BlockPos pos, EnumFacing side, EntityPlayer player);

    void onFormed(World world, BlockPos pos, EnumFacing side, EntityPlayer player);

    void onDestroyed(World world, BlockPos pos, EnumFacing side, EntityPlayer player);

    void readFromNBT(NBTTagCompound tag);

    NBTTagCompound writeToNBT(NBTTagCompound tag);

    @SideOnly(Side.CLIENT)
    void renderFormed(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer);

}
