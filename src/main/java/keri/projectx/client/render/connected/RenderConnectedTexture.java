/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.ModelUtils;
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
public class RenderConnectedTexture implements IBlockRenderingHandler {

    public static final RenderConnectedTexture INSTANCE = new RenderConnectedTexture();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel DEFAULT_MODEL;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        DEFAULT_MODEL = ModelUtils.getNormalized(new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D));
        Cuboid6[] BOUNDS = new Cuboid6[]{
                //bottom, top left
                new Cuboid6(0D, 0D, 0D, 8D, 8D, 8D),
                //bottom, top right
                new Cuboid6(8D, 0D, 0D, 16D, 8D, 8D),
                //bottom, bottom left
                new Cuboid6(0D, 0D, 8D, 8D, 8D, 16D),
                //bottom, bottom right
                new Cuboid6(8D, 0D, 8D, 16D, 8D, 16D),
                //top, top left
                new Cuboid6(0D, 8D, 0D, 8D, 16D, 8D),
                //top, top right
                new Cuboid6(8D, 8D, 0D, 16D, 16D, 8D),
                //top, bottom left
                new Cuboid6(0D, 8D, 8D, 8D, 16D, 16D),
                //top, bottom right
                new Cuboid6(8D, 8D, 8D, 16D, 16D, 16D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(BOUNDS);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
        return false;
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.bind(buffer);
        CCModel model = DEFAULT_MODEL.copy();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        model.render(renderState, new IconTransformation(texture));
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {

    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

    public void renderConnectedTexture(IBlockAccess world, BlockPos pos, CCRenderState renderState){
        ICTMBlock block = (ICTMBlock)world.getBlockState(pos).getBlock();
        TextureHandlerCTM textureHandler = block.getTextureHandler(world, pos);
    }

}
