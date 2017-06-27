/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.item;

import codechicken.lib.util.ClientUtils;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.client.render.RenderTruncatedIcosahedron;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPowerCore implements IItemRenderingHandler {

    public static final RenderPowerCore INSTANCE = new RenderPowerCore();
    public static EnumItemRenderType RENDER_TYPE;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer) {
        Tessellator.getInstance().draw();
        RenderTruncatedIcosahedron renderer = RenderTruncatedIcosahedron.getInstance();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, 0.5D, 0.5D);
        GlStateManager.rotate((float)(-ClientUtils.getRenderTime() * 20F) * 0.5F, 0F, 1F, 0F);
        GlStateManager.rotate((float)(-ClientUtils.getRenderTime() * 5F) * 0.5F, 0F, 0F, 1F);
        GlStateManager.rotate((float)(-ClientUtils.getRenderTime() * -2F) * 0.5F, 1F, 0F, 0F);
        renderer.render(1D, 0xFFFFFFFF, 0xFFFFFFFF, RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
        GlStateManager.popMatrix();
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
