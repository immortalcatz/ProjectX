/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.featurehack;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRenderHook extends Entity {
    public final IRenderCallback callback;

    public EntityRenderHook(World world, double x, double y, double z, IRenderCallback callback) {
        super(world);
        setPosition(x, y, z);
        ignoreFrustumCheck = true;
        this.callback = callback;
    }

    @Override
    public void onUpdate() {
        if (!callback.isValid())
            setDead();
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return callback.shouldRenderInPass(pass);
    }

    public static interface IRenderCallback {
        public void render(float frame, int pass);

        public boolean shouldRenderInPass(int pass);

        public boolean isValid();
    }
}