package keri.projectx.client.render;

import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
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
public class RenderHydrogenicSeperator implements IBlockRenderingHandler {

    public static final RenderHydrogenicSeperator INSTANCE = new RenderHydrogenicSeperator();
    public static EnumBlockRenderType RENDER_TYPE;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
        return false;
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
