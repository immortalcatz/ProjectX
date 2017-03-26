package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Scale;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.Vertex5;
import keri.ninetaillib.render.IBlockKeyProvider;
import keri.ninetaillib.render.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconBlock;
import keri.projectx.ProjectX;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderQuartzCrystal implements IBlockRenderingHandler, IBlockKeyProvider {

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {

    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        if(this.hasDynamicItemRendering()){
            TextureAtlasSprite texture = ((IIconBlock)Block.getBlockFromItem(stack.getItem())).getIcon(0, 0);
            CCModel model = CCModel.newModel(GL11.GL_QUADS, 24);
            model.verts = this.getVertices(texture);
            model.setColour(new ColourRGBA(0, 200, 200, 255).rgba());
            model.apply(new Scale(new Vector3(2D, 2D, 2D)));
            model.apply(new Translation(new Vector3(0.5D, 0.5D, 0.5D)));
            renderState.brightness = 0x00F000F0;
            renderState.pushLightmap();
            model.computeNormals().render(renderState);
        }
        else{
            TextureAtlasSprite texture = ((IIconBlock)Block.getBlockFromItem(stack.getItem())).getIcon(0, 0);
            CCModel model = CCModel.newModel(GL11.GL_QUADS, 24);
            model.verts = this.getVertices(texture);
            model.setColour(new ColourRGBA(0, 200, 200, 255).rgba());
            model.apply(new Scale(new Vector3(2D, 2D, 2D)));
            model.apply(new Translation(new Vector3(0.5D, 0.5D, 0.5D)));
            model.computeNormals().render(renderState);
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IIconBlock block, int meta) {
        return block.getIcon(0, 0);
    }

    @Override
    public boolean hasDynamicItemRendering() {
        return !ProjectX.CONFIG.useFastItemRendering;
    }

    @Override
    public String getExtendedBlockKey(IExtendedBlockState state) {
        return "";
    }

    private Vertex5[] getVertices(TextureAtlasSprite texture){
        Vertex5[] verts = new Vertex5[24];
        float u = texture.getMinU();
        float v = texture.getMinV();
        float U = texture.getMaxU();
        float V = texture.getMaxV();
        verts[0] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        verts[1] = new Vertex5(0.09995D, 0.121D, 0.0D, u, V);
        verts[2] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, V);
        verts[3] = new Vertex5(-0.04704D, 0.121D, -0.08485D, U, v);
        verts[4] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        verts[5] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, V);
        verts[6] = new Vertex5(-0.09995D, -0.121D, 0.0D, U, V);
        verts[7] = new Vertex5(-0.04704D, 0.121D, 0.08485D, U, v);
        verts[8] = new Vertex5(0.00395D, 0.364D, 0.0D, u, v);
        verts[9] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, V);
        verts[10] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, V);
        verts[11] = new Vertex5(0.09995D, 0.121D, 0.0D, U, v);
        verts[12] = new Vertex5(-0.04704D, 0.121D, 0.08485D, u, v);
        verts[13] = new Vertex5(-0.09995D, -0.121D, -0.0D, u, V);
        verts[14] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        verts[15] = new Vertex5(0.04704D, -0.121D, 0.08485D, U, v);
        verts[16] = new Vertex5(0.09995D, 0.121D, 0.0D, u, v);
        verts[17] = new Vertex5(0.04704D, -0.121D, 0.08485D, u, V);
        verts[18] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        verts[19] = new Vertex5(0.04704D, -0.121D, -0.08485D, U, v);
        verts[20] = new Vertex5(-0.04704D, 0.121D, -0.08485D, u, v);
        verts[21] = new Vertex5(0.04704D, -0.121D, -0.08485D, u, V);
        verts[22] = new Vertex5(-0.00395D, -0.364D, 0.0D, U, V);
        verts[23] = new Vertex5(-0.09995D, -0.121D, -0.0D, U, v);
        return verts;
    }

}
