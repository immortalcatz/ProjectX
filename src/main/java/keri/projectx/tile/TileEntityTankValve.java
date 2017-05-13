package keri.projectx.tile;

import keri.projectx.multiblock.IMultiblock;
import keri.projectx.multiblock.MultiblockTank;

public class TileEntityTankValve extends TileEntityMultiblock {

    @Override
    public IMultiblock getMultiblock() {
        return new MultiblockTank();
    }

}
