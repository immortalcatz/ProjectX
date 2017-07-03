/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.network;

import keri.projectx.client.gui.GuiFaricator;
import keri.projectx.client.gui.GuiManual;
import keri.projectx.container.ContainerDummy;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.tile.TileEntityFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ProjectXGuiHandler implements IGuiHandler {

    public static final int GUIID_BASE = 0;
    public static final int GUIID_FABRICATOR = GUIID_BASE + 0;
    public static final int GUIID_MANUAL = GUIID_BASE + 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUIID_FABRICATOR){
            TileEntityFabricator tile = (TileEntityFabricator)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerFabricator(player.inventory, tile);
        }
        else if(ID == GUIID_MANUAL){
            return new ContainerDummy();
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUIID_FABRICATOR){
            TileEntityFabricator tile = (TileEntityFabricator)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiFaricator(player.inventory, tile);
        }
        else if(ID == GUIID_MANUAL){
            return new GuiManual();
        }

        return null;
    }

}
