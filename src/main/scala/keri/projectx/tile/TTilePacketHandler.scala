/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.tile

import codechicken.lib.packet.PacketCustom
import keri.projectx.ProjectX
import net.minecraft.tileentity.TileEntity

/**
  * Used to send data to client without using NBT data
  */
trait TTilePacketHandler extends TileEntity {
  def handlePacket(packetCustom: PacketCustom): Unit

  def preparePacket(): PacketCustom = new PacketCustom(ProjectX.INSTANCE, 5).writePos(getPos)
}
