/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderConnectedTexture implements IBlockRenderingHandler {

    //TODO: move anything ctm related to NineTailLib once cleaned up

    public static final RenderConnectedTexture INSTANCE = new RenderConnectedTexture();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel DEFAULT_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        DEFAULT_MODEL = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D));
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        TextureAtlasSprite texture = iconProvider.getIcon(0, 0);
        BlockRenderContext renderContext = new BlockRenderContext();
        renderContext.setBlockAccess(world);
        renderContext.setCurrentBlockState(ProjectXContent.GLASS_VIEWER.getDefaultState());
        renderContext.setChangeBounds(false);
        renderContext.renderStandardBlock(pos, texture);
        BakingVertexBuffer parent = BakingVertexBuffer.create();
        parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(parent);
        renderContext.getModel().render(renderState);
        parent.finishDrawing();
        return RenderUtils.renderQuads(buffer, world, pos, parent.bake());
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        renderState.reset();
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
        renderState.reset();
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

}
