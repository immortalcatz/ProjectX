package keri.projectx.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Scale;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.Vertex5;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingConstants;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderQuartzCrystal implements IBlockRenderingHandler {

    public static EnumBlockRenderType RENDER_TYPE;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderQuartzCrystal());
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
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        IAnimationHandler animationHandler = (IAnimationHandler)Block.getBlockFromItem(stack.getItem());
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = animationHandler.getAnimationBrightness(stack, 0);
        CCModel model = this.getModel(animationHandler.getAnimationIcon(stack, 0)).copy();
        model.apply(new Scale(new Vector3(2D, 2D, 2D)));
        model.apply(new Translation(new Vector3(0.5D, 0.5D, 0.5D)));
        model.render(renderState);
        renderState.brightness = lastBrightness;
        Tessellator.getInstance().draw();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

    private CCModel getModel(TextureAtlasSprite texture){
        CCModel model = CCModel.quadModel(24);
        float u = texture.getMinU();
        float v = texture.getMinV();
        float U = texture.getMaxU();
        float V = texture.getMaxV();
        model.verts[0] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[1] = new Vertex5(0.09995D, 0.121D, 0.0D, u, V);
        model.verts[2] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, V);
        model.verts[3] = new Vertex5(-0.04704D, 0.121D, -0.08485D, U, v);
        model.verts[4] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[5] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, V);
        model.verts[6] = new Vertex5(-0.09995D, -0.121D, 0.0D, U, V);
        model.verts[7] = new Vertex5(-0.04704D, 0.121D, 0.08485D, U, v);
        model.verts[8] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        model.verts[9] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, V);
        model.verts[10] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, V);
        model.verts[11] = new Vertex5(0.09995D, 0.121D, 0.0D, U, v);
        model.verts[12] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, v);
        model.verts[13] = new Vertex5(-0.09995D, -0.121D, -0.0D, u, V);
        model.verts[14] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[15] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, v);
        model.verts[16] = new Vertex5(0.09995D, 0.121D, 0.0D, u, v);
        model.verts[17] = new Vertex5(0.04704D, -0.121D, 0.08485D, u, V);
        model.verts[18] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[19] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, v);
        model.verts[20] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, v);
        model.verts[21] = new Vertex5(0.04704D, -0.121D, -0.08485D, u, V);
        model.verts[22] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        model.verts[23] = new Vertex5(-0.09995D, -0.121D, -0.0D, U, v);
        return model.computeNormals();
    }

}
