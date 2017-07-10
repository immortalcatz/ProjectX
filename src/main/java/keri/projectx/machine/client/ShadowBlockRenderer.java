package keri.projectx.machine.client;

import codechicken.lib.render.block.ICCBlockRenderer;
import keri.projectx.machine.MechBlocks$;
import keri.projectx.machine.multiblock.tile.BlockDef;
import keri.projectx.machine.multiblock.tile.TileMultiShadow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scala.Option;

public class ShadowBlockRenderer implements ICCBlockRenderer {
    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, BufferBuilder buffer) {
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, BufferBuilder buffer) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileMultiShadow)) {
            return false;
        }
        Option<BlockDef> blockDef = ((TileMultiShadow) tile).getCurrBlockDef();
        if (!blockDef.isDefined())
            return false;

        IBlockState shadow_state = blockDef.get().block().getStateFromMeta(MechBlocks$.MODULE$.blockShadowWood().getMetaFromState(state));
        return Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(shadow_state, pos, world, buffer);
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {

    }

    @Override
    public void registerTextures(TextureMap map) {

    }
}
