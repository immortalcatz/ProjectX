package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.client.ProjectXModels;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderTankValve implements IBlockRenderingHandler {

    private static CCModel[] model = ProjectXModels.getModel("tank_valve").getModel();

    @Override
    public List<BakedQuad> renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        //CCModel[] model = ProjectXModels.getModel("tank_valve").getModel();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);

        for(int i = 0; i < model.length; i++){
            model[i].render(renderState, new IconTransformation(textureAnimation));
        }

        return null;
    }

    @Override
    public List<BakedQuad> renderItem(CCRenderState renderState, ItemStack stack, long rand){
        //CCModel[] model = ProjectXModels.getModel("tank_valve").getModel();
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);

        for(int i = 0; i < model.length; i++){
            model[i].render(renderState, new IconTransformation(textureAnimation));
        }

        return null;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return TextureUtils.getTexture("minecraft:blocks/stone");
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return true;
    }

}
