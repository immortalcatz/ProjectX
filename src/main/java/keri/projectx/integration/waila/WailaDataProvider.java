/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration.waila;

import codechicken.lib.util.ItemNBTUtils;
import keri.projectx.init.ProjectXContent;
import keri.projectx.tile.TileEntityXycroniumLight;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class WailaDataProvider implements IWailaDataProvider {

    @Nonnull
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof TileEntityXycroniumLight){
            TileEntityXycroniumLight tile = (TileEntityXycroniumLight)accessor.getTileEntity();
            ItemStack stack = new ItemStack(ProjectXContent.xycroniumLight, 1, 0);
            ItemNBTUtils.validateTagExists(stack);
            stack.getTagCompound().setInteger("color", tile.getColor().rgba());
            return stack;
        }

        return null;
    }

    @Nonnull
    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof TileEntityXycroniumLight){
            TileEntityXycroniumLight tile = (TileEntityXycroniumLight)accessor.getTileEntity();
            currenttip.add(TextFormatting.RED + "R: " + (tile.getColor().r & 0xFF));
            currenttip.add(TextFormatting.GREEN + "G: " + (tile.getColor().g & 0xFF));
            currenttip.add(TextFormatting.BLUE + "B: " + (tile.getColor().b & 0xFF));
        }

        return currenttip;
    }

    @Nonnull
    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, BlockPos pos) {
        return null;
    }

}
