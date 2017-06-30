/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConnectedRenderContext {

    private static final double[] U = new double[]{-1D, 1D, 1D, -1D};
    private static final double[] V = new double[]{1D, 1D, -1D, -1D};
    private IBlockAccess world;
    private VertexBuffer buffer;
    private Block currentBlock;
    private int currentMeta;
    private boolean changeBounds = false;

    public void setBlockAccess(IBlockAccess world){
        this.world = world;
    }

    public void setVertexBuffer(VertexBuffer buffer){
        this.buffer = buffer;
    }

    private void renderSide(Block block, double x, double y, double z, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, double rx, double ry, double rz) {
        byte[] i = new byte[4];
        boolean areSame = true;

        for(int j = 0; j < 4; j++){
            i[j] = getType(block, side, (int) x, (int) y, (int) z,
                    ax * (int) U[j], ay * (int) U[j], az * (int) U[j],
                    bx * (int) V[j], by * (int) V[j], bz * (int) V[j],
                    (int) (ox * 2.0D - 1.0D), (int) (oy * 2D - 1D), (int) (oz * 2D - 1D));

            if((areSame) && (j > 0) && (i[j] != i[0])){
                areSame = false;
            }
        }
        if(areSame){
            icon.setType(i[0]);
            for (int j = 0; j < 4; j++) {
                double cx = x + rx + ox + U[j] * ax * 0.5D + V[j] * bx * 0.5D;
                double cy = y + ry + oy + U[j] * ay * 0.5D + V[j] * by * 0.5D;
                double cz = z + rz + oz + U[j] * az * 0.5D + V[j] * bz * 0.5D;
                double offsetX = 0;
                double offsetY = 0;
                double offsetZ = 0;

                if(this.changeBounds) {
                    switch (side) {
                        case 0:
                            offsetY -= 0.0004D;
                            break;
                        case 1:
                            offsetY += 0.0004D;
                            break;
                        case 2:
                            offsetZ -= 0.0004D;
                            break;
                        case 3:
                            offsetZ += 0.0004D;
                            break;
                        case 4:
                            offsetX -= 0.0004D;
                            break;
                        case 5:
                            offsetX += 0.0004D;
                            break;
                    }
                }

                this.buffer.pos(cx + offsetX, cy + offsetY, cz + offsetZ)
                        .tex(icon.getInterpolatedU(16.0D - (8.0D + U[j] * 8.0D)), icon.getInterpolatedV(16.0D - (8.0D + V[j] * 8.0D)))
                        .endVertex();
            }

            icon.resetType();
            return;
        }

        for (int j = 0; j < 4; j++) {
            icon.setType(i[j]);
            double cx = x + rx + ox + ax * U[j] / 4D + bx * V[j] / 4D;
            double cy = y + ry + oy + ay * U[j] / 4D + by * V[j] / 4D;
            double cz = z + rz + oz + az * U[j] / 4D + bz * V[j] / 4D;
            double offsetX = 0;
            double offsetY = 0;
            double offsetZ = 0;

            if(this.changeBounds)
                switch (side) {
                    case 0:
                        offsetY -= 0.0004D;
                        break;
                    case 1:
                        offsetY += 0.0004D;
                        break;
                    case 2:
                        offsetZ -= 0.0004D;
                        break;
                    case 3:
                        offsetZ += 0.0004D;
                        break;
                    case 4:
                        offsetX -= 0.0004D;
                        break;
                    case 5:
                        offsetX += 0.0004D;
                        break;
                }

            for (int k = 0; k < 4; k++) {
                this.buffer.pos(cx + U[k] * ax * 0.25D + V[k] * bx * 0.25D + offsetX, cy + U[k] * ay * 0.25D + V[k] * by * 0.25D + offsetY, cz + U[k] * az * 0.25D + V[k] * bz * 0.25D + offsetZ)
                        .tex(icon.getInterpolatedU(16.0D - (8.0D + U[j] * 4.0D + U[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + V[j] * 4.0D + V[k] * 4.0D)))
                        .endVertex();
            }

            icon.resetType();
        }
    }

    private byte getType(Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz) {
        int sidea = this.getSideFromDir(new BlockPos(ax, ay, az));
        int sideb = this.getSideFromDir(new BlockPos(bx, by, bz));
        boolean a = (this.matchBlock(new BlockPos(x + ax, y + ay, z + az))) &&
                (!this.matchBlock(new BlockPos(x + cx, y + cy, z + cz))) &&
                (!this.matchBlock(new BlockPos(x + ax + cx, y + ay + cy, z + az + cz)));
        boolean b = (this.matchBlock(new BlockPos(x + bx, y + by, z + bz))) &&
                (!this.matchBlock(new BlockPos(x + cx, y + cy, z + cz)) &&
                (!this.matchBlock(new BlockPos(x + bx + cx, y + by + cy, z + bz + cz))));

        if(a){
            if(b){
                if(this.matchBlock(new BlockPos(x + ax + bx, y + ay + by, z + az + bz))){
                    if((this.matchBlock(new BlockPos(x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz))) ||
                            (this.matchBlock(new BlockPos(x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz))) ||
                            (this.matchBlock(new BlockPos(x + bx + cx, y + by + cy, z + bz + cz))) ||
                            (this.matchBlock(new BlockPos(x + ax + cx, y + ay + cy, z + az + cz)))) {
                        return 4;
                    }

                    return 3;
                }

                return 4;
            }

            return 2;
        }

        if(b){
            return 1;
        }

        return 0;
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
