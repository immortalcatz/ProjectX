package keri.projectx.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ParticleSparkle extends Particle {

    public int multiplier;
    public boolean shrink;
    public int particle;
    public boolean tinkle;
    public int blendmode;

    public ParticleSparkle(World world, double d, double d1, double d2, float f, float f1, float f2, float f3, int m) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.multiplier = 4;
        this.shrink = false;
        this.particle = 0;
        this.tinkle = true;
        this.blendmode = 1;
        if(f1 == 0.0F){ f1 = 1.0F; }
        super.particleRed = f1;
        super.particleGreen = f2;
        super.particleBlue = f3;
        super.particleGravity = 0.0F;
        super.motionX = super.motionY = super.motionZ = 0.0D;
        super.particleScale *= f;
        super.particleMaxAge = 4 * m;
        this.multiplier = m;
        this.setParticleTexture(ParticleManager.INSTANCE.getParticleTexture("sparkle"));
    }

    public ParticleSparkle(World world, double x, double y, double z, float scale, int type, int multiplier) {
        this(world, x, y, z, scale, 0.0F, 0.0F, 0.0F, multiplier);

        switch (type) {
            case 0:
                super.particleRed = 0.75F + world.rand.nextFloat() * 0.25F;
                super.particleGreen = 0.25F + world.rand.nextFloat() * 0.25F;
                super.particleBlue = 0.75F + world.rand.nextFloat() * 0.25F;
                break;
            case 1:
                super.particleRed = 0.5F + world.rand.nextFloat() * 0.3F;
                super.particleGreen = 0.5F + world.rand.nextFloat() * 0.3F;
                super.particleBlue = 0.2F;
                break;
            case 2:
                super.particleRed = 0.2F;
                super.particleGreen = 0.2F;
                super.particleBlue = 0.7F + world.rand.nextFloat() * 0.3F;
                break;
            case 3:
                super.particleRed = 0.2F;
                super.particleGreen = 0.7F + world.rand.nextFloat() * 0.3F;
                super.particleBlue = 0.2F;
                break;
            case 4:
                super.particleRed = 0.7F + world.rand.nextFloat() * 0.3F;
                super.particleGreen = 0.2F;
                super.particleBlue = 0.2F;
                break;
            case 5:
                this.blendmode = 771;
                super.particleRed = world.rand.nextFloat() * 0.1F;
                super.particleGreen = world.rand.nextFloat() * 0.1F;
                super.particleBlue = world.rand.nextFloat() * 0.1F;
                break;
            case 6:
                super.particleRed = 0.8F + world.rand.nextFloat() * 0.2F;
                super.particleGreen = 0.8F + world.rand.nextFloat() * 0.2F;
                super.particleBlue = 0.8F + world.rand.nextFloat() * 0.2F;
                break;
            case 7:
                super.particleRed = 0.2F;
                super.particleGreen = 0.5F + world.rand.nextFloat() * 0.3F;
                super.particleBlue = 0.6F + world.rand.nextFloat() * 0.3F;
        }
    }

    public ParticleSparkle(World world, double d, double d1, double d2, double x, double y, double z, float f, int type, int m) {
        this(world, d, d1, d2, f, type, m);
        double dx = x - super.posX;
        double dy = y - super.posY;
        double dz = z - super.posZ;
        super.motionX = dx / (double) super.particleMaxAge;
        super.motionY = dy / (double) super.particleMaxAge;
        super.motionZ = dz / (double) super.particleMaxAge;
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entity, float partialTicks, float rx, float ry, float rz, float ryz, float rxy) {
        int part = this.particle + super.particleAge / this.multiplier;
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

    public void setGravity(float value) {
        super.particleGravity = value;
    }

    public void setParticle(int part) {
        this.particle = part;
    }

}
