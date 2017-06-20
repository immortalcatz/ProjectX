package keri.projectx.block.machine;

import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.block.BlockProjectX;
import keri.projectx.client.render.RenderHydrogenicSeperator;
import keri.projectx.network.ProjectXGuiHandler;
import keri.projectx.tile.TileEntityHydrogenicSeperator;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockHydrogenicSeperator extends BlockProjectX<TileEntityHydrogenicSeperator> {

    public BlockHydrogenicSeperator() {
        super("hydrogenic_seperator", Material.IRON);
        this.setHardness(1.4F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityHydrogenicSeperator.class, "tile." + ModPrefs.MODID + ".hydrogenic_seperator");
    }

    @Nullable
    @Override
    public TileEntityHydrogenicSeperator createNewTileEntity(World world, int meta) {
        return new TileEntityHydrogenicSeperator();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int orientation = MathHelper.floor(placer.rotationYaw * 4D / 360D + 0.5D) & 3;
        BlockAccessUtils.setBlockMetadata(world, pos, orientation, 2);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            player.openGui(ProjectX.INSTANCE, ProjectXGuiHandler.GUIID_HYDROGENIC_SEPERATOR, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderHydrogenicSeperator.RENDER_TYPE;
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
