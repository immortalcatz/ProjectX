/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.tile

import java.util

import keri.projectx.multiblock.MultiBlock
import keri.projectx.multiblock.fluid.TFluidMultiBlock
import net.minecraft.entity.player.EntityPlayer

import scala.collection.JavaConversions._


abstract class TileMultiBlock extends TileEntityProjectX {
  val formedMultiBlocks = new util.LinkedList[MultiBlock]

  def onBlockActivated(player: EntityPlayer): Boolean = {
    if (player.isSneaking)
      return false

    var ret = false
    for (i <- 0 until formedMultiBlocks.size()) {
      val act = formedMultiBlocks.get(i).onActivated(getPos, player)
      act match {
        case 1 => return true
        case 2 => ret = true
        case _ =>
      }
    }
    ret
  }

  override def invalidate(): Unit = {
    super.invalidate()
    if (!getWorld.isRemote) {
      for (i <- 0 until formedMultiBlocks.size()) {
        formedMultiBlocks.get(i).getWorldExt.removeMultiBlock(formedMultiBlocks.get(i))
      }
    }
  }

  def isAlreadyPartOfStructure: Boolean = !formedMultiBlocks.isEmpty

  def joinMultiBlock(multiBlock: MultiBlock) = formedMultiBlocks.add(multiBlock)

  def removeMultiBlock(multiBlock: MultiBlock) = formedMultiBlocks.remove(multiBlock)

  def getTank(index: Int): Option[TFluidMultiBlock] = {
    //Have to iterate each one because it'll crash... -_-
    var tankIndex = 0
    for (multiBlock <- formedMultiBlocks) {
      multiBlock match {
        case x: TFluidMultiBlock =>
          if (tankIndex == index)
            return Some(x)
        case _ =>
      }
      tankIndex += 1
    }
    None
  }
}
