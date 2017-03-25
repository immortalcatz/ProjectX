package keri.projectx.block;

import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.client.render.RenderSimpleGlow;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockCustomRenderer<T extends TileEntity> extends BlockProjectX<T> implements IAnimationSideHandler {

    public BlockCustomRenderer(String blockName, Material material, MapColor mapColor) {
        super(blockName, material, mapColor);
    }

    public BlockCustomRenderer(String blockName, Material material) {
        super(blockName, material);
    }

    @Override
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderSimpleGlow();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

}
