package keri.projectx.client.renderold;

import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconBlock;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.client.render.IAnimationSideHandler;
import keri.projectx.tile.TileEntityXynergyNode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.IntStream;

//TODO Convert to IMultipassModel...
@SideOnly(Side.CLIENT)
public class RenderXynergyNode extends TileEntitySpecialRenderer<TileEntityXynergyNode> implements IBlockRenderingHandler {

    private static CCModel[][] model;
    private static RenderTruncatedIcosahedron renderIcosahedron = new RenderTruncatedIcosahedron();
    private TextureAtlasSprite texture;

    static{
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(4D, 0D, 4D, 12D, 2D, 12D),
                new Cuboid6(3.5D, 0D, 3.5D, 5D, 3.5D, 5D),
                new Cuboid6(11D, 0D, 3.5D, 12.5D, 3.5D, 5D),
                new Cuboid6(11D, 0D, 11D, 12.5D, 3.5D, 12.5D),
                new Cuboid6(3.5D, 0D, 11D, 5D, 3.5D, 12.5D),
                new Cuboid6(5D, 2D, 4D, 11D, 3D, 6D),
                new Cuboid6(5D, 2D, 11D, 11D, 3D, 13D),
                new Cuboid6(4D, 2D, 5D, 5D, 4D, 11D),
                new Cuboid6(11D, 2D, 5D, 12D, 4D, 11D),
                new Cuboid6(3D, 0D, 3D, 4D, 3D, 13D),
                new Cuboid6(12D, 0D, 3D, 13D, 3D, 13D),
                new Cuboid6(4D, 0D, 3D, 12D, 3D, 4D),
                new Cuboid6(4D, 0D, 12D, 12D, 3D, 13D)
        };

        model = new CCModel[6][];
        IntStream.range(0, model.length).forEach(index -> model[index] = new CCModel[bounds.length]);
        IntStream.range(0, bounds.length).forEach(index -> model[0][index] = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds[index], 16D)).computeNormals());
        model[0][5].apply(new Rotation(45D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(new Vector3(8D, 3.25D, 4.5D).divide(16D)));
        model[0][6].apply(new Rotation(-45D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(new Vector3(8D, 2D, 12D).divide(16D)));
        model[0][7].apply(new Rotation(45D * MathHelper.torad, new Vector3(0D, 0D, 1D)).at(new Vector3(5.25D, 2.5D, 8D).divide(16D)));
        model[0][8].apply(new Rotation(-45D * MathHelper.torad, new Vector3(0D, 0D, 1D)).at(new Vector3(10.8D, 2.5D, 8D).divide(16D)));
        IntStream.range(0, bounds.length).forEach(index -> {
            model[1][index] = model[0][index].copy();
            model[1][index].apply(new Rotation(180D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
            model[2][index] = model[0][index].copy();
            model[2][index].apply(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
            model[3][index] = model[2][index].copy();
            model[3][index].apply(new Rotation(90D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
            model[4][index] = model[2][index].copy();
            model[4][index].apply(new Rotation(180D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
            model[5][index] = model[2][index].copy();
            model[5][index].apply(new Rotation(270D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
        });
    }

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        TextureAtlasSprite texture = ((IIconBlock)state.getBlock()).getIcon(0, 0);
        this.texture = texture;

        if(state instanceof IExtendedBlockState){
            IExtendedBlockState extendedState = (IExtendedBlockState)state;
            NBTTagCompound tag = (NBTTagCompound)extendedState.getValue(CommonProperties.NBT_TAG_PROPERTY);

            if(tag != null){
                EnumFacing orientation = EnumFacing.getFront(tag.getInteger("orientation"));
                CCModel[] currentModel = null;

                switch(orientation){
                    case UP:
                        currentModel = model[1];
                        break;
                    case DOWN:
                        currentModel = model[0];
                        break;
                    case NORTH:
                        currentModel = model[2];
                        break;
                    case EAST:
                        currentModel = model[5];
                        break;
                    case SOUTH:
                        currentModel = model[4];
                        break;
                    case WEST:
                        currentModel = model[3];
                        break;
                }

                for(int i = 0; i < currentModel.length; i++){
                    currentModel[i].render(renderState, new IconTransformation(texture));
                }
            }
        }
    }

    @Override
    public void renderTileEntityAt(TileEntityXynergyNode tile, double x, double y, double z, float partialTicks, int destroyStage) {

    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        TextureAtlasSprite textureAnimation = ((IAnimationSideHandler)Block.getBlockFromItem(stack.getItem())).getAnimationIcon(stack, 0);
        TextureAtlasSprite textureBlock = ((IIconBlock)Block.getBlockFromItem(stack.getItem())).getIcon(0, 0);

        for(int pass = 0; pass < 2; pass++){
            for(int part = 0; part < model[0].length; part++){

            }
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }

    @Override
    public String getBlockKey(IExtendedBlockState state) {
        return Integer.toString(((NBTTagCompound)state.getValue(CommonProperties.NBT_TAG_PROPERTY)).getInteger("orientation"));
    }

}
