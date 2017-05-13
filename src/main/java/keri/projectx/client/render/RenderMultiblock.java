package keri.projectx.client.render;

import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.projectx.tile.TileEntityMultiblock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RenderMultiblock implements IBlockRenderingHandler {

    @Override
    public boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        TileEntityMultiblock tile = (TileEntityMultiblock)world.getTileEntity(pos);

        if(tile != null){
            if(!tile.getIsMaster() && !tile.getIsSlave()){
                return this.renderDefaultBlock(renderState, world, pos, layer);
            }
            else{
                return tile.getMultiblock().renderMultiblock(renderState, world, pos, layer);
            }
        }

        return false;
    }

    @Override
    public void renderBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture) {
        TileEntityMultiblock tile = (TileEntityMultiblock)world.getTileEntity(pos);

        if(!tile.getIsMaster() && !tile.getIsSlave()){
            this.renderDefaultBlockDamage(renderState, world, pos, texture);
        }
    }

    public abstract boolean renderDefaultBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer);

    public abstract void renderDefaultBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture);

}
