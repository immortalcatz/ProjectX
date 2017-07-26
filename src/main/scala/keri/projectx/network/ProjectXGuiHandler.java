/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.network;

import keri.projectx.client.gui.GuiFaricator;
import keri.projectx.client.gui.GuiManual;
import keri.projectx.client.gui.GuiMultiTank;
import keri.projectx.container.ContainerDummy;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.container.ContainerMultitank;
import keri.projectx.multiblock.MultiTank;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.tile.TileMultiBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ProjectXGuiHandler implements IGuiHandler {

    public static final int GUIID_BASE = 0;
    public static final int GUIID_FABRICATOR = GUIID_BASE + 0;
    public static final int GUIID_MANUAL = GUIID_BASE + 1;
    public static final int MULTI_TANK = GUIID_BASE + 2;

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUIID_FABRICATOR:
                TileEntityFabricator tileFabricator = (TileEntityFabricator) world.getTileEntity(new BlockPos(x, y, z));
                return new ContainerFabricator(player.inventory, tileFabricator);
            case GUIID_MANUAL:
                return new ContainerDummy();
            case MULTI_TANK:
                TileMultiBlock tile = (TileMultiBlock) world.getTileEntity(new BlockPos(x, y, z));
                return new ContainerMultitank(player.inventory, (MultiTank) tile.getTank(0).get());
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUIID_FABRICATOR:
                TileEntityFabricator tileFabricator = (TileEntityFabricator) world.getTileEntity(new BlockPos(x, y, z));
                return new GuiFaricator(player.inventory, tileFabricator);
            case GUIID_MANUAL:
                return new GuiManual();
            case MULTI_TANK:
                TileMultiBlock tileMultiBlock = (TileMultiBlock) world.getTileEntity(new BlockPos(x, y, z));
                return new GuiMultiTank(player.inventory, (MultiTank) tileMultiBlock.getTank(0).get());

        }
        return null;
    }

}
