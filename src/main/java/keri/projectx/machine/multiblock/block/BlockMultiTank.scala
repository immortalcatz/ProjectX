package keri.projectx.machine.multiblock.block

import keri.projectx.machine.multiblock.MultiTankInitiator
import keri.projectx.machine.multiblock.tile.TileValve
import com.projectxy.world.block.BlockXY
import com.projectxy.world.block.glow.ITextureRegisteringAnimationHandler
import com.projectxy.world.{ProjectXYBlocksTab, WorldBlocks}
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}

class BlockMultiTank extends BlockXY(Material.ROCK) with ITextureRegisteringAnimationHandler with BlockMulti {
  setBlockName("blockMultiTank")
  setCreativeTab(ProjectXYBlocksTab)

  override val textureName: String = "valve"

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileValve

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    world.getTileEntity(pos) match {
      case valve: TileValve =>
        if (!world.isRemote) {
          if (valve.isAlreadyPartOfStructure)
            return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
          MultiTankInitiator.create(world, pos)
        }
      case _ =>
    }
    false
  }

  override def getAnimationColor(world: IBlockAccess, state: IBlockState, pos: BlockPos, side: Int): Int = WorldBlocks.xyColors.head.rgba()

  override def getAnimationColor(stack: ItemStack, side: Int): Int = WorldBlocks.xyColors.head.rgba()
}

