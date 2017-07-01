/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.Vertex5;
import codechicken.lib.vec.uv.IconTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.init.ProjectXContent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderConnectedTexture implements IBlockRenderingHandler {

    public static final RenderConnectedTexture INSTANCE = new RenderConnectedTexture();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel DEFAULT_MODEL;
    //-------------------
    private static final double[] U = new double[]{-1D, 1D, 1D, -1D};
    private static final double[] V = new double[]{1D, 1D, -1D, -1D};
    private boolean changeBounds = false;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        DEFAULT_MODEL = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D));
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
        return this.renderConnectedTexture(world, pos, buffer);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        CCModel model = DEFAULT_MODEL.copy();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        model.render(renderState, new IconTransformation(texture));
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        CCModel model = DEFAULT_MODEL.copy();
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        model.render(renderState, new IconTransformation(iconProvider.getIcon(0, 0)));
        GlStateManager.popMatrix();
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

    public boolean renderConnectedTexture(IBlockAccess world, BlockPos pos, VertexBuffer buffer){
        Block block = world.getBlockState(pos).getBlock();
        IconConnectedTexture texture = (IconConnectedTexture)((IIconBlock)block).getIcon(0, 0);
        List<Vertex5> vertices = Lists.newArrayList();
        vertices.addAll(this.renderFaceXNeg(world, block, 0D, 0D, 0D, texture));
        vertices.addAll(this.renderFaceXPos(world, block, 0D, 0D, 0D, texture));
        vertices.addAll(this.renderFaceYNeg(world, block, 0D, 0D, 0D, texture));
        vertices.addAll(this.renderFaceYPos(world, block, 0D, 0D, 0D, texture));
        vertices.addAll(this.renderFaceZNeg(world, block, 0D, 0D, 0D, texture));
        vertices.addAll(this.renderFaceZPos(world, block, 0D, 0D, 0D, texture));
        CCModel model = CCModel.quadModel(vertices.size());

        for(int i = 0; i < vertices.size(); i++){
            model.getVertices()[i] = vertices.get(i);
        }

        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        model.computeNormals().apply(new Translation(Vector3.fromBlockPos(pos))).render(renderState);
        return true;
    }

    //---------------------------------------------------------------------

    private List<Vertex5> renderSide(IBlockAccess world, Block block, double x, double y, double z, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, double rx, double ry, double rz) {
        final List<Vertex5> vertices = Lists.newArrayList();
        final byte[] i = new byte[4];
        boolean areSame = true;

        for(int j = 0; j < 4; j++){
            i[j] = getType(world, block, side, (int) x, (int) y, (int) z, ax * (int) U[j], ay * (int) U[j], az * (int) U[j], bx * (int) V[j], by * (int) V[j], bz * (int) V[j], (int) (ox * 2.0D - 1.0D), (int) (oy * 2.0D - 1.0D), (int) (oz * 2.0D - 1.0D));
            if ((areSame) && (j > 0) && (i[j] != i[0])) {
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

                vertices.add(new Vertex5(cx + offsetX, cy + offsetY, cz + offsetZ, icon.getInterpolatedU(16.0D - (8.0D + U[j] * 8.0D)), icon.getInterpolatedV(16.0D - (8.0D + V[j] * 8.0D))));
            }

            icon.resetType();
            return vertices;
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
                vertices.add(new Vertex5(cx + U[k] * ax * 0.25D + V[k] * bx * 0.25D + offsetX, cy + U[k] * ay * 0.25D + V[k] * by * 0.25D + offsetY, cz + U[k] * az * 0.25D + V[k] * bz * 0.25D + offsetZ, icon.getInterpolatedU(16.0D - (8.0D + U[j] * 4.0D + U[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + V[j] * 4.0D + V[k] * 4.0D))));
            }

            icon.resetType();
        }

        return vertices;
    }

    public byte getType(IBlockAccess world, Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz) {
        int sidea = getSideFromDir(ax, ay, az);
        int sideb = getSideFromDir(bx, by, bz);
        boolean a = (matchBlock(world, side, x + ax, y + ay, z + az)) && (!matchBlock(world, sidea, x + cx, y + cy, z + cz)) && (!matchBlock(world, EnumFacing.getFront(sidea).getOpposite().getIndex(), x + ax + cx, y + ay + cy, z + az + cz));
        boolean b = (matchBlock(world, side, x + bx, y + by, z + bz)) && (!matchBlock(world, sideb, x + cx, y + cy, z + cz)) && (!matchBlock(world, EnumFacing.getFront(sideb).getOpposite().getIndex(), x + bx + cx, y + by + cy, z + bz + cz));

        if (a) {
            if (b) {
                if (matchBlock(world, side, x + ax + bx, y + ay + by, z + az + bz)) {
                    if ((matchBlock(world, EnumFacing.getFront(sidea).getOpposite().getIndex(), x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(world, EnumFacing.getFront(sideb).getOpposite().getIndex(), x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(world, sidea, x + bx + cx, y + by + cy, z + bz + cz)) || (matchBlock(world, sideb, x + ax + cx, y + ay + cy, z + az + cz))) {
                        return 4;
                    }
                    else{
                        return 3;
                    }
                }
                else{
                    return 4;
                }
            }
            else{
                return 2;
            }
        }
        else if (b) {
            return 1;
        }
        else{
            return 0;
        }
    }

    private boolean matchBlock(IBlockAccess world, int side, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        if(world.getBlockState(pos).getBlock() == ProjectXContent.GLASS_VIEWER){
            if(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) == 0){
                return true;
            }
        }

        return false;
    }

    private int getSideFromDir(int x, int y, int z) {
        if (y < 0) {
            return 0;
        }
        else if (y > 0) {
            return 1;
        }
        else if (z < 0) {
            return 2;
        }
        else if (z > 0) {
            return 3;
        }
        else if (x < 0) {
            return 4;
        }
        else if (x > 0) {
            return 5;
        }
        else{
            return 0;
        }
    }

    public List<Vertex5> renderFaceYNeg(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 0.5D, 0.0D, 0.5D, -1, 0, 0, 0, 0, 1, ((IconConnectedTexture)icon).flip(), 0, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

    public List<Vertex5> renderFaceYPos(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 0.5D, 1.0D, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture) icon, 1, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

    public List<Vertex5> renderFaceZNeg(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 0.5D, 0.5D, 0.0D, 1, 0, 0, 0, 1, 0, (IconConnectedTexture) icon, 2, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

    public List<Vertex5> renderFaceZPos(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 0.5D, 0.5D, 1.0D, -1, 0, 0, 0, 1, 0, (IconConnectedTexture) icon, 3, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

    public List<Vertex5> renderFaceXNeg(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 0.0D, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture) icon, 4, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

    public List<Vertex5> renderFaceXPos(IBlockAccess world, Block block, double x, double y, double z, TextureAtlasSprite icon) {
        if ((icon instanceof IconConnectedTexture)) {
            return this.renderSide(world, block, x, y, z, 1.0D, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture) icon, 5, 0.0D, 0.0D, 0.0D);
        }
        else {
            return Lists.newArrayList();
        }
    }

}
