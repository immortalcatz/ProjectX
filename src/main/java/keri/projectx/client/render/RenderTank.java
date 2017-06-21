package keri.projectx.client.render;

import codechicken.lib.colour.ColourARGB;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.IBlockRenderingHandler;
import keri.ninetaillib.lib.render.RenderingConstants;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.ModelUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.projectx.tile.TileEntityTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
public class RenderTank extends TileEntitySpecialRenderer<TileEntityTank> implements IBlockRenderingHandler {

    public static final RenderTank INSTANCE = new RenderTank();
    public static EnumBlockRenderType RENDER_TYPE;
    private static CCModel[] BLOCK_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(INSTANCE);
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(0D, 0D, 0D, 16D, 2D, 16D),
                new Cuboid6(0D, 2D, 0D, 2D, 16D, 2D),
                new Cuboid6(14D, 2D, 0D, 16D, 16D, 2D),
                new Cuboid6(14D, 2D, 14D, 16D, 16D, 16D),
                new Cuboid6(0D, 2D, 14D, 2D, 16D, 16D),
                new Cuboid6(2D, 2D, 1D, 14D, 14D, 2D),
                new Cuboid6(2D, 2D, 14D, 14D, 14D, 15D),
                new Cuboid6(1D, 2D, 2D, 2D, 14D, 14D),
                new Cuboid6(14D, 2D, 2D, 15D, 14D, 14D),
                new Cuboid6(2D, 14D, 0D, 14D, 16D, 2D),
                new Cuboid6(2D, 14D, 14D, 14D, 16D, 16D),
                new Cuboid6(0D, 14D, 2D, 2D, 16D, 14D),
                new Cuboid6(14D, 14D, 2D, 16D, 16D, 14D)
        };
        BLOCK_MODEL = ModelUtils.getNormalized(bounds);
    }

    @Override
    public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
        IIconBlock iconProvider = (IIconBlock)world.getBlockState(pos).getBlock();
        TextureAtlasSprite textureFrame = iconProvider.getIcon(0, 0);
        TextureAtlasSprite textureGlass = iconProvider.getIcon(0, 1);
        CCRenderState renderState = RenderingConstants.getRenderState();
        BakingVertexBuffer parent = BakingVertexBuffer.create();
        parent.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderState.reset();
        renderState.bind(parent);

        if(layer == BlockRenderLayer.TRANSLUCENT){
            for(int i = 0; i < BLOCK_MODEL.length; i++){
                if(i > 4 && i < 9){
                    BLOCK_MODEL[i].render(renderState, new IconTransformation(textureGlass));
                }
            }
        }
        else{
            for(int i = 0; i < BLOCK_MODEL.length; i++){
                if(i < 5 || i > 8){
                    BLOCK_MODEL[i].render(renderState, new IconTransformation(textureFrame));
                }
            }
        }

        parent.finishDrawing();
        return RenderUtils.renderQuads(buffer, world, pos, parent.bake());
    }

    @Override
    public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);

        for(int i = 0; i < BLOCK_MODEL.length; i++){
            CCModel part = BLOCK_MODEL[i].copy();
            part.apply(new Translation(Vector3.fromBlockPos(pos)));
            part.render(renderState, new IconTransformation(texture));
        }
    }

    @Override
    public void renderInventory(ItemStack stack, VertexBuffer buffer) {
        GlStateManager.enableLighting();
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());
        TextureAtlasSprite textureFrame = iconProvider.getIcon(0, 0);
        TextureAtlasSprite textureGlass = iconProvider.getIcon(0, 1);
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);

        for(int i = 0; i < BLOCK_MODEL.length; i++){
            if(i > 4 && i < 9){
                BLOCK_MODEL[i].render(renderState, new IconTransformation(textureGlass));
            }
            else{
                BLOCK_MODEL[i].render(renderState, new IconTransformation(textureFrame));
            }
        }
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public void renderTileEntityAt(TileEntityTank tile, double x, double y, double z, float partialTicks, int destroyStage) {
        int lastBrightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        CCModel model = ModelUtils.getNormalized(new Cuboid6(2D, 2D, 2D, 14D, 14D, 14D));
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        CCRenderState renderState = RenderingConstants.getRenderState();

        if(tile != null && tile.getFluidTank().getFluid() != null){
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            TextureUtils.bindBlockTexture();
            buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
            renderState.reset();
            renderState.bind(buffer);
            renderState.lightMatrix.locate(tile.getWorld(), tile.getPos());
            renderState.setBrightness(tile.getWorld(), tile.getPos());
            int brightness = tile.getWorld().getCombinedLight(tile.getPos(), tile.getFluidTank().getFluid().getFluid().getLuminosity());
            renderState.brightness = brightness;
            TextureAtlasSprite texture = TextureUtils.getTexture(tile.getFluidTank().getFluid().getFluid().getStill());
            model.setColour(new ColourARGB(tile.getFluidTank().getFluid().getFluid().getColor()).rgba());
            model.apply(new Translation(new Vector3(x, y, z)));
            model.render(renderState, new IconTransformation(texture));
            Tessellator.getInstance().draw();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

}
