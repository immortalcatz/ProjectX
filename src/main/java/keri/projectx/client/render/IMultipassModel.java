package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMultipassModel {

    CCModel[] getModel(RenderData data, EnumRenderType type);

    int getPassCount(RenderData data, EnumRenderType type);

    TextureAtlasSprite getModelTexture(RenderData data, EnumRenderType type);

    TextureAtlasSprite getParticleTexture();

    Colour getColorMultiplier(RenderData data, EnumRenderType type);

    int getBrightness(RenderData data, EnumRenderType type);

}
