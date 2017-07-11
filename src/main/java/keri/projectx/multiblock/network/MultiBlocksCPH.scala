/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock.network

import codechicken.lib.packet.ICustomPacketHandler.IClientPacketHandler
import codechicken.lib.packet.PacketCustom
import keri.projectx.data.ProjectXWorldExtensionInstantiator
import net.minecraft.client.Minecraft
import net.minecraft.network.play.INetHandlerPlayClient

//TODO: move this to ProejctXCPH
object MultiBlocksCPH extends IClientPacketHandler {
  val CHANNEL = "ProjectXMultiBlocks"

  override def handlePacket(packet: PacketCustom, minecraft: Minecraft, inhpc: INetHandlerPlayClient): Unit = {
    packet.getType match {
      case 1 => ProjectXWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleDescriptionPacket(packet)
      case 2 => ProjectXWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleRemoveMultiBlockPacket(packet)
      case 3 => ProjectXWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleMultiBlockUpdate(packet)
      case _ =>
    }
  }
}
