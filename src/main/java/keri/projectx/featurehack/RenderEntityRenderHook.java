/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.featurehack;

import codechicken.lib.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

/**
 * Created by Adam on 7/4/2017.
 */
public class RenderEntityRenderHook extends Render<EntityRenderHook> {

    protected RenderEntityRenderHook() {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    @Override
    public void doRender(EntityRenderHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
        EntityRenderHook hook = (EntityRenderHook) entity;
        GL11.glTranslated(x - hook.posX, y - hook.posY, z - hook.posZ);
        entity.callback.render(ClientUtils.getRenderFrame(), MinecraftForgeClient.getRenderPass());
        GL11.glTranslated(hook.posX - x, hook.posY - y, hook.posZ - z);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRenderHook entity) {
        return null;
    }
}
