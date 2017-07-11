package keri.projectx.multiblock.network

import codechicken.lib.packet.ICustomPacketHandler.IClientPacketHandler
import codechicken.lib.packet.PacketCustom
import keri.projectx.multiblock.XYWorldExtensionInstantiator
import net.minecraft.client.Minecraft
import net.minecraft.network.play.INetHandlerPlayClient

object MultiBlocksCPH extends IClientPacketHandler {
  val CHANNEL = "ProjectXMultiBlocks"

  override def handlePacket(packet: PacketCustom, minecraft: Minecraft, inhpc: INetHandlerPlayClient): Unit = {
    packet.getType match {
      case 1 => XYWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleDescriptionPacket(packet)
      case 2 => XYWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleRemoveMultiBlockPacket(packet)
      case 3 => XYWorldExtensionInstantiator.getExtensionXy(minecraft.world).handleMultiBlockUpdate(packet)
      case _ =>
    }
  }
}
