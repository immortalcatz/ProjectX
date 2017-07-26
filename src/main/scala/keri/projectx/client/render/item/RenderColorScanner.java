/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render.item;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.MultiIconTransformation;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
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
public class RenderColorScanner implements IItemRenderingHandler {

    public static final RenderColorScanner INSTANCE = new RenderColorScanner();
    public static EnumItemRenderType RENDER_TYPE;
    private static CCModel[] ITEM_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(4D, 2D, 7D, 12D, 14D, 8D),
                new Cuboid6(4D, 13D, 8D, 12D, 14D, 9D),
                new Cuboid6(4D, 2D, 8D, 12D, 9D, 9D),
                new Cuboid6(4D, 9D, 8D, 5D, 13D, 9D),
                new Cuboid6(11D, 9D, 8D, 12D, 13D, 9D),
                new Cuboid6(5D, 9D, 8D, 11D, 13D, 8.5D),
                new Cuboid6(9D, 14D, 7.75D, 9.5D, 15D, 8.25D),
                new Cuboid6(10D, 14D, 7.75D, 10.5D, 15D, 8.25D),
                new Cuboid6(11D, 14D, 7.75D, 11.5D, 15D, 8.25D)
        };
        ITEM_MODEL = ModelUtils.getNormalized(BOUNDS);
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType){
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        IIconItem iconProvider = (IIconItem)stack.getItem();
        Colour displayColor = null;

        if(stack.getTagCompound() != null){
            displayColor = new ColourRGBA(stack.getTagCompound().getInteger("color"));
        }
        else{
            displayColor = new ColourRGBA(40, 40, 40, 255);
        }

        TextureAtlasSprite textureBack = iconProvider.getIcon(0);
        TextureAtlasSprite textureFront = iconProvider.getIcon(1);
        TextureAtlasSprite textureSide = iconProvider.getIcon(2);
        TextureAtlasSprite textureAnimation = ProjectX.PROXY.getAnimatedTexture();
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);

        for(int part = 0; part < ITEM_MODEL.length; part++){
            if(part == 0){
                ITEM_MODEL[part].render(renderState, new MultiIconTransformation(textureSide, textureSide, textureBack, textureBack, textureSide, textureSide));
            }
            else if(part > 0 && part < 5){
                ITEM_MODEL[part].render(renderState, new MultiIconTransformation(textureSide, textureSide, textureFront, textureFront, textureSide, textureSide));
            }
        }

        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = 0x00F000F0;

        for(int part = 0; part < ITEM_MODEL.length; part++){
            if(part == 5){
                ITEM_MODEL[part].setColour(displayColor.rgba());
                ITEM_MODEL[part].render(renderState, new IconTransformation(textureAnimation));
            }
            else if(part > 5){
                ITEM_MODEL[part].setColour(EnumXycroniumColor.VALUES[part - 6].getColor().rgba());
                ITEM_MODEL[part].render(renderState, new IconTransformation(textureAnimation));
            }
        }

        Tessellator.getInstance().draw();
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.popMatrix();
        renderState.brightness = lastBrightness;
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
