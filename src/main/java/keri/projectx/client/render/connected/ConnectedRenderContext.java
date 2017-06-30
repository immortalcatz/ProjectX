/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConnectedRenderContext {

    private static final double[] U = new double[]{-1D, 1D, 1D, -1D};
    private static final double[] V = new double[]{1D, 1D, -1D, -1D};
    private IBlockAccess world;
    private Block currentBlock;
    private int currentMeta;

    public void setBlockAccess(IBlockAccess world){
        this.world = world;
    }

    private boolean matchBlock(BlockPos pos) {
        IBlockState state = this.world.getBlockState(pos);

        if(state.getBlock() == this.currentBlock){
            return state.getBlock().getMetaFromState(state) == this.currentMeta;
        }

        return false;
    }

    private int getSideFromDir(BlockPos pos) {
        if (pos.getY() < 0) {
            return 0;
        }
        if (pos.getY() > 0) {
            return 1;
        }
        if (pos.getZ() < 0) {
            return 2;
        }
        if (pos.getZ() > 0) {
            return 3;
        }
        if (pos.getX() < 0) {
            return 4;
        }
        if (pos.getX() > 0) {
            return 5;
        }

        return 0;
    }

}
