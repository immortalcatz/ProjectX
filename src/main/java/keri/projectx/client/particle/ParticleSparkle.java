/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.particle;

import keri.projectx.client.IconHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ParticleSparkle extends Particle {

    public ParticleSparkle(World world, double x, double y, double z, float scale, float r, float g, float b, int multiplier) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        if(r == 0.0F){ r = 1.0F; }
        super.particleRed = r;
        super.particleGreen = g;
        super.particleBlue = b;
        super.particleGravity = 0.0F;
        super.motionX = super.motionY = super.motionZ = 0.0D;
        super.particleScale *= scale;
        super.particleMaxAge = 4 * multiplier;
        this.setParticleTexture(IconHandler.INSTANCE.getIcon("particle_sparkle"));
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entity, float partialTicks, float rx, float ry, float rz, float ryz, float rxy) {
        float minU = this.particleTexture.getMinU();
        float maxU = this.particleTexture.getMaxU();
        float minV = this.particleTexture.getMinV();
        float maxV = this.particleTexture.getMaxV();
        float scale = 0.1F * super.particleScale * ((float) (super.particleMaxAge - super.particleAge + 1) / (float) super.particleMaxAge);
        float posX = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)partialTicks - Particle.interpPosX);
        float posY = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)partialTicks - Particle.interpPosY);
        float posZ = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)partialTicks - Particle.interpPosZ);
        int brightness = this.getBrightnessForRender(partialTicks);
        int l1 = brightness >> 0x10 & 0xFFFF;
        int l2 = brightness & 0xFFFF;
        float r = this.particleRed;
        float g = this.particleGreen;
        float b = this.particleBlue;
        float a = 1F;
        buffer.pos((double)(posX - rx * scale - ryz * scale), (double)(posY - ry * scale), (double)(posZ - rz * scale - rxy * scale)).tex(maxU, maxV).color(r, g, b, a).lightmap(l1, l2).endVertex();
        buffer.pos((double)(posX - rx * scale + ryz * scale), (double)(posY + ry * scale), (double)(posZ - rz * scale + rxy * scale)).tex(maxU, minV).color(r, g, b, a).lightmap(l1, l2).endVertex();
        buffer.pos((double)(posX + rx * scale + ryz * scale), (double)(posY + ry * scale), (double)(posZ + rz * scale + rxy * scale)).tex(minU, minV).color(r, g, b, a).lightmap(l1, l2).endVertex();
        buffer.pos((double)(posX + rx * scale - ryz * scale), (double)(posY - ry * scale), (double)(posZ + rz * scale - rxy * scale)).tex(minU, maxV).color(r, g, b, a).lightmap(l1, l2).endVertex();
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 240;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        EntityPlayer e = Minecraft.getMinecraft().player;
        byte visibleDistance = 50;

        if (!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }

        if (e.getDistance(super.posX, super.posY, super.posZ) > (double) visibleDistance) {
            this.setExpired();
        }

        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;

        if (super.particleAge++ >= super.particleMaxAge) {
            this.setExpired();
        }

        super.motionY -= 0.04D * (double) super.particleGravity;
        this.move(super.motionX, super.motionY, super.motionZ);
        super.motionX *= 0.9800000190734863D;
        super.motionY *= 0.9800000190734863D;
        super.motionZ *= 0.9800000190734863D;

        if (super.onGround) {
            super.motionX *= 0.699999988079071D;
            super.motionZ *= 0.699999988079071D;
        }
    }

    public void setGravity(float gravity){
        this.particleGravity = gravity;
    }

}
