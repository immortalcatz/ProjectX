/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.MultiIconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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

@SideOnly(Side.CLIENT)
public class RenderXynergyNode implements IBlockRenderingHandler {

    public static final RenderXynergyNode INSTANCE = new RenderXynergyNode();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(3D, 0D, 3D, 7D, 1D, 7D),
                new Cuboid6(9D, 0D, 3D, 13D, 1D, 7D),
                new Cuboid6(9D, 0D, 9D, 13D, 1D, 13D),
                new Cuboid6(3D, 0D, 9D, 7D, 1D, 13D),
                new Cuboid6(3D, 1D, 6D, 5D, 2D, 10D),
                new Cuboid6(11D, 1D, 6D, 13D, 2D, 10D),
                new Cuboid6(6D, 1D, 11D, 10D, 2D, 13D),
                new Cuboid6(6D, 1D, 3D, 10D, 2D, 5D),
                new Cuboid6(3.5D, 1D, 2.5D, 5.5D, 2D, 6.5D),
                new Cuboid6(10.5D, 1D, 2.5D, 12.5D, 2D, 6.5D),
                new Cuboid6(3.25D, 1D, 10D, 5.25D, 2D, 14D),
                new Cuboid6(10.75D, 1D, 10D, 12.75D, 2D, 14D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
        ModelUtils.rotate(BLOCK_MODEL[8], -45D, new Vector3(0D, 1D, 0D), new Vector3(4D, 8D, 6D));
        ModelUtils.rotate(BLOCK_MODEL[9], 45D, new Vector3(0D, 1D, 0D), new Vector3(12D, 8D, 6D));
        ModelUtils.rotate(BLOCK_MODEL[10], 45D, new Vector3(0D, 1D, 0D), new Vector3(3D, 8D, 10D));
        ModelUtils.rotate(BLOCK_MODEL[11], -45D, new Vector3(0D, 1D, 0D), new Vector3(13D, 8D, 10D));
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, BlockRenderLayer layer) {
        EnumFacing orientation = EnumFacing.getFront(BlockAccessUtils.getBlockMetadata(world, pos));
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        TextureAtlasSprite textureTop = iconProvider.getIcon(0, EnumFacing.DOWN);
        TextureAtlasSprite textureBottom = iconProvider.getIcon(0, EnumFacing.UP);
        TextureAtlasSprite textureSide = iconProvider.getIcon(0, EnumFacing.NORTH);
        BakingVertexBuffer parent = BakingVertexBuffer.create();
        parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(parent);

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelPart = BLOCK_MODEL[part].copy();

            switch(orientation){
                case DOWN:
                    ModelUtils.rotate(modelPart, 180D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D));
                    break;
                case UP:
                    break;
                case NORTH:
                    ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D));
                    break;
                case EAST:
                    ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D));
                    ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D));
                    break;
                case SOUTH:
                    ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D));
                    break;
                case WEST:
                    ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D));
                    ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D));
                    break;
            }

            if(part < 4){
                modelPart.render(renderState, new MultiIconTransformation(textureBottom, textureBottom, textureSide, textureSide, textureSide, textureSide));
            }
            else if(part > 3 && part < 8){
                modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide));
            }
            else if(part > 7){
                double zOffset = 0.0004D;
                modelPart.zOffset(new Cuboid6(zOffset, zOffset, zOffset, zOffset, zOffset, zOffset));
                modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide));
            }
        }

        parent.finishDrawing();
        return RenderUtils.renderQuads(buffer, world, pos, parent.bake());
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelPart = BLOCK_MODEL[part].copy();
            modelPart.apply(new Translation(Vector3.fromBlockPos(pos)));
            modelPart.render(renderState, new IconTransformation(texture));
        }
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        TextureAtlasSprite textureTop = iconProvider.getIcon(0, EnumFacing.DOWN);
        TextureAtlasSprite textureBottom = iconProvider.getIcon(0, EnumFacing.UP);
        TextureAtlasSprite textureSide = iconProvider.getIcon(0, EnumFacing.NORTH);
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);

        for(int part = 0; part < BLOCK_MODEL.length; part++){
            CCModel modelPart = BLOCK_MODEL[part].copy();

            if(part < 4){
                modelPart.render(renderState, new MultiIconTransformation(textureBottom, textureBottom, textureSide, textureSide, textureSide, textureSide));
            }
            else if(part > 3 && part < 8){
                modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide));
            }
            else if(part > 7){
                modelPart.zOffset(new Cuboid6(0D, 0D, 0D, 0D, 0.0004D, 0D));
                modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide));
            }
        }

        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
