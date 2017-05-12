package keri.projectx.tile;

import keri.projectx.multiblock.IMultiblock;
import keri.projectx.multiblock.MultiblockDummy;

public class TileEntityEngineeringBricks extends TileEntityMultiblock {

    @Override
    public IMultiblock getMultiblock() {
        return new MultiblockDummy();
    }

}
