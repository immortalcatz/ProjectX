/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.item;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCModelState;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderXynergyTool implements IItemRenderingHandler {

    public static final RenderXynergyTool INSTANCE = new RenderXynergyTool();
    public static EnumItemRenderType RENDER_TYPE;
    private static CCModel[] ITEM_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(7D, 0D, 7D, 9D, 4D, 9D),
                new Cuboid6(7D, 8D, 7D, 9D, 12D, 9D),
                new Cuboid6(7.25D, 4D, 7.25D, 8.75D, 8D, 8.75D),
                new Cuboid6(7D, 12D, 7.25D, 8D, 15D, 8.75D),
                new Cuboid6(8D, 12D, 7.25D, 9D, 15D, 8.75D),
                new Cuboid6(5D, 13D, 7.5D, 6D, 15D, 8.5D),
                new Cuboid6(10D, 13D, 7.5D, 11D, 15D, 8.5D)
        };
        ITEM_MODEL = ModelUtils.getNormalized(BOUNDS);
        ModelUtils.rotate(ITEM_MODEL[3], 45D, new Vector3(0D, 0D, 1D), new Vector3(8D, 12D, 8D));
        ModelUtils.rotate(ITEM_MODEL[4], -45D, new Vector3(0D, 0D, 1D), new Vector3(8D, 12D, 8D));
        ModelUtils.rotate(ITEM_MODEL[5], -22.5D, new Vector3(0D, 0D, 1D), new Vector3(7D, 12D, 8D));
        ModelUtils.rotate(ITEM_MODEL[6], 22.5D, new Vector3(0D, 0D, 1D), new Vector3(9D, 12D, 8D));
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType) {
        IIconItem iconProvider = (IIconItem)stack.getItem();
        TextureAtlasSprite textureSide = iconProvider.getIcon(0);
        TextureAtlasSprite textureBottom = iconProvider.getIcon(1);
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);

        for(int part = 0; part < ITEM_MODEL.length; part++){
            for(int side = 0; side < 6; side++){
                int rs = 4 * side;
                int re = 4 + (4 * side);
                IconTransformation iconTransformation = null;

                if(side < 2){
                    iconTransformation = new IconTransformation(textureBottom);
                }
                else{
                    iconTransformation = new IconTransformation(textureSide);
                }

                ITEM_MODEL[part].render(renderState, rs, re, iconTransformation);
            }
        }

        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public CCModelState getTransforms() {
        return TransformUtils.DEFAULT_HANDHELD_ROD;
    }

}
