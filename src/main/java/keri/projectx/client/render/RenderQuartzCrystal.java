/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.*;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
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
public class RenderQuartzCrystal implements IBlockRenderingHandler {

    public static final RenderQuartzCrystal INSTANCE = new RenderQuartzCrystal();
    public static EnumBlockRenderType RENDER_TYPE;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
        IAnimationHandler animationHandler = (IAnimationHandler)world.getBlockState(pos).getBlock();
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        TextureAtlasSprite texture = animationHandler.getAnimationIcon(world, pos, 0);
        int animationColor = animationHandler.getAnimationColor(world, pos, 0);
        int animationBrightness = animationHandler.getAnimationBrightness(world, pos, 0);
        renderState.brightness = animationBrightness;
        CCModel model = this.getModel(texture, EnumFacing.getFront(BlockAccessUtils.getBlockMetadata(world, pos)), Vector3.fromBlockPos(pos));
        model.setColour(animationColor);
        model.render(renderState);
        return true;
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        Colour color = new ColourRGBA(255, 255, 255, 255);
        CCModel model = this.getModel(texture, EnumFacing.getFront(BlockAccessUtils.getBlockMetadata(world, pos)), Vector3.fromBlockPos(pos));
        model.setColour(color.rgba());
        model.render(renderState);
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        IAnimationHandler animationHandler = (IAnimationHandler)Block.getBlockFromItem(stack.getItem());
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = CCRenderState.instance();
        TextureAtlasSprite texture = animationHandler.getAnimationIcon(stack, 0);
        int animationBrightness = animationHandler.getAnimationBrightness(stack, 0);
        int animationColor = animationHandler.getAnimationColor(stack, 0);
        renderState.bind(buffer);
        renderState.brightness = animationBrightness;
        CCModel model = this.getModel(texture, null, Vector3.zero);
        model.setColour(animationColor);
        model.apply(new Translation(new Vector3(-0.5D, -0.5D, -0.5D)));
        model.apply(new Scale(new Vector3(2D, 2D, 2D)));
        model.apply(new Translation(new Vector3(0.5D, 0.5D, 0.5D)));
        model.render(renderState);
        renderState.brightness = lastBrightness;
        Tessellator.getInstance().draw();
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

    public CCModel getModel(TextureAtlasSprite texture, EnumFacing side, Vector3 pos){
        double modX = 0D;
        double modY = 0D;
        double modZ = 0D;
        Rotation rotation = new Rotation(0D, Vector3.zero);

        if(side != null){
            switch(side.getOpposite().getIndex()) {
                case 0:
                    modY = 0.3D;
                    break;
                case 1:
                    modY = -0.3D;
                    break;
                case 2:
                    rotation = new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D));
                    modZ = 0.3D;
                    break;
                case 3:
                    rotation = new Rotation(-90D * MathHelper.torad, new Vector3(1D, 0D, 0D));
                    modZ = -0.3D;
                    break;
                case 4:
                    rotation = new Rotation(90D * MathHelper.torad, new Vector3(0D, 0D, 1D));
                    modX = 0.3D;
                    break;
                case 5:
                    rotation = new Rotation(-90D * MathHelper.torad, new Vector3(0D, 0D, 1D));
                    modX = -0.3D;
                    break;
            }
        }

        Vector3 position = pos.copy();
        position.add(0.5D + modX, 0.5D + modY, 0.5D + modZ);
        CCModel model = CCModel.quadModel(24);
        float u = texture.getMinU();
        float v = texture.getMinV();
        float U = texture.getMaxU();
        float V = texture.getMaxV();
        model.verts[0] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[1] = new Vertex5(0.09995D, 0.121D, 0.0D, u, V);
        model.verts[2] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, V);
        model.verts[3] = new Vertex5(-0.04704D, 0.121D, -0.08485D, U, v);
        model.verts[4] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[5] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, V);
        model.verts[6] = new Vertex5(-0.09995D, -0.121D, 0.0D, U, V);
        model.verts[7] = new Vertex5(-0.04704D, 0.121D, 0.08485D, U, v);
        model.verts[8] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[9] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, V);
        model.verts[10] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, V);
        model.verts[11] = new Vertex5(0.09995D, 0.121D, 0.0D, U, v);
        model.verts[12] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, v);
        model.verts[13] = new Vertex5(-0.09995D, -0.121D, -0.0D, u, V);
        model.verts[14] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[15] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, v);
        model.verts[16] = new Vertex5(0.09995D, 0.121D, 0.0D, u, v);
        model.verts[17] = new Vertex5(0.04704D, -0.121D, 0.08485D, u, V);
        model.verts[18] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[19] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, v);
        model.verts[20] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, v);
        model.verts[21] = new Vertex5(0.04704D, -0.121D, -0.08485D, u, V);
        model.verts[22] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[23] = new Vertex5(-0.09995D, -0.121D, -0.0D, U, v);
        return model.computeNormals().apply(rotation).apply(new Translation(position));
    }

}
