package keri.projectx.block.machine;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.block.BlockProjectX;
import keri.projectx.client.render.RenderTank;
import keri.projectx.tile.TileEntityTank;
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
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockTank extends BlockProjectX<TileEntityTank> {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockTank() {
        super("tank", Material.IRON);
        this.setHardness(1.3F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityTank.class, "tile." + ModPrefs.MODID + ".tank");
    }

    @Nullable
    @Override
    public TileEntityTank createNewTileEntity(World world, int meta) {
        return new TileEntityTank();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityTank tile = (TileEntityTank)world.getTileEntity(pos);

        if(tile != null){
            ItemStack heldItem = player.getHeldItemMainhand();

            if(heldItem != null && !heldItem.isEmpty()){
                if(FluidUtil.getFluidContained(heldItem) != null){
                    return FluidUtil.interactWithFluidHandler(heldItem, tile, player).isSuccess();
                }
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[2];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/tank_frame");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/tank_glass");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture[side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderTank.RENDER_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
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
