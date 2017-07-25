package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
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
public class RenderEngineeringTable implements IBlockRenderingHandler {

    public static final RenderEngineeringTable INSTANCE = new RenderEngineeringTable();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] BOUNDS = new Cuboid6[]{
                new Cuboid6(0D, 0D, 0D, 16D, 8D, 16D),
                new Cuboid6(0D, 15D, 0D, 16D, 16D, 16D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
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
