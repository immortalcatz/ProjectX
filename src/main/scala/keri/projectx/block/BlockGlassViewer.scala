package keri.projectx.block

import keri.ninetaillib.lib.render.connected.ICTMBlock
import keri.ninetaillib.lib.texture.IIconRegister
import keri.ninetaillib.lib.util.ClientUtils
import keri.projectx.util.ModPrefs
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumFacing}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class BlockGlassViewer extends BlockProjectX("glass_viewer", Material.GLASS) with ICTMBlock {
  setHardness(1.1F)

  @SideOnly(Side.CLIENT)
  var texture: Array[TextureAtlasSprite] = _

  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IIconRegister): Unit = {
    texture = ClientUtils.registerConnectedTexture(register, ModPrefs.MODID + ":blocks/glass_viewer/glass_viewer")
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, side: EnumFacing): TextureAtlasSprite = texture(0)

  @SideOnly(Side.CLIENT)
  override def getConnectedTexture(world: IBlockAccess, pos: BlockPos, side: EnumFacing): Array[TextureAtlasSprite] = texture

  @SideOnly(Side.CLIENT)
  override def canTextureConnect(world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean = true

  @SideOnly(Side.CLIENT)
  override def shouldSideBeRendered(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean = {
    world.getBlockState(pos.offset(side)).getBlock != this
  }

  @SideOnly(Side.CLIENT)
  override def canRenderInLayer(state: IBlockState, layer: BlockRenderLayer): Boolean = layer == BlockRenderLayer.TRANSLUCENT

  @SideOnly(Side.CLIENT)
  override def isOpaqueCube(state: IBlockState): Boolean = false

  @SideOnly(Side.CLIENT)
  override def isFullCube(state: IBlockState): Boolean = false

}
