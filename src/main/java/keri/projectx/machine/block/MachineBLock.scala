package keri.projectx.machine.block

import java.util

import com.google.common.collect.Lists
import com.projectxy.core.tile.TMachineTile
import net.minecraft.block.state.IBlockState
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}

trait MachineBlock extends Block with ITileEntityProvider {
  override def onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack): Unit = {
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack)
    val tile = worldIn.getTileEntity(pos)
    tile match {
      case machine: TMachineTile =>
        machine.readFromItemStack(stack)
      case _ =>
    }
  }

  override def getDrops(world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int): util.List[ItemStack] = {
    if (!customDrops)
      return super.getDrops(world, pos, state, fortune)

    world.getTileEntity(pos) match {
      case machine: TMachineTile =>
        val stack = new ItemStack(this, 1, getMetaFromState(state))
        machine.writeToItemStack(stack)
        Lists.newArrayList(stack)
      case _ => super.getDrops(world, pos, state, fortune)
    }
  }

  def customDrops: Boolean = true


  override def removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean = if (willHarvest) true else super.removedByPlayer(state, world, pos, player, willHarvest)


  override def harvestBlock(worldIn: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, te: TileEntity, stack: ItemStack): Unit = {
    super.harvestBlock(worldIn, player, pos, state, te, stack)
    worldIn.setBlockToAir(pos)
  }
}