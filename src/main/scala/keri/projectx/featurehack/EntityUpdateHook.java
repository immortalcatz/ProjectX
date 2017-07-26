/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.featurehack;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityUpdateHook extends Entity {
    public final IUpdateCallback callback;

    public EntityUpdateHook(World world, int x, int y, int z, IUpdateCallback callback) {
        super(world);
        setPosition(x, y, z);
        this.callback = callback;
    }

    @Override
    public void onUpdate() {
        if (!callback.isValid())
            setDead();
        else
            callback.onUpdate();
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

    public static interface IUpdateCallback {
        public void onUpdate();

        public boolean isValid();
    }
}