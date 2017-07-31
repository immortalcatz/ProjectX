/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.util

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

object ScalaNBTExtensions {
  implicit def asIterableCompound(tag: NBTTagList): Iterable[NBTTagCompound] = (0 until tag.tagCount()).map(tag.getCompoundTagAt)
}
