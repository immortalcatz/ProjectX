package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.ModelUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockBeveled implements IBlockRenderingHandler {

    public static final RenderBlockBeveled INSTANCE = new RenderBlockBeveled();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(1D, 1D, 1D, 15D, 15D, 15D),
                new Cuboid6(0D, 0D, 0D, 2D, 16D, 2D),
                new Cuboid6(14D, 0D, 0D, 16D, 16D, 2D),
                new Cuboid6(14D, 0D, 14D, 16D, 16D, 16D),
                new Cuboid6(0D, 0D, 14D, 2D, 16D, 16D),
                new Cuboid6(2D, 0D, 0D, 14D, 2D, 2D),
                new Cuboid6(2D, 0D, 14D, 14D, 2D, 16D),
                new Cuboid6(2D, 14D, 14D, 14D, 16D, 16D),
                new Cuboid6(2D, 14D, 0D, 14D, 16D, 2D),
                new Cuboid6(0D, 0D, 2D, 2D, 2D, 14D),
                new Cuboid6(0D, 14D, 2D, 2D, 16D, 14D),
                new Cuboid6(14D, 14D, 2D, 16D, 16D, 14D),
                new Cuboid6(14D, 0D, 2D, 16D, 2D, 14D),
                new Cuboid6(5D, 5D, 0D, 11D, 11D, 1D),
                new Cuboid6(5D, 11D, 0D, 11D, 13D, 1D),
                new Cuboid6(5D, 3D, 0D, 11D, 5D, 1D),
                new Cuboid6(3D, 5D, 0D, 5D, 11D, 1D),
                new Cuboid6(11D, 5D, 0D, 13D, 11D, 1D),
                new Cuboid6(5D, 5D, 15D, 11D, 11D, 16D),
                new Cuboid6(5D, 11D, 15D, 11D, 13D, 16D),
                new Cuboid6(5D, 3D, 15D, 11D, 5D, 16D),
                new Cuboid6(3D, 5D, 15D, 5D, 11D, 16D),
                new Cuboid6(11D, 5D, 15D, 13D, 11D, 16D),
                new Cuboid6(0D, 5D, 5D, 1D, 11D, 11D),
                new Cuboid6(0D, 5D, 3D, 1D, 11D, 5D),
                new Cuboid6(0D, 5D, 11D, 1D, 11D, 13D),
                new Cuboid6(0D, 11D, 5D, 1D, 13D, 11D),
                new Cuboid6(0D, 3D, 5D, 1D, 5D, 11D),
                new Cuboid6(15D, 5D, 5D, 16D, 11D, 11D),
                new Cuboid6(15D, 5D, 3D, 16D, 11D, 5D),
                new Cuboid6(15D, 5D, 11D, 16D, 11D, 13D),
                new Cuboid6(15D, 11D, 5D, 16D, 13D, 11D),
                new Cuboid6(15D, 3D, 5D, 16D, 5D, 11D),
                new Cuboid6(5D, 0D, 5D, 11D, 1D, 11D),
                new Cuboid6(5D, 0D, 3D, 11D, 1D, 5D),
                new Cuboid6(5D, 0D, 11D, 11D, 1D, 13D),
                new Cuboid6(3D, 0D, 5D, 5D, 1D, 11D),
                new Cuboid6(11D, 0D, 5D, 13D, 1D, 11D),
                new Cuboid6(5D, 15D, 5D, 11D, 16D, 11D),
                new Cuboid6(3D, 15D, 5D, 5D, 16D, 11D),
                new Cuboid6(11D, 15D, 5D, 13D, 16D, 11D),
                new Cuboid6(5D, 15D, 3D, 11D, 16D, 5D),
                new Cuboid6(5D, 15D, 11D, 11D, 16D, 13D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
        ModelUtils.rotate(BLOCK_MODEL[14], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 11D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[15], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 5D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[16], 45D, new Vector3(0D, 1D, 0D), new Vector3(5D, 8D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[17], -45D, new Vector3(0D, 1D, 0D), new Vector3(11D, 8D, 0D));
        ModelUtils.rotate(BLOCK_MODEL[19], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 11D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[20], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 5D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[21], -45D, new Vector3(0D, 1D, 0D), new Vector3(5D, 8D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[22], 45D, new Vector3(0D, 1D, 0D), new Vector3(11D, 8D, 16D));
        ModelUtils.rotate(BLOCK_MODEL[24], -45D, new Vector3(0D, 1D, 0D), new Vector3(0D, 8D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[25], 45D, new Vector3(0D, 1D, 0D), new Vector3(0D, 8D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[26], -45D, new Vector3(0D, 0D, 1D), new Vector3(0D, 11D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[27], 45D, new Vector3(0D, 0D, 1D), new Vector3(0D, 5D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[29], 45D, new Vector3(0D, 1D, 0D), new Vector3(16D, 8D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[30], -45D, new Vector3(0D, 1D, 0D), new Vector3(16D, 8D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[31], 45D, new Vector3(0D, 0D, 1D), new Vector3(16D, 11D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[32], -45D, new Vector3(0D, 0D, 1D), new Vector3(16D, 5D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[34], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 0D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[35], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 0D, 11D));
        ModelUtils.rotate(BLOCK_MODEL[36], -45D, new Vector3(0D, 0D, 1D), new Vector3(5D, 0D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[37], 45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 0D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[39], 45D, new Vector3(0D, 0D, 1D), new Vector3(5D, 16D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[40], -45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 16D, 8D));
        ModelUtils.rotate(BLOCK_MODEL[41], -45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 16D, 5D));
        ModelUtils.rotate(BLOCK_MODEL[42], 45D, new Vector3(1D, 0D, 0D), new Vector3(8D, 16D, 11D));
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, BlockRenderLayer layer) {
        return false;
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer, TextureAtlasSprite texture) {

    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {

    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
