package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMultipassModel {

    public static CCModel[] missingModel = new CCModel[]{CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals()};

    public static TextureAtlasSprite missingSprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();

    CCModel[] getModel(RenderData data, EnumRenderType type);

    int getPassCount(RenderData data, EnumRenderType type);

    TextureAtlasSprite getModelTexture(RenderData data, EnumRenderType type);

    Colour getColorMultiplier(RenderData data, EnumRenderType type);

    int getBrightness(RenderData data, EnumRenderType type);

}
