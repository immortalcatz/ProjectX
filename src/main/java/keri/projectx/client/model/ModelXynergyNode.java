package keri.projectx.client.model;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import keri.projectx.client.render.EnumRenderType;
import keri.projectx.client.render.IMultipassModel;
import keri.projectx.client.render.RenderData;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelXynergyNode implements IMultipassModel {

    //TODO Finish you lazy shit!

    @Override
    public CCModel[] getModel(RenderData data, EnumRenderType type) {
        return new CCModel[0];
    }

    @Override
    public int getPassCount(RenderData data, EnumRenderType type) {
        return 2;
    }

    @Override
    public TextureAtlasSprite getModelTexture(RenderData data, EnumRenderType type) {
        return null;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public Colour getColorMultiplier(RenderData data, EnumRenderType type) {
        return null;
    }

    @Override
    public int getBrightness(RenderData data, EnumRenderType type) {
        return 0;
    }

}
