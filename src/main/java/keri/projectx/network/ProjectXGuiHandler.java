package keri.projectx.network;

import keri.projectx.client.gui.GuiEngineeringTable;
import keri.projectx.client.gui.GuiTank;
import keri.projectx.container.ContainerEngineeringTable;
import keri.projectx.container.ContainerTank;
import keri.projectx.tile.TileEntityEngineeringTable;
import keri.projectx.tile.TileEntityTankValve;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ProjectXGuiHandler implements IGuiHandler {

    public static final int GUIID_BASE = 0;
    public static final int GUIID_ENGINEERING_TABLE = GUIID_BASE + 0;
    public static final int GUIID_TANK = GUIID_BASE + 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUIID_ENGINEERING_TABLE){
            TileEntityEngineeringTable tile = (TileEntityEngineeringTable)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerEngineeringTable(player.inventory, tile);
        }
        else if(ID == GUIID_TANK){
            TileEntityTankValve tile = (TileEntityTankValve)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerTank(player.inventory, tile);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUIID_ENGINEERING_TABLE){
            TileEntityEngineeringTable tile = (TileEntityEngineeringTable)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiEngineeringTable(player.inventory, tile);
        }
        else if(ID == GUIID_TANK){
            TileEntityTankValve tile = (TileEntityTankValve)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiTank(player.inventory, tile);
        }

        return null;
    }

}
