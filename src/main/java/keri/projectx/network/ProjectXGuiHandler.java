package keri.projectx.network;

import keri.projectx.client.gui.GuiFaricator;
import keri.projectx.client.gui.GuiHydrogenicSeperator;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.container.ContainerHydrogenicSeperator;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.tile.TileEntityHydrogenicSeperator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ProjectXGuiHandler implements IGuiHandler {

    public static final int GUIID_BASE = 0;
    public static final int GUIID_FABRICATOR = GUIID_BASE + 0;
    public static final int GUIID_HYDROGENIC_SEPERATOR = GUIID_BASE + 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUIID_FABRICATOR){
            TileEntityFabricator tile = (TileEntityFabricator)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerFabricator(player.inventory, tile);
        }
        else if(ID == GUIID_HYDROGENIC_SEPERATOR){
            TileEntityHydrogenicSeperator tile = (TileEntityHydrogenicSeperator)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerHydrogenicSeperator(player.inventory, tile);
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
        else if(ID == GUIID_HYDROGENIC_SEPERATOR){
            TileEntityHydrogenicSeperator tile = (TileEntityHydrogenicSeperator)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiHydrogenicSeperator(player.inventory, tile);
        }

        return null;
    }

}
