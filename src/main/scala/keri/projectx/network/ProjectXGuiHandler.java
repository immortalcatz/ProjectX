/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.network;

import keri.projectx.client.gui.item.GuiManual;
import keri.projectx.client.gui.machine.GuiEngineeringTable;
import keri.projectx.client.gui.machine.GuiFaricator;
import keri.projectx.client.gui.machine.GuiMultiTank;
import keri.projectx.container.ContainerDummy;
import keri.projectx.container.ContainerEngineeringTable;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.container.ContainerMultitank;
import keri.projectx.multiblock.MultiTank;
import keri.projectx.tile.TileEntityEngineeringTable;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.tile.TileEntityMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ProjectXGuiHandler implements IGuiHandler {

    public static final int GUIID_BASE = 0;
    public static final int GUIID_FABRICATOR = GUIID_BASE + 0;
    public static final int GUIID_MANUAL = GUIID_BASE + 1;
    public static final int GUIID_MULTI_TANK = GUIID_BASE + 2;
    public static final int GUIID_ENGINEERING_TABLE = GUIID_BASE + 3;

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUIID_FABRICATOR:
                TileEntityFabricator tileFabricator = (TileEntityFabricator) world.getTileEntity(new BlockPos(x, y, z));
                return new ContainerFabricator(player.inventory, tileFabricator);
            case GUIID_MANUAL:
                return new ContainerDummy();
            case GUIID_MULTI_TANK:
                TileEntityMultiblock tileMultiTank = (TileEntityMultiblock) world.getTileEntity(new BlockPos(x, y, z));
                if (tileMultiTank.getTank(0).isDefined())
                    return new ContainerMultitank(player.inventory, (MultiTank) tileMultiTank.getTank(0).get());
                break;
            case GUIID_ENGINEERING_TABLE:
                TileEntityEngineeringTable tileEngineeringTable = (TileEntityEngineeringTable)world.getTileEntity(new BlockPos(x, y, z));
                return new ContainerEngineeringTable(player.inventory, tileEngineeringTable);
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
            case GUIID_MULTI_TANK:
                TileEntityMultiblock tileMultiBlock = (TileEntityMultiblock) world.getTileEntity(new BlockPos(x, y, z));
                if (tileMultiBlock.getTank(0).isDefined())
                    return new GuiMultiTank(player.inventory, (MultiTank) tileMultiBlock.getTank(0).get());
                break;
            case GUIID_ENGINEERING_TABLE:
                TileEntityEngineeringTable tileEngineeringTable = (TileEntityEngineeringTable)world.getTileEntity(new BlockPos(x, y, z));
                return new GuiEngineeringTable(player.inventory, tileEngineeringTable);
        }
        return null;
    }

}
