package keri.projectx.machine.multiblock.block

import keri.projectx.machine.multiblock.tile.TileMultiBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.world.World

trait BlockMulti extends Block with ITileEntityProvider {
  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (worldIn.isRemote)
      return true
    worldIn.getTileEntity(pos) match {
      case tileMultiBlock: TileMultiBlock => tileMultiBlock.onBlockActivated(playerIn)
      case _ => true
    }
  }
}
