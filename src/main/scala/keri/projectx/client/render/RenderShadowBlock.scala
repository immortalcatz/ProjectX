package keri.projectx.client.render

import codechicken.lib.render.{CCModel, CCRenderState}
import codechicken.lib.vec.uv.IconTransformation
import codechicken.lib.vec.{Cuboid6, Translation, Vector3}
import keri.ninetaillib.lib.render.{IBlockRenderingHandler, RenderingRegistry}
import keri.ninetaillib.lib.util.ModelUtils
import keri.projectx.tile.{BlockDef, TileEntityMultiShadow}
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.{BlockRendererDispatcher, VertexBuffer}
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType}
import net.minecraft.world.IBlockAccess

object RenderShadowBlock extends IBlockRenderingHandler {

  private final val DAMAGE_MODEL: CCModel = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D))
  val RENDER_TYPE: EnumBlockRenderType = RenderingRegistry.getNextAvailableType
  RenderingRegistry.registerRenderingHandler(RenderShadowBlock.this)

  override def renderWorld(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, layer: BlockRenderLayer): Boolean = {
    val brd: BlockRendererDispatcher = Minecraft.getMinecraft.getBlockRendererDispatcher

    world.getTileEntity(pos) match {
      case tileMultiShadow: TileEntityMultiShadow => {
        var blockDef: Option[BlockDef] = tileMultiShadow.getCurrBlockDef

        if (blockDef.isDefined) {
          brd.renderBlock(blockDef.get.getBlockState, pos, world, buffer)
        }

        false
      }
      case _ => false
    }
  }

  override def renderDamage(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, texture: TextureAtlasSprite): Unit = {
    val renderState: CCRenderState = CCRenderState.instance
    var translation: Translation = new Translation(Vector3.fromBlockPos(pos))
    renderState.reset
    renderState.bind(buffer)
    DAMAGE_MODEL.apply(translation).render(renderState, new IconTransformation(texture))
  }

  override def renderInventory(stack: ItemStack, buffer: VertexBuffer): Unit = {}

  override def getRenderType: EnumBlockRenderType = RENDER_TYPE

}
