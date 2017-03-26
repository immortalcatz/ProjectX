package keri.projectx.block;

import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.projectx.ProjectX;
import keri.projectx.client.render.RenderQuartzCrystal;
import keri.projectx.tile.TileEntityQuartzCrystal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockQuartzCrystal extends BlockProjectX<TileEntityQuartzCrystal> {

    public BlockQuartzCrystal() {
        super("quartz_crystal", Material.GLASS);
        this.setHardness(1.3F);
    }

    @Override
    public TileEntityQuartzCrystal createNewTileEntity(World world, int meta) {
        return new TileEntityQuartzCrystal();
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
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderingHandler getRenderingHandler() {
        return new RenderQuartzCrystal();
    }

}
