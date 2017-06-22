/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.item;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingConstants;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.ProjectX;
import keri.projectx.util.EnumUpgradeType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderUpgradeCard implements IItemRenderingHandler {

    public static EnumItemRenderType RENDER_TYPE;
    private static CCModel[] ITEM_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderUpgradeCard());
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(3D, 2D, 7D, 13D, 12D, 8D),
                new Cuboid6(3D, 4D, 8D, 13D, 12D, 9D),
                new Cuboid6(3D, 2D, 8D, 3.5D, 4D, 9D),
                new Cuboid6(12.5D, 2D, 8D, 13D, 4D, 9D),
                new Cuboid6(4D, 2D, 8D, 5D, 4D, 8.5D),
                new Cuboid6(6D, 2D, 8D, 7D, 4D, 8.5D),
                new Cuboid6(8D, 2D, 8D, 9D, 4D, 8.5D),
                new Cuboid6(10D, 2D, 8D, 12D, 4D, 8.5D),
                new Cuboid6(3D, 12D, 7D, 11D, 14D, 9D),
                new Cuboid6(9.5D, 11.4D, 7D, 12.5D, 13.4D, 8.75D)
        };
        ITEM_MODEL = ModelUtils.getNormalized(bounds);
        ModelUtils.rotate(ITEM_MODEL[9], -45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 12D, 8D));
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer) {
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        Tessellator.getInstance().draw();
        IIconItem iconProvider = (IIconItem)stack.getItem();
        Colour color = EnumUpgradeType.VALUES[stack.getMetadata()].getColor();
        TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata());
        TextureAtlasSprite textureBack = iconProvider.getIcon(EnumUpgradeType.VALUES.length);
        CCRenderState renderState = RenderingConstants.getRenderState();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = 0x00F000F0;
        ITEM_MODEL[1].setColour(color.rgba());
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        ITEM_MODEL[1].render(renderState, new IconTransformation(ProjectX.PROXY.getAnimatedTexture()));
        renderState.brightness = lastBrightness;
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(buffer);
        ITEM_MODEL[0].render(renderState, new IconTransformation(textureBack));
        ITEM_MODEL[1].setColour(0xFFFFFFFF);

        for(int i = 0; i < 6; i++){
            IconTransformation icon = new IconTransformation(i == 3 ? texture : textureBack);
            ITEM_MODEL[1].render(renderState, (4 * i), 4 + (4 * i), icon);
        }

        ITEM_MODEL[2].render(renderState, new IconTransformation(textureBack));
        ITEM_MODEL[3].render(renderState, new IconTransformation(textureBack));
        ITEM_MODEL[4].render(renderState, new IconTransformation(texture));
        ITEM_MODEL[5].render(renderState, new IconTransformation(texture));
        ITEM_MODEL[6].render(renderState, new IconTransformation(texture));
        ITEM_MODEL[7].render(renderState, new IconTransformation(texture));
        ITEM_MODEL[8].render(renderState, new IconTransformation(textureBack));
        ITEM_MODEL[9].render(renderState, new IconTransformation(textureBack));
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
