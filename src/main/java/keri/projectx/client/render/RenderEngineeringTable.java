package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.ClientUtils;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.fms.FMSModel;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.client.ProjectXModels;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class RenderEngineeringTable extends TileEntitySpecialRenderer<TileEntityEngineeringTable> implements IBlockRenderingHandler {

    private static FMSModel externalModel = ProjectXModels.getModel("engineering_table");

    @Override
    public boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        CCModel[] model = externalModel.getModel();
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        IAnimationSideHandler handler = (IAnimationSideHandler)state.getBlock();
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        int meta = state.getBlock().getMetaFromState(state);
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(state, 0);
        int animationBrightness = handler.getAnimationBrightness(state, 0);
        ColourRGBA animationColor = handler.getAnimationColor(state, 0);
        ColourRGBA colorMultiplier = handler.getColorMultiplier(state, 0);

        for(int part = 0; part < model.length; part++){
            model[part].apply(new Translation(Vector3.fromBlockPos(pos)));
        }

        for(int pass = 0; pass < 2; pass++){
            renderState.reset();

            for(int part = 0; part < model.length; part++){
                renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;
                model[part].setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                model[part].render(renderState, new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
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
        CCModel[] model = externalModel.getModel();
        Tessellator.getInstance().draw();
        IAnimationSideHandler handler = (IAnimationSideHandler) Block.getBlockFromItem(stack.getItem());
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        int meta = stack.getMetadata();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        TextureAtlasSprite textureBlock = iconProvider.getIcon(meta, 0);
        TextureAtlasSprite textureAnimation = handler.getAnimationIcon(stack, 0);
        int animationBrightness = handler.getAnimationBrightness(stack, 0);
        ColourRGBA animationColor = handler.getAnimationColor(stack, 0);
        ColourRGBA colorMultiplier = handler.getColorMultiplier(stack, 0);

        for(int pass = 0; pass < 2; pass++){
            VertexBuffer buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            renderState.reset();
            renderState.bind(buffer);
            renderState.brightness = pass == 0 ? animationBrightness : lastBrightness;

            for(int part = 0; part < model.length; part++){
                model[part].setColour(pass == 0 ? animationColor.rgba() : colorMultiplier.rgba());
                model[part].render(renderState, new IconTransformation(pass == 0 ? textureAnimation : textureBlock));
            }

            Tessellator.getInstance().draw();
        }

        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, 0.58D, 0.5D);
        GlStateManager.rotate((float) ClientUtils.getRenderTime() * 2.6F, 0F, 1F, 0F);
        RenderTruncatedIcosahedron renderer = new RenderTruncatedIcosahedron();
        Colour colourHex = CommonUtils.fromAWT(Color.getHSBColor((float)ClientUtils.getRenderTime() / 255F, 1F, 1F));
        Colour colorPent = colourHex.copy().multiplyC(0.48D);
        renderer.render(0.82D, colorPent, colourHex, RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntityEngineeringTable tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.58D, z + 0.5D);
        GlStateManager.rotate((float) ClientUtils.getRenderTime() * 2.6F, 0F, 1F, 0F);
        RenderTruncatedIcosahedron renderer = new RenderTruncatedIcosahedron();
        Colour colourHex = CommonUtils.fromAWT(Color.getHSBColor((float)ClientUtils.getRenderTime() / 255F, 1F, 1F));
        Colour colorPent = colourHex.copy().multiplyC(0.48D);
        renderer.render(0.82D, colorPent, colourHex, RenderTruncatedIcosahedron.EnumHedrontexture.SPACE);
        GlStateManager.popMatrix();
    }

}
