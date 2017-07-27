package keri.projectx.util

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

object ScalaNBTExtensions {
  implicit def asIterableCompound(tag: NBTTagList): Iterable[NBTTagCompound] = (0 until tag.tagCount()).map(tag.getCompoundTagAt)
}
