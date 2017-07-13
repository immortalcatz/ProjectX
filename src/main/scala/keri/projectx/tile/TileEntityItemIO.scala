/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.tile

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ITickable

import scala.beans.BeanProperty

/**
  * Created by Adam on 7/12/2017.
  */
class TileEntityItemIO extends TileMultiBlock with ITickable {
  @BeanProperty var currentState: ItemIOState = ItemIOState.BOTH

  override def update(): Unit = {
  }

  override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
    tag.setInteger("state", currentState.ordinal())
    super.writeToNBT(tag)
  }

  override def readFromNBT(tag: NBTTagCompound): Unit = {
    super.readFromNBT(tag)
    setState(ItemIOState.values()(tag.getInteger("state")))
  }
  def setState(state: ItemIOState): Unit = {
    if (state != currentState) {
      currentState = state
      sendUpdatePacket(true)
    }
  }
  override def onBlockActivated(player: EntityPlayer): Boolean = {
    if (player.isSneaking) {
      setState(currentState.nextState())
    }
    super.onBlockActivated(player)
  }
}

