/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.playereffect;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.util.ClientUtils;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Scale;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.ninetaillib.mod.playereffect.IPlayerEffect;
import keri.projectx.ProjectX;
import keri.projectx.client.render.RenderQuartzCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerEffectQuartzCrystal implements IPlayerEffect {

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        RenderUtils.MipmapFilterData mipmapFilterData = RenderUtils.disableMipmap();
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        renderState.brightness = 0x00F000F0;
        GlStateManager.rotate(yawOffset, 0F, -1F, 0F);
        GlStateManager.rotate(yaw - 270F, 0F, 1F, 0F);
        GlStateManager.rotate(pitch, 0F, 0F, 1F);
        TextureAtlasSprite texture = ProjectX.PROXY.getAnimatedTexture();
        CCModel model = RenderQuartzCrystal.INSTANCE.getModel(texture, EnumFacing.UP, Vector3.zero);
        double sneakOffset = player.isSneaking() ? 0.4D : 0D;
        double floating = MathHelper.sin(player.ticksExisted * (MathHelper.torad * 8D)) / 16D;
        double angle = (double)(ClientUtils.getRenderTime() * 6D);
        model.apply(new Translation(new Vector3(-0.5D, -2.3D + sneakOffset, -0.5D)));
        model.apply(new Scale(new Vector3(0.5D, 0.5D, 0.5D)));
        model.setColour(new ColourRGBA(0, 200, 200, 255).rgba());

        if(!Minecraft.getMinecraft().isGamePaused()){
            model.apply(new Rotation(angle * MathHelper.torad, new Vector3(0D, 1D, 0D)));
            model.apply(new Translation(new Vector3(0D, floating, 0D)));
        }

        TextureUtils.bindBlockTexture();
        model.render(renderState);
        renderState.brightness = lastBrightness;
        Tessellator.getInstance().draw();
        RenderUtils.enableMipmap(mipmapFilterData);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

}
