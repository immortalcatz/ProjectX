package keri.projectx.block;

import keri.projectx.ProjectX;
import keri.projectx.client.render.IAnimationHandler;
import keri.projectx.client.render.RenderQuartzCrystal;
import keri.projectx.tile.TileEntityQuartzCrystal;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockQuartzCrystal extends BlockProjectX<TileEntityQuartzCrystal> implements IAnimationHandler {

    public BlockQuartzCrystal() {
        super("quartz_crystal", Material.GLASS);
        this.setHardness(0.8F);
    }

    @Nullable
    @Override
    public TileEntityQuartzCrystal createNewTileEntity(World world, int meta) {
        return new TileEntityQuartzCrystal();
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityQuartzCrystal.class, ModPrefs.MODID + ".tileentity.quartz_crystal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(ItemStack stack, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(IBlockAccess world, BlockPos pos, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(ItemStack stack, int side) {
        return 0xFFFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return 0xFFFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderQuartzCrystal.RENDER_TYPE;
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

}
