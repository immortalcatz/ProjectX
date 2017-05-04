package keri.projectx.client.model;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.property.CommonProperties;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.client.render.EnumRenderType;
import keri.projectx.client.render.IMultipassModel;
import keri.projectx.client.render.RenderData;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class ModelXynergyNode implements IMultipassModel {

    private static CCModel[][] model;

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
            model[3][index].apply(new Rotation(270D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
            model[4][index] = model[2][index].copy();
            model[4][index].apply(new Rotation(180D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
            model[5][index] = model[2][index].copy();
            model[5][index].apply(new Rotation(90D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
        });
    }

    @Override
    public CCModel[] getModel(RenderData data, EnumRenderType type) {
        if(type == EnumRenderType.BLOCK){
            if(data.getState() instanceof IExtendedBlockState){
                IExtendedBlockState extendedState = (IExtendedBlockState)data.getState();
                NBTTagCompound tag = (NBTTagCompound)extendedState.getValue(CommonProperties.NBT_TAG_PROPERTY);
                return model[tag.getInteger("orientation")];
            }
            else{
                return missingModel;
            }
        }
        else{
            return model[0];
        }
    }

    @Override
    public int getPassCount(RenderData data, EnumRenderType type) {
        return 2;
    }

    //TODO Fix this stuff...

    @Override
    public TextureAtlasSprite getModelTexture(RenderData data, EnumRenderType type){
        return missingSprite;
    }

    @Override
    public Colour getColorMultiplier(RenderData data, EnumRenderType type) {
        return new ColourRGBA(255, 255, 255, 255);
    }

    @Override
    public int getBrightness(RenderData data, EnumRenderType type) {
        return data.getLastBrightness();
    }

}
