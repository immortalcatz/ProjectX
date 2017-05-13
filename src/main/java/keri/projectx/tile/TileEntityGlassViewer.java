package keri.projectx.tile;

import keri.projectx.multiblock.IMultiblock;
import keri.projectx.multiblock.MultiblockDummy;

public class TileEntityGlassViewer extends TileEntityMultiblock {

    @Override
    public IMultiblock getMultiblock() {
        return new MultiblockDummy();
    }

}
