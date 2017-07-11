package keri.projectx.client.render;

import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.projectx.init.ProjectXContent;
import keri.projectx.tile.BlockDef;
import keri.projectx.tile.TileMultiShadow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import scala.Option;

public class ShadowBlockRenderer implements IBlockRenderingHandler {
    public static EnumBlockRenderType RENDER_TYPE;

    static {
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new ShadowBlockRenderer());
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileMultiShadow)) {
            return false;
        }
        Option<BlockDef> blockDef = ((TileMultiShadow) tile).getCurrBlockDef();
        if (!blockDef.isDefined())
            return false;

        IBlockState shadow_state = blockDef.get().block().getStateFromMeta(ProjectXContent.SHADOW_WOOD.getMetaFromState(world.getBlockState(pos)));
        return Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(shadow_state, pos, world, buffer);
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {

    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {

    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }
}
