package keri.projectx.block;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.ProjectX;
import keri.projectx.client.render.IAnimationHandler;
import keri.projectx.client.render.RenderSimpleGlow;
import keri.projectx.network.ProjectXGuiHandler;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.util.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockFabricator extends BlockProjectX<TileEntityFabricator> implements IAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockFabricator() {
        super("fabricator", Material.IRON);
        this.setHardness(1.4F);
    }

    @Nullable
    @Override
    public TileEntityFabricator createNewTileEntity(World world, int meta) {
        return new TileEntityFabricator();
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFabricator.class, ModPrefs.MODID + ".tileentity.fabricator");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.GUIID_FABRICATOR, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/fabricator/fabricator_side");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/fabricator/fabricator_top");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/fabricator/fabricator_bottom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(side){
            case 0: return this.texture[2];
            case 1: return this.texture[1];
            case 2: return this.texture[0];
            case 3: return this.texture[0];
            case 4: return this.texture[0];
            case 5: return this.texture[0];
        }

        return null;
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
        return EnumXycroniumColor.BLUE.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.BLUE.getColor().rgba();
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
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderSimpleGlow.RENDER_TYPE;
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
