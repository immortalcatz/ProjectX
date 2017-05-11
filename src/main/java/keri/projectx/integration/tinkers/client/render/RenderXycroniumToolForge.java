package keri.projectx.integration.tinkers.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.MultiIconTransformation;
import keri.ninetaillib.render.fms.FMSModel;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.client.ProjectXModels;
import keri.projectx.client.render.IAnimationSideHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderXycroniumToolForge implements IBlockRenderingHandler {

    private static FMSModel externalModel = ProjectXModels.getModel("tool_forge");
    private TextureAtlasSprite texture;

    @Override
    public boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        CCModel[] model = externalModel.getModel();
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        this.texture = iconProvider.getIcon(0, 0);
        int meta = state.getBlock().getMetaFromState(state);
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);
        TextureAtlasSprite textureBlockTop = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureBlockSide = iconProvider.getIcon(meta, 1);
        TextureAtlasSprite textureBlockBottom = iconProvider.getIcon(meta, 2);
        int animationBrightness = handler.getAnimationBrightness(state, 0);
        ColourRGBA animationColor = handler.getAnimationColor(state, 0);
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        for(int part = 0; part < model.length; part++){
            model[part].apply(new Translation(Vector3.fromBlockPos(pos)));
        }

        for(int pass = 0; pass < 2; pass++){
            renderState.reset();

            for(int part = 0; part < model.length; part++){
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                model[part].setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                model[part].render(renderState, pass == 0 ? new IconTransformation(textureAnimation) : new MultiIconTransformation(textureBlockBottom, textureBlockTop, textureBlockSide, textureBlockSide, textureBlockSide, textureBlockSide));
            }
        }

        return true;
    }

    @Override
    public void renderBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture) {
        CCModel[] model = externalModel.getModel();

        for(int part = 0; part < model.length; part++){
            model[part].apply(new Translation(Vector3.fromBlockPos(pos)));
            model[part].render(renderState, new IconTransformation(texture));
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack) {
        Tessellator.getInstance().draw();
        CCModel[] model = externalModel.getModel();
        IAnimationSideHandler handler = (IAnimationSideHandler)Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);
        TextureAtlasSprite textureBlockTop = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureBlockSide = iconProvider.getIcon(meta, 1);
        TextureAtlasSprite textureBlockBottom = iconProvider.getIcon(meta, 2);
        int animationBrightness = handler.getAnimationBrightness(stack, 0);
        ColourRGBA animationColor = handler.getAnimationColor(stack, 0);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        for(int pass = 0; pass < 2; pass++){
            VertexBuffer buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            renderState.reset();
            renderState.bind(buffer);
            renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

            for(int part = 0; part < model.length; part++){
                model[part].setColour(pass == 0 ? animationColor.rgba() : 0xFFFFFFFF);
                model[part].render(renderState, pass == 0 ? new IconTransformation(textureAnimation) : new MultiIconTransformation(textureBlockBottom, textureBlockTop, textureBlockSide, textureBlockSide, textureBlockSide, textureBlockSide));
            }

            Tessellator.getInstance().draw();
        }

        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

}
