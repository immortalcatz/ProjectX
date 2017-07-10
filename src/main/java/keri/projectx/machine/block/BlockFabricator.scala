package keri.projectx.machine.block

import codechicken.lib.colour.ColourRGBA
import keri.projectx.machine.tile.TileFabricator
import com.projectxy.world.block.BlockXY
import com.projectxy.world.block.glow.ITextureRegisteringAnimationHandler
import com.projectxy.world.{GuiHandler, ProjectXYBlocksTab, ProjectXYWorld, WorldBlocks}
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}

class BlockFabricator extends BlockXY(Material.ROCK) with ITextureRegisteringAnimationHandler with ITileEntityProvider with MachineBlock {
  setBlockName("fabricator")
  setCreativeTab(ProjectXYBlocksTab)
  setHardness(1.0F)

  //  override val hasColorMultiplier: Boolean = false
  //
  //  override def getColor(iB4lockAccess: IBlockAccess, x: Int, y: Int, z: Int): Int = color
  override val textureName: String = "fabricator"
  val icons = Array.fill[TextureAtlasSprite](2)(null)

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileFabricator


  //  override def getBrightness(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
  //    world.getTileEntity(x, y, z) match {
  //      case tileFab: TileFabricator =>
  //        tileFab.mode match {
  //          case 0 => if (tileFab.powered) 120 else 220
  //          case 1 => if (tileFab.powered) 220 else 120
  //          case 2 => if (tileFab.powered) 220 else 120
  //          case _ => 220
  //        }
  //      case _ => 220
  //    }
  //  }

  //  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, par6: Int): Unit = {
  //    world.getTileEntity(x, y, z).asInstanceOf[TileFabricator].dropItems()
  //    super.breakBlock(world, x, y, z, block, par6)
  //  }

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (!playerIn.isSneaking && worldIn.getTileEntity(pos).isInstanceOf[TileFabricator]) {
      playerIn.openGui(ProjectXYWorld, GuiHandler.GuiIds.FABRICATOR.id, worldIn, pos.getX, pos.getY, pos.getZ)
    }
    true
  }

  override def customDrops: Boolean = false

  override def getAnimationColor(world: IBlockAccess, state: IBlockState, pos: BlockPos, side: Int): Int = WorldBlocks.xyColors.head.rgba()

  override def getAnimationColor(stack: ItemStack, side: Int): Int = WorldBlocks.xyColors.head.rgba()
}

object BlockFabricator {
  lazy val color = new ColourRGBA(0, 100, 255, 255).rgb()
}
