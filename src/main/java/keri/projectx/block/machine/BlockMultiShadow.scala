/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block.machine

import java.util
import java.util.Random

import keri.projectx.block.BlockProjectX
import keri.projectx.client.render.ShadowBlockRenderer
import keri.projectx.tile.{BlockDef, TileMultiShadow}
import keri.projectx.util.ModPrefs
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.particle.ParticleManager
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.{AxisAlignedBB, BlockPos, RayTraceResult}
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType, EnumFacing}
import net.minecraft.world.{Explosion, IBlockAccess, World}
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockMultiShadow(material: Material, suffix: String) extends BlockProjectX[TileMultiShadow](s"blockMultiShadow$suffix", material) with BlockMulti {
  setCreativeTab(null)
  material match {
    case Material.WOOD => setSoundType(SoundType.WOOD)
    case Material.GLASS => setSoundType(SoundType.GLASS)
    case Material.GROUND => setSoundType(SoundType.GROUND)
    case _ =>
  }

  override def createNewTileEntity(world: World, meta: Int): TileMultiShadow = new TileMultiShadow

  override def getRenderType(state: IBlockState): EnumBlockRenderType = ShadowBlockRenderer.RENDER_TYPE

  override def canRenderInLayer(state: IBlockState, layer: BlockRenderLayer): Boolean = {
    if (blockMaterial == Material.GLASS) {
      return layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT
    } else {
      return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.SOLID
    }
  }


  override def updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random): Unit = {
    super.updateTick(world, pos, state, rand)
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.updateTick(world, pos, shadowBlock.get.getBlockState(), rand)
    }
  }

  override def getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos): AxisAlignedBB = {
    val shadowBlock = getShadowBlock(worldIn, pos)
    if (shadowBlock.isDefined) {
      return shadowBlock.get.block.getCollisionBoundingBox(blockState, worldIn, pos)
    }
    super.getCollisionBoundingBox(blockState, worldIn, pos)
  }


  override def isFireSource(world: World, pos: BlockPos, side: EnumFacing): Boolean = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.isFireSource(world, pos, side)
    } else {
      false
    }
  }

  override def isFullCube(state: IBlockState): Boolean = blockMaterial != Material.GLASS && blockMaterial != Material.AIR

  override def isAir(state: IBlockState, world: IBlockAccess, pos: BlockPos): Boolean = blockMaterial == Material.AIR

  override def isOpaqueCube(state: IBlockState): Boolean = blockMaterial != Material.GLASS && blockMaterial != Material.AIR

  override def canCollideCheck(state: IBlockState, hitIfLiquid: Boolean): Boolean = blockMaterial != Material.AIR

  override def shouldSideBeRendered(blockState: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean = {
    if (blockMaterial == Material.GLASS) {
      val state = world.getBlockState(pos.offset(side))
      if (state.getBlock == this) {
        return false
      } else {
        return true
      }
    }
    blockMaterial != Material.AIR
  }


  override def addDestroyEffects(world: World, pos: BlockPos, manager: ParticleManager): Boolean = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.addDestroyEffects(world, pos, manager)
    } else {
      super.addDestroyEffects(world, pos, manager)
    }
  }

  override def addHitEffects(state: IBlockState, worldObj: World, target: RayTraceResult, manager: ParticleManager): Boolean = {
    val shadowBlock = getShadowBlock(worldObj, target.getBlockPos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.addHitEffects(shadowBlock.get.getBlockState(), worldObj, target, manager)
    } else {
      super.addHitEffects(state, worldObj, target, manager)
    }
  }

  override def getPickBlock(state: IBlockState, target: RayTraceResult, world: World, pos: BlockPos, player: EntityPlayer): ItemStack = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getPickBlock(shadowBlock.get.getBlockState(), target, world, pos, player)
    } else {
      super.getPickBlock(state, target, world, pos, player)
    }
  }

  override def getDrops(world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int): util.List[ItemStack] = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getDrops(world, pos, shadowBlock.get.getBlockState(), fortune)
    } else {
      super.getDrops(world, pos, state, fortune)
    }
  }

  def getShadowBlock(world: IBlockAccess, pos: BlockPos): Option[BlockDef] = {
    world.getTileEntity(pos) match {
      case tile: TileMultiShadow => tile.getCurrBlockDef
      case _ => None
    }
  }

  override def getBlockHardness(blockState: IBlockState, worldIn: World, pos: BlockPos): Float = {
    val shadowBlock = getShadowBlock(worldIn, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getBlockHardness(shadowBlock.get.getBlockState(), worldIn, pos)
    } else {
      super.getBlockHardness(blockState, worldIn, pos)
    }
  }

  override def getExplosionResistance(world: World, pos: BlockPos, exploder: Entity, explosion: Explosion): Float = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.getExplosionResistance(world, pos, exploder, explosion)
    } else {
      super.getExplosionResistance(world, pos, exploder, explosion)
    }
  }

  override def onNeighborChange(world: IBlockAccess, pos: BlockPos, neighbor: BlockPos): Unit = {
    val shadowBlock = getShadowBlock(world, pos)
    if (shadowBlock.isDefined) {
      shadowBlock.get.block.onNeighborChange(world, pos, neighbor)
    } else {
      super.onNeighborChange(world, pos, neighbor)
    }
  }

  override def hasTileEntity(state: IBlockState): Boolean = true

  override def removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean = if (willHarvest) true else super.removedByPlayer(state, world, pos, player, willHarvest)

  override def harvestBlock(worldIn: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, te: TileEntity, stack: ItemStack): Unit = {
    super.harvestBlock(worldIn, player, pos, state, te, stack)
    worldIn.setBlockToAir(pos)
  }

  override def registerTileEntities(): Unit = GameRegistry.registerTileEntity(classOf[TileMultiShadow], "tile." + ModPrefs.MODID + ".multi_block_shadow_block")
}
