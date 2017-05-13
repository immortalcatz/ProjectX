package keri.projectx.block.decorative;

import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.block.base.BlockProjectX;
import keri.projectx.client.render.RenderGlassViewer;
import keri.projectx.tile.TileEntityGlassViewer;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlassViewer extends BlockProjectX {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public BlockGlassViewer() {
        super("glass_viewer", Material.GLASS);
        this.setHardness(1.3F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityGlassViewer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegistrar registrar) {
        this.texture = registrar.registerIcon(ModPrefs.MODID + ":blocks/glass_viewer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return !world.getBlockState(pos.offset(side)).equals(this.getDefaultState());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderGlassViewer();
    }

}
