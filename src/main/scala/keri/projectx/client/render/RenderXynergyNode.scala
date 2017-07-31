package keri.projectx.client.render

import codechicken.lib.render.buffer.BakingVertexBuffer
import codechicken.lib.render.{CCModel, CCRenderState}
import codechicken.lib.vec.uv.{IconTransformation, MultiIconTransformation}
import codechicken.lib.vec.{Cuboid6, Translation, Vector3}
import keri.ninetaillib.lib.render.{IBlockRenderingHandler, RenderingRegistry}
import keri.ninetaillib.lib.texture.IIconBlock
import keri.ninetaillib.lib.util.{ModelUtils, RenderUtils}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{GlStateManager, Tessellator, VertexBuffer}
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType, EnumFacing}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT)
object RenderXynergyNode extends IBlockRenderingHandler {

  private final val BLOCK_MODEL: Array[CCModel] = ModelUtils.getNormalized(Array(
    new Cuboid6(3D, 0D, 3D, 7D, 1D, 7D),
    new Cuboid6(9D, 0D, 3D, 13D, 1D, 7D),
    new Cuboid6(9D, 0D, 9D, 13D, 1D, 13D),
    new Cuboid6(3D, 0D, 9D, 7D, 1D, 13D),
    new Cuboid6(3D, 1D, 6D, 5D, 2D, 10D),
    new Cuboid6(11D, 1D, 6D, 13D, 2D, 10D),
    new Cuboid6(6D, 1D, 11D, 10D, 2D, 13D),
    new Cuboid6(6D, 1D, 3D, 10D, 2D, 5D),
    new Cuboid6(3.5D, 1D, 2.5D, 5.5D, 2D, 6.5D),
    new Cuboid6(10.5D, 1D, 2.5D, 12.5D, 2D, 6.5D),
    new Cuboid6(3.25D, 1D, 10D, 5.25D, 2D, 14D),
    new Cuboid6(10.75D, 1D, 10D, 12.75D, 2D, 14D)
  ))
  ModelUtils.rotate(BLOCK_MODEL(8), -45D, new Vector3(0D, 1D, 0D), new Vector3(4D, 8D, 6D))
  ModelUtils.rotate(BLOCK_MODEL(9), 45D, new Vector3(0D, 1D, 0D), new Vector3(12D, 8D, 6D))
  ModelUtils.rotate(BLOCK_MODEL(10), 45D, new Vector3(0D, 1D, 0D), new Vector3(3D, 8D, 10D))
  ModelUtils.rotate(BLOCK_MODEL(11), -45D, new Vector3(0D, 1D, 0D), new Vector3(13D, 8D, 10D))
  final val RENDER_TYPE: EnumBlockRenderType = RenderingRegistry.getNextAvailableType
  RenderingRegistry.registerRenderingHandler(RenderXynergyNode.this)

  override def renderWorld(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, layer: BlockRenderLayer): Boolean = {
    val iconProvider: IIconBlock = state.getBlock.asInstanceOf[IIconBlock]
    val textureTop: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.DOWN)
    val textureBottom: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.UP)
    val textureSide: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.NORTH)
    val meta: Int = state.getBlock.getMetaFromState(state)
    val renderState: CCRenderState = CCRenderState.instance
    val parentBuffer: BakingVertexBuffer = BakingVertexBuffer.create
    parentBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)
    renderState.reset()
    renderState.bind(parentBuffer)

    for (part <- 0 until BLOCK_MODEL.length) {
      val modelPart: CCModel = BLOCK_MODEL(part).copy

      EnumFacing.getFront(meta) match {
        case EnumFacing.DOWN => ModelUtils.rotate(modelPart, 180D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.NORTH => ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.EAST => {
          ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
          ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D))
        }
        case EnumFacing.SOUTH => ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.WEST => {
          ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
          ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D))
        }
        case _ => false
      }

      part match {
        case x if x < 4 => modelPart.render(renderState, new MultiIconTransformation(textureBottom, textureBottom, textureSide, textureSide, textureSide, textureSide))
        case x if x > 3 && x < 8 => modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide))
        case _ => {
          val zOffset: Double = 0.0004D
          modelPart.zOffset(new Cuboid6(zOffset, zOffset, zOffset, zOffset, zOffset, zOffset))
          modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide))
        }
      }
    }

    parentBuffer.finishDrawing
    RenderUtils.renderQuads(buffer, world, pos, parentBuffer.bake())
  }

  override def renderDamage(world: IBlockAccess, pos: BlockPos, state: IBlockState, buffer: VertexBuffer, texture: TextureAtlasSprite): Unit = {
    val renderState: CCRenderState = CCRenderState.instance
    val translation: Translation = new Translation(Vector3.fromBlockPos(pos))
    val meta: Int = state.getBlock.getMetaFromState(state)
    renderState.reset
    renderState.bind(buffer)

    for (part <- 0 until BLOCK_MODEL.length) {
      var modelPart: CCModel = BLOCK_MODEL(part).copy

      EnumFacing.getFront(meta) match {
        case EnumFacing.DOWN => ModelUtils.rotate(modelPart, 180D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.NORTH => ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.EAST => {
          ModelUtils.rotate(modelPart, -90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
          ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D))
        }
        case EnumFacing.SOUTH => ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
        case EnumFacing.WEST => {
          ModelUtils.rotate(modelPart, 90D, new Vector3(1D, 0D, 0D), new Vector3(0D, 8D, 8D))
          ModelUtils.rotate(modelPart, -90D, new Vector3(0D, 1D, 0D), new Vector3(8D, 0D, 8D))
        }
        case _ => false
      }

      modelPart.apply(new Translation(Vector3.fromBlockPos(pos)))
      modelPart.render(renderState, new IconTransformation(texture))
    }
  }

  override def renderInventory(stack: ItemStack, buffer: VertexBuffer): Unit = {
    val iconProvider: IIconBlock = Block.getBlockFromItem(stack.getItem).asInstanceOf[IIconBlock]
    val textureTop: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.DOWN)
    val textureBottom: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.UP)
    val textureSide: TextureAtlasSprite = iconProvider.getIcon(0, EnumFacing.NORTH)
    val renderState: CCRenderState = CCRenderState.instance()
    Tessellator.getInstance.draw
    GlStateManager.pushMatrix
    GlStateManager.enableLighting
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM)
    renderState.reset
    renderState.bind(buffer)

    for (part <- 0 until BLOCK_MODEL.length) {
      var modelPart: CCModel = BLOCK_MODEL(part).copy

      part match {
        case x if x < 4 => modelPart.render(renderState, new MultiIconTransformation(textureBottom, textureBottom, textureSide, textureSide, textureSide, textureSide))
        case x if x > 3 && x < 8 => modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide))
        case _ => {
          val zOffset: Double = 0.0004D
          modelPart.zOffset(new Cuboid6(zOffset, zOffset, zOffset, zOffset, zOffset, zOffset))
          modelPart.render(renderState, new MultiIconTransformation(textureTop, textureTop, textureSide, textureSide, textureSide, textureSide))
        }
      }
    }

    Tessellator.getInstance.draw
    GlStateManager.popMatrix
    buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM))
  }

  override def getRenderType: EnumBlockRenderType = RENDER_TYPE

}
