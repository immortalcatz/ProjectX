/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.multiblock;

import net.minecraft.nbt.NBTTagCompound;

public enum MultiBlockType {
    TANK;

    static String NBT_TAG_KEY = "multi_block_type";

    public static MultiBlockType fromNBT(NBTTagCompound tag) {
        return MultiBlockType.values()[tag.getInteger(NBT_TAG_KEY)];
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger(NBT_TAG_KEY, ordinal());
    }
}
