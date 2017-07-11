//package keri.projectx.machine.block
//
//import java.util.Random
//
//import codechicken.lib.fluid.FluidUtils
//import com.projectxy
//import com.projectxy.api.IFluidSource
//import keri.projectx.machine.block.BlockXyBasicMachine._
//import com.projectxy.world.block.MetaBlock
//import com.projectxy.world.block.glow.ITextureRegisteringAnimationHandler
//import com.projectxy.world.{ProjectXYProxy, ProjectXYWorld}
//import net.minecraft.block.Block
//import net.minecraft.block.material.{Material, MaterialLiquid}
//import net.minecraft.block.state.IBlockState
//import net.minecraft.client.renderer.texture.{TextureAtlasSprite, TextureMap}
//import net.minecraft.creativetab.CreativeTabs
//import net.minecraft.init.Blocks
//import net.minecraft.item.ItemStack
//import net.minecraft.util.math.BlockPos
//import net.minecraft.util.{EnumFacing, NonNullList, ResourceLocation}
//import net.minecraft.world.{IBlockAccess, World}
//import net.minecraftforge.common.IPlantable
//import net.minecraftforge.fluids.FluidStack
//
//class BlockXyBasicMachine extends MetaBlock(Material.ROCK) with ITextureRegisteringAnimationHandler with IFluidSource {
//  setBlockName("blockXyBasicMachine")
//  setTickRandomly(true)
//  override val textureName: String = null
//  val icons = Array.fill[TextureAtlasSprite](4)(null)
//  var upIcon: TextureAtlasSprite = null
//
//  override def registerIcons(iconRegister: TextureMap) {
//    def registerMachineIcon(name: String): TextureAtlasSprite = iconRegister.registerSprite(new ResourceLocation(s"${ProjectXYWorld.MOD_ID}:overlay/$name"))
//
//    icons(XY_WATER_META) = registerMachineIcon("water")
//    icons(XY_VOID_META) = registerMachineIcon("void")
//    icons(XY_ICE_META) = registerMachineIcon("ice")
//    icons(XY_SOIL_META) = registerMachineIcon("soil")
//    upIcon = registerMachineIcon("machine_top")
//    texture = ProjectXYProxy.animationTexture.texture
//  }
//
//  override def neighborChanged(state: IBlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos): Unit = {
//    searchForAll(worldIn, pos)
//  }
//
//  def searchForAll(world: World, pos: BlockPos): Unit = {
//    getMetaFromState(world.getBlockState(pos)) match {
//      case XY_WATER_META =>
//        //Water
//        //Not the best... But it works
//        searchForBlockAndReplaceWith(world, pos, Blocks.FLOWING_LAVA, Blocks.OBSIDIAN, Some(0))
//        searchForBlockAndReplaceWith(world, pos, Blocks.LAVA, Blocks.OBSIDIAN, Some(0))
//        searchForBlockAndReplaceWith(world, pos, Blocks.FLOWING_LAVA, Blocks.COBBLESTONE, None)
//        searchForBlockAndReplaceWith(world, pos, Blocks.LAVA, Blocks.COBBLESTONE, None)
//      case XY_VOID_META =>
//        //Void
//        searchForLiquid(world, pos)
//      case XY_ICE_META =>
//        //Ice
//        searchForBlockAndReplaceWith(world, pos, Blocks.WATER, Blocks.ICE, None)
//        searchForBlockAndReplaceWith(world, pos, Blocks.FLOWING_WATER, Blocks.ICE, None)
//      case _ =>
//    }
//  }
//
//  def searchForLiquid(world: World, pos: BlockPos) {
//    for (direction <- EnumFacing.values()) {
//      val offsetBlock = world.getBlockState(pos.offset(direction))
//      if (offsetBlock.getMaterial.isInstanceOf[MaterialLiquid])
//        world.setBlockToAir(pos.offset(direction))
//    }
//  }
//
//  def searchForBlockAndReplaceWith(world: World, pos: BlockPos, block: Block, replaceWith: Block, meta: Option[Int]) {
//    for (direction <- EnumFacing.values()) {
//      val offsetBlock = world.getBlockState(pos.offset(direction))
//      if (offsetBlock.getBlock == block)
//        if (meta.isDefined) {
//          if (offsetBlock.getBlock.getMetaFromState(offsetBlock) == meta.get) {
//            world.setBlockState(pos.offset(direction), replaceWith.getDefaultState)
//          }
//        } else {
//          world.setBlockState(pos.offset(direction), replaceWith.getDefaultState)
//        }
//    }
//  }
//
//  override def onBlockAdded(world: World, pos: BlockPos, state: IBlockState): Unit = {
//    super.onBlockAdded(world, pos, state)
//    searchForAll(world, pos)
//  }
//
//  override def updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random): Unit = {
//    if (getMetaFromState(state) == XY_SOIL_META)
//      world.getBlockState(pos.up()).getBlock match {
//        case blockBasicMachine: BlockXyBasicMachine =>
//          if (getMetaFromState(world.getBlockState(pos.up())) == XY_SOIL_META) {
//            blockBasicMachine.updateTick(world, pos.up(), world.getBlockState(pos.up()), rand)
//          }
//        case plantable: IPlantable =>
//          plantable.updateTick(world, pos.up(), world.getBlockState(pos.up()), rand)
//        case _ =>
//      }
//    super.updateTick(world, pos, state, rand)
//  }
//
//  override def getSubBlocks(tab: CreativeTabs, list: NonNullList[ItemStack]): Unit = {
//    for (meta <- 0 to 3) {
//      list.add(new ItemStack(this, 1, meta))
//    }
//  }
//
//  override def canSustainPlant(state: IBlockState, world: IBlockAccess, pos: BlockPos, direction: EnumFacing, plantable: IPlantable): Boolean = getMetaFromState(state) == XY_SOIL_META
//
//  override def isFertile(world: World, pos: BlockPos): Boolean = getMetaFromState(world.getBlockState(pos)) == XY_SOIL_META
//
//  override def getAnimationColor(world: IBlockAccess, state: IBlockState, pos: BlockPos, side: Int): Int = projectxy.world.WorldBlocks.xyColors(getMetaFromState(state)).rgba()
//
//  override def getAnimationColor(stack: ItemStack, side: Int): Int = projectxy.world.WorldBlocks.xyColors(stack.getMetadata).rgba()
//
//  override def getFluid(world: IBlockAccess, pos: BlockPos, state: IBlockState): FluidStack = if (getMetaFromState(state) == XY_WATER_META) new FluidStack(FluidUtils.water, 50) else FluidUtils.emptyFluid()
//}
//
//object BlockXyBasicMachine {
//  val XY_SOIL_META = 3
//  val XY_ICE_META = 2
//  val XY_VOID_META = 1
//  val XY_WATER_META = 0
//}
